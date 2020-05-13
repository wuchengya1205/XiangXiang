package com.xiang.lib.utils;

/**
 * author : fengzhangwei
 * date : 2019/9/11
 */
public class Constant {

    public static String BASE_URL = "http://v.juhe.cn/";
    public static String BASE_TOMACT_URL = "http://172.16.201.143:8082/"; //172.16.201.143  //111.229.231.206
    public static String BASE_CHAT_URL =   "http://172.16.201.143:9092/";   // im连接地址
    public static String SPKey_UID = "user_uid";
    public static String SPKey_PHONE = SPUtils.getInstance().getString(SPKey_UID) + "_user_phone";
    public static String SPKey_PWD = SPUtils.getInstance().getString(SPKey_UID) + "_user_pwd";
    public static String SPKey_USERINFO = SPUtils.getInstance().getString(SPKey_UID) + "_user_info";

    public static String FROM_ID = "776ec439-be0f-4df5-b492-39ac7cd360fd";
    public static String TO_ID = "77aa7031-9fab-4199-84a2-a180c399b0bb";
}
