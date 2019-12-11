package com.xiang.lib.utils;

/**
 * author : fengzhangwei
 * date : 2019/9/11
 */
public class Constant {

    public  static String BASE_URL = "http://v.juhe.cn/";
    public  static String BASE_TOMACT_URL = "http://172.16.200.235:8081/";
    public  static String SPKey_UID = "user_uid";
    public static String SPKey_PHONE = SPUtils.getInstance().getString(SPKey_UID) + "_user_phone";
    public static String SPKey_PWD = SPUtils.getInstance().getString(SPKey_UID) + "_user_pwd";
    public static String SPKey_USERINFO = SPUtils.getInstance().getString(SPKey_UID) + "_user_info";
}
