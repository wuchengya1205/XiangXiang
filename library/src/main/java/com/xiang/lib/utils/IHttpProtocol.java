package com.xiang.lib.utils;



import com.xiang.lib.base.BaseResponse;
import com.xiang.lib.base.BaseResponseTC;
import com.xiang.lib.allbean.CommonBean;
import com.xiang.lib.allbean.LoginBean;

import java.util.HashMap;

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

    @POST("")
    @FormUrlEncoded
    Observable<BaseResponse<CommonBean>> getData(@Url String url, @FieldMap HashMap<String, String> map);
}
