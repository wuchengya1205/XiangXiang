package com.lib.xiangxiang.im;

import java.util.Random;

/**
 * author : fengzhangwei
 * date : 2019/12/19
 */
public class ImSendMessageUtils {

    /**
     * 随机产生一个4个字节的int
     */
    public static int getRandomInt() {
        int min = 10;
        int max = 99;
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }


    public static String getRandomString() {
        String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < 2; i++) {
            int number = random.nextInt(62);// 0~61
            sf.append(str.charAt(number));

        }
        return sf.toString();
    }

    public static String getPid(){
        StringBuilder stringBuffer = new StringBuilder();
        String time = String.valueOf(System.currentTimeMillis());
        stringBuffer.append("AN")
                .append(getRandomInt())
                .append(getRandomString())
                .append(getRandomInt())
                .append(getRandomString())
                .append(getRandomInt())
                .append(time.substring(time.length() - 4, time.length()));
        return stringBuffer.toString();
    }
}
