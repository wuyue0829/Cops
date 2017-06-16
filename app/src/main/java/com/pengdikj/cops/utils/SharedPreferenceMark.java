package com.pengdikj.cops.utils;

/**
 * 权限提示标示
 *
 */
public final class SharedPreferenceMark {


    public static Boolean hasShowCamera=false;//是否已经提示过摄像头权限
    public static Boolean hasShowLocation=false;//是否已经提示过摄像头权限
    public static Boolean hasShowRecordAudio=false;//是否已经提示过摄像头权限
    public static Boolean hasShowExtralStoreage=false;//是否已经提示过摄像头权限
    public static Boolean hasShowContact=false;//是否已经提示过摄像头权限


    public static Boolean getHasShowCamera() {
        return hasShowCamera;
    }

    public static void setHasShowCamera(Boolean hasShowCamera) {
        SharedPreferenceMark.hasShowCamera = hasShowCamera;
    }

    public static Boolean getHasShowLocation() {
        return hasShowLocation;
    }

    public static void setHasShowLocation(Boolean hasShowLocation) {
        SharedPreferenceMark.hasShowLocation = hasShowLocation;
    }

    public static Boolean getHasShowRecordAudio() {
        return hasShowRecordAudio;
    }

    public static void setHasShowRecordAudio(Boolean hasShowRecordAudio) {
        SharedPreferenceMark.hasShowRecordAudio = hasShowRecordAudio;
    }

    public static Boolean getHasShowExtralStoreage() {
        return hasShowExtralStoreage;
    }

    public static void setHasShowExtralStoreage(Boolean hasShowExtralStoreage) {
        SharedPreferenceMark.hasShowExtralStoreage = hasShowExtralStoreage;
    }

    public static Boolean getHasShowContact() {
        return hasShowContact;
    }

    public static void setHasShowContact(Boolean hasShowContact) {
        SharedPreferenceMark.hasShowContact = hasShowContact;
    }
}
