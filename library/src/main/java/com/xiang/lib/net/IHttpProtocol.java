package com.xiang.lib.net;



import com.xiang.lib.allbean.AttnBean;
import com.xiang.lib.base.BaseResponse;
import com.xiang.lib.base.BaseResponseTC;
import com.xiang.lib.allbean.CommonBean;
import com.xiang.lib.allbean.LoginBean;
import com.xiang.lib.chatBean.ChatMessage;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface IHttpProtocol {
    @POST("Lg/user")
    @FormUrlEncoded
    Observable<BaseResponseTC<LoginBean>> login(@FieldMap HashMap<String, String> map);

    @POST("update/user")
    @FormUrlEncoded
    Observable<BaseResponseTC<String>> updateUserInfo(@FieldMap HashMap<String, String> map);

    @POST("update/user")
    @FormUrlEncoded
    Observable<BaseResponseTC<String>> upUserInfo(@FieldMap HashMap<String, Object> map);

    @POST("all/user")
    @FormUrlEncoded
    Observable<BaseResponseTC<List<AttnBean>>> getAllAttn(@FieldMap HashMap<String, String> map);

    @POST("info/user")
    @FormUrlEncoded
    Observable<BaseResponseTC<LoginBean>> getUserInfo(@FieldMap HashMap<String, String> map);

    @POST("history/user")
    @FormUrlEncoded
    Observable<BaseResponseTC<List<ChatMessage>>> getHistory(@FieldMap HashMap<String, Object> map);

    @POST("")
    @FormUrlEncoded
    Observable<BaseResponse<CommonBean>> getData(@Url String url, @FieldMap HashMap<String, String> map);
}
