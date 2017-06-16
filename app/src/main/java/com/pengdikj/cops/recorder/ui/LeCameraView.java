package com.pengdikj.cops.recorder.ui;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.letv.recorder.bean.AudioParams;
import com.letv.recorder.bean.CameraParams;
import com.letv.recorder.bean.LivesInfo;
import com.letv.recorder.callback.ISurfaceCreatedListener;
import com.letv.recorder.callback.LetvRecorderCallback;
import com.letv.recorder.callback.PublishListener;
import com.letv.recorder.controller.CameraSurfaceView;
import com.letv.recorder.controller.LetvPublisher;
import com.letv.recorder.ui.logic.RecorderConstance;
import com.pengdikj.cops.recorder.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 这个是乐视云直播使用方法。
 * 相比较CameraView 移动直播使用方法,这里区别有以下5点,
 */
public class LeCameraView extends CameraSurfaceView implements ISurfaceCreatedListener {
    //*********************区别1:这里使用LetvPublisher******************
    private LetvPublisher publisher;




    private CameraParams cameraParams;
    private final static String TAG = "CameraView";
    private boolean isBack = false;//后台标志,在进入后台之前正在推流设置为true。判断是否在后台回来时继续推流
    private String url;
    private TextView timeView;//推流时间显示
    private int time = 0;
    public LeCameraView(Context context) {
        super(context);
    }

    public LeCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void init(Activity context, boolean isLandscape,String activityId, String userId, String secretKey){
        //*********************区别2:需要先调用LetvPublisher.init(),并且传入云直播所使用的活动ID,用户ID和秘钥******************
        LetvPublisher.init(activityId,userId,secretKey);




        publisher = LetvPublisher.getInstance();
        publisher.initPublisher(context);
        publisher.getRecorderContext().setUseLanscape(isLandscape);//告诉推流器使用横屏推流还是竖屏推流
        cameraParams = publisher.getCameraParams();
        AudioParams audioParams = publisher.getAudioParams();
        publisher.setPublishListener(listener);//设置推流状态监听器
        //绑定Camera显示View,要求必须是CameraSurfaceView
        publisher.getVideoRecordDevice().bindingGLView(this);
        //设置CameraSurfaceView 监听器,当CameraSurfaceView 创建成功的时候回回调onGLSurfaceCreatedListener,这个时候才能开启摄像头
        publisher.getVideoRecordDevice().setSurfaceCreatedListener(this);
        //////////////////以下设置必须在在推流之前设置,也可以不设置////////////////////////////////////////
        if(isLandscape){//设置流分辨率。要求宽度必须是16的整倍数,高度没有要求
            cameraParams.setWidth(640);
            cameraParams.setHeight(368);
        }else{
            cameraParams.setWidth(368);
            cameraParams.setHeight(640);
        }
        cameraParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT); //开启默认前置摄像头
        cameraParams.setVideoBitrate(1000*1000); //设置码率
        audioParams.setEnableVolumeGain(true);//开启音量调节,注意,这一点会影响性能,如果没有必要,设置为false
        cameraParams.setFocusOnTouch(false);//关闭对焦功能
        cameraParams.setFocusOnAnimation(false);//关闭对焦动画
        cameraParams.setOpenGestureZoom(true);//开启拉近拉远手势
        cameraParams.setFrontCameraMirror(true);//开启镜像
        publisher.getVideoRecordDevice().setFocusView(new View(getContext()));//设置对焦图片。如果需要对焦功能和对焦动画,请打开上边两个设置,并且在这里传入一个合适的View
        /////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onPause() {
        super.onPause();
        if(publisher.isRecording()) { //正在推流
            isBack = true;
            publisher.stopPublish();//停止推流
        }
        //关闭摄像头
        publisher.getVideoRecordDevice().stop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        publisher.release();//销毁推流器
    }
    @Override
    public void onGLSurfaceCreatedListener() {
        //打开摄像头
        publisher.getVideoRecordDevice().start();
        if(isBack) {
            isBack = false;
            publish(url);
        }
    }

    @Override
    public void zoomOnTouch(int state ,int zoom, int maxZoom) {

    }

    /**
     * 开始推流
     * @param url 推流地址
     */
    public void publish(String url){
        this.url = url;
        time = 0;
        if(!publisher.isRecording() && url != null){
            timeView.setText("开始请求推流");
            //*********************区别3:这里不能在设置推流地址,而是通过handleMachine 去请求调度得到推流地址******************
//            publisher.setUrl(url);//设置推流地址
//            publisher.publish();//在摄像头打开以后才能开始推流
            publisher.handleMachine(callback);




        }else{
            timeView.setText("开始关闭推流");
            publisher.stopPublish(); //结束推流
        }
    }

    /**
     * 切换摄像头,需要注意,切换摄像头不能太频繁
     */
    public void switchCamera(){
        int cameraID ;
        if(cameraParams.getCameraId() == Camera.CameraInfo.CAMERA_FACING_FRONT){
            cameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
        }else{
            cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
            if(flag) flag = !flag;//切换前置摄像头会自动关闭闪光灯
        }
        boolean falg = publisher.getVideoRecordDevice().switchCamera(cameraID);//切换摄像头
        if(!falg){
            Log.d(TAG,"切换失败，最大的可能是没有到允许切换时间");
        }
    }

    /**
     * 开启闪光灯。注意,当使用前置摄像头时不能打开闪光灯
     */
    boolean flag = false;
    public void changeFlash(){
        if(cameraParams.getCameraId() != Camera.CameraInfo.CAMERA_FACING_FRONT) {
            flag = !flag;
            publisher.getVideoRecordDevice().setFlashFlag(flag);//切换闪关灯
        }else{
            ToastUtils.showShort(getContext(),"前置摄像头不能打开闪关灯");
        }
    }

    /**
     * 切换滤镜,设置为0为关闭滤镜
     */
    int model = CameraParams.FILTER_VIDEO_NONE;//无效果
    public void switchFilter(){
        if(model == CameraParams.FILTER_VIDEO_NONE){
            model = CameraParams.FILTER_VIDEO_DEFAULT; //默认的美颜效果
        }else{
            model = CameraParams.FILTER_VIDEO_NONE;//无效果
        }
        publisher.getVideoRecordDevice().setFilterModel(model);//切换滤镜
    }

    /**
     * 设置声音大小,必须对setEnableVolumeGain设置为true
     * @param volume 0-1为缩小音量,1为正常音量,大于1为放大音量
     */
    int volume = 1;
    public void setVolume(){
        if(volume == 1){
            volume = 0;
        }else{
            volume = 1;
        }
        publisher.setVolumeGain(volume);//设置声音大小
    }
    private PublishListener listener = new PublishListener() {
        @Override
        public void onPublish(int code, String msg, Object... obj) {
            Message message = mHandler.obtainMessage(code);
            message.obj = msg;
            mHandler.sendMessage(message);
        }
    };
    private void parseHandler(Message msg){
        switch (msg.what){
            case RecorderConstance.RECORDER_OPEN_URL_SUCESS:
                Log.d(TAG,"推流连接成功:只有当连接成功以后才能开始推流");
                ToastUtils.showShort(getContext(),"推流连接成功:只有当连接成功以后才能开始推流");
                timeView.setText("连接成功");
                break;
            case RecorderConstance.RECORDER_OPEN_URL_FAILED:
                Log.d(TAG,"推流连接失败:如果失败,大多是推流地址不可用或者网络问题");
                ToastUtils.showShort(getContext(),"推流连接失败:如果失败,大多是推流地址不可用或者网络问题");
                timeView.setText("连接失败");
                break;
            case RecorderConstance.RECORDER_PUSH_FIRST_SIZE:
                Log.d(TAG,"第一针画面推流成功,代表成功的开始推流了:推流成功的标志回调");
                ToastUtils.showShort(getContext(),"第一针画面推流成功,代表成功的开始推流了:推流成功的标志回调");
                mHandler.postDelayed(timerRunnable,1000);
                break;
            case RecorderConstance.RECORDER_PUSH_AUDIO_PACKET_LOSS_RATE:
                Log.d(TAG,"音频出现丢帧现象。如果一分钟丢帧次数大于5次,导致声音跳动:可以对网络进行判定");
                break;
            case RecorderConstance.RECORDER_PUSH_VIDEO_PACKET_LOSS_RATE:
                Log.d(TAG,"视频出现丢帧现象,如果一分钟丢帧次数大于5次,导致画面跳动:可以对网络进行判定");
                break;
            case RecorderConstance.RECORDER_PUSH_ERROR:
                ToastUtils.showShort(getContext(),"推流失败,原因:网络较差,编码出错,推流崩溃,第一针数据发送失败...等等各种原因导致");
                Log.d(TAG,"推流失败,原因:网络较差,编码出错,推流崩溃,第一针数据发送失败...等等各种原因导致");
                timeView.setText("推流出错");
                break;
            case RecorderConstance.RECORDER_PUSH_STOP_SUCCESS:
                Log.d(TAG,"成功的关闭了底层推流,可以进行下次推流了:保证推流成功关闭");
                ToastUtils.showShort(getContext(),"成功的关闭了底层推流,可以进行下次推流了:保证推流成功关闭");
                timeView.setText("成功关闭推流服务");
                break;
            //*********************区别5:乐视云直播有一部分属于自己的回调事件*****************
            case RecorderConstance.LIVE_STATE_END_ERROR:
                Log.d(TAG,"直播已结束");
                ToastUtils.showShort(getContext(),"直播已结束");
                break;
            case RecorderConstance.LIVE_STATE_CANCEL_ERROR:
                Log.d(TAG,"直播已取消");
                ToastUtils.showShort(getContext(),"直播已取消");
                break;
            case RecorderConstance.LIVE_STATE_NEED_RECORD:
                Log.d(TAG,"直播开启转点播功能");
                ToastUtils.showShort(getContext(),"直播开启转点播功能");
                break;
            case RecorderConstance.LIVE_STATE_NOT_STARTED_ERROR:
                Log.d(TAG,"直播时间未到");
                ToastUtils.showShort(getContext(),"直播时间未到");
                break;
            case RecorderConstance.LIVE_STATE_OTHER_ERROR:
                Log.d(TAG,"其他直播错误");
                ToastUtils.showShort(getContext(),"其他直播错误");
                break;
            case RecorderConstance.LIVE_STATE_PUSH_COMPLETE:
                Log.d(TAG,"推流完成");
                ToastUtils.showShort(getContext(),"推流完成");
                break;
            case RecorderConstance.LIVE_STATE_TIME_REMAINING:
                Log.d(TAG,"直播剩余时间:在剩余5分钟和30分钟时都会回调");
                ToastUtils.showShort(getContext(),"直播剩余时间:在剩余5分钟和30分钟时都会回调");
                break;
        }
    }

    private LetvRecorderCallback<ArrayList<LivesInfo>> callback = new LetvRecorderCallback<ArrayList<LivesInfo>>() {
        @Override
        public void onFailed(int code, String msg) {

        }

        @Override
        public void onSucess(ArrayList<LivesInfo> data) {
            //*********************区别4:在收到请求成功消息时,需要选择自己的推流机位。并且开始推流******************
            if(data != null && data.size() >0){
                publisher.selectMachine(0);//相当于设置推流地址
                publisher.publish();
            }
        }
    };

    private final  Handler mHandler = new MHandler(LeCameraView.this);
    private final Runnable timerRunnable =  new TimerRunnable(LeCameraView.this);
    private  final class TimerRunnable implements Runnable {
        private WeakReference<LeCameraView> reference;
        private TimerRunnable(LeCameraView view){
            reference = new  WeakReference<LeCameraView>(view);
        }
        @Override
        public void run() {
            LeCameraView view = reference.get();
            if(view != null && view.publisher.isRecording()){
                view.time++;
                view.timeView.setText("时间:"+view.time);
                view.mHandler.postDelayed(view.timerRunnable,1000);
            }
        }
    }
    private final class MHandler extends  Handler{
        private WeakReference<LeCameraView> reference;
        private MHandler(LeCameraView view){
            reference = new WeakReference<LeCameraView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LeCameraView view = reference.get();
            if(view != null){
                view.parseHandler(msg);
            }
        }
    }

    void setTime(TextView time) {
        this.timeView = time;
    }
}
