package com.pengdikj.cops.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.pengdikj.cops.R;

import java.util.ArrayList;

/**
 * Created by asus on 2016/3/22.
 */
public class ContactFragment extends Fragment {

    private static final int PERMISSION_CALL = 1000;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private View view;
    private RecyclerView rvContacts;
    private WaveSideBar sideBar;
    private String phoneNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initView(){
        rvContacts = (RecyclerView) getView().findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        ContactsAdapter1 contactsAdapter = new ContactsAdapter1(contacts, R.layout.item_contacts_one);
        contactsAdapter.setOnItemClickListener(new ContactsAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                phoneNumber = Contact.getChineseContacts().get(position).getPhoneNumber();
                getpersion(phoneNumber);
            }
        });
        rvContacts.setAdapter(contactsAdapter);
        sideBar = (WaveSideBar) getView().findViewById(R.id.side_bar);
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i=0; i<contacts.size(); i++) {
                    if (contacts.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void getpersion(String phoneNumber){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_CALL);
                return;
            }
            else
            {
                callPhone(phoneNumber);
            }
        }
        else
        {
            callPhone(phoneNumber);
        }
    }
    private void callPhone(String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case  PERMISSION_CALL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callPhone(phoneNumber);
                }
                else
                {
                    // Permission Denied
                    Toast.makeText(getActivity(), "CALL_PHONE Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void initData() {
        contacts.addAll(Contact.getChinese1Contacts());
    }
}
