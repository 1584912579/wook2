package com.example.asus.wook2.net;

import com.example.asus.wook2.bean.GetCartsBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by asus on 2018/5/19.
 */

public interface GetCartsApiService {
    @FormUrlEncoded
    @POST("product/getCarts")
    Observable<GetCartsBean> getGetCarts(@Field("uid") String uid,@Field("token") String token);
}
