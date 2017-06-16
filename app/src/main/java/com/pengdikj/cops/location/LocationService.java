package com.pengdikj.cops.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.pengdikj.cops.entity.UserGps;
import com.pengdikj.cops.model.MoGps;
import com.pengdikj.cops.utils.BaseUtil;
import com.pengdikj.cops.utils.LogUtil;
import com.pengdikj.cops.utils.constant.NetConstant;
import com.pengdikj.cops.utils.volley.IRequest;
import com.pengdikj.cops.utils.volley.RequestJsonListener;
import com.pengdikj.cops.utils.volley.RequestParams;

import java.util.ArrayList;
import java.util.List;

import static com.pengdikj.cops.ui.A1_HomeActivity.RECEIVER_ACTION;

/**
 * 包名： com.amap.locationservicedemo
 * <p>
 * 创建时间：2016/10/27
 * 项目名称：LocationServiceDemo
 *
 * @author guibao.ggb
 * @email guibao.ggb@alibaba-inc.com
 * <p>
 * 类说明：后台服务定位
 *
 * <p>
 *     modeified by liangchao , on 2017/01/17
 *     update:
 *     1. 只有在由息屏造成的网络断开造成的定位失败时才点亮屏幕
 *     2. 利用notification机制增加进程优先级
 * </p>
 */
public class LocationService extends NotiService {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private int locationCount;

    /**
     * 处理息屏关掉wifi的delegate类
     */
    private IWifiAutoCloseDelegate mWifiAutoCloseDelegate = new WifiAutoCloseDelegate();

    /**
     * 记录是否需要对息屏关掉wifi的情况进行处理
     */
    private boolean mIsWifiCloseable = false;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION);
        registerReceiver(locationChangeBroadcastReceiver, intentFilter);
        applyNotiKeepMech(); //开启利用notification提高进程优先级的机制
        if (mWifiAutoCloseDelegate.isUseful(getApplicationContext())) {
            mIsWifiCloseable = true;
            mWifiAutoCloseDelegate.initOnServiceStarted(getApplicationContext());
        }

        startLocation();

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        unApplyNotiKeepMech();
        stopLocation();

        super.onDestroy();
    }

    /**
     * 启动定位
     */
    void startLocation() {
        stopLocation();

        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this.getApplicationContext());
        }

        mLocationOption = new AMapLocationClientOption();
        // 使用连续
        mLocationOption.setOnceLocation(false);
        mLocationOption.setLocationCacheEnable(false);
        // 每10秒定位一次
        mLocationOption.setInterval(100);
        // 地址信息
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(locationListener);
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    void stopLocation() {
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
        }
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //发送结果的通知
            sendLocationBroadcast(aMapLocation);

            if (!mIsWifiCloseable) {
                return;
            }

            if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                mWifiAutoCloseDelegate.onLocateSuccess(getApplicationContext(), PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isMobileAva(getApplicationContext()));
            } else {
                mWifiAutoCloseDelegate.onLocateFail(getApplicationContext() , aMapLocation.getErrorCode() , PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isWifiCon(getApplicationContext()));
            }

        }

        private void sendLocationBroadcast(AMapLocation aMapLocation) {
            //记录信息并发送广播
            locationCount++;
            long callBackTime = System.currentTimeMillis();
            StringBuffer sb = new StringBuffer();
//            sb.append("定位完成 第" + locationCount + "次\n");
//            sb.append("回调时间: " + Utils.formatUTC(callBackTime, null) + "\n");
            if (null == aMapLocation) {
                sb.append("定位失败：location is null!!!!!!!");
            } else {
                sb.append(Utils.getLocationStr(aMapLocation));
            }

            Intent mIntent = new Intent(RECEIVER_ACTION);
            mIntent.putExtra("result", sb.toString());

            //发送广播
            sendBroadcast(mIntent);
        }

    };


    private BroadcastReceiver locationChangeBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RECEIVER_ACTION)) {
                String locationResult = intent.getStringExtra("result");
                if (null != locationResult && !locationResult.trim().equals("")) {
                    LogUtil.e(locationResult);
                    LogUtil.e("开启线程发发送定位");
                    if(!BaseUtil.isSpace(locationResult)){
                        String[] contact = locationResult.split("\\,");
                        if(contact.length == 2){
                            List<UserGps> list = new ArrayList<>();
                            UserGps userGps = new UserGps();
                            userGps.setLatitude(contact[1]);
                            userGps.setLongitude(contact[0]);
                            list.add(userGps);
                            RequestParams requestParams = new RequestParams();
                            requestParams.put("jsondata",new Gson().toJson(list));
                            requestParams.put("userId","1");
                            LogUtil.e(new Gson().toJson(userGps));
                            IRequest.post(getApplicationContext(), NetConstant.UPLOADGPS, MoGps.class, requestParams, new RequestJsonListener<MoGps>() {
                                @Override
                                public void requestSuccess(MoGps result) {
                                    // TODO Auto-generated method stub
                                    LogUtil.e("yy"+result.toString());
                                }

                                @Override
                                public void requestError(VolleyError e) {
                                    // TODO Auto-generated method stub
                                    LogUtil.e("yy"+e.toString());
                                }
                            });
                        }
                    }
                }
            }
        }
    };

}
