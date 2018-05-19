package com.example.asus.wook2.net;


import com.example.asus.wook2.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by asus on 2018/5/17.
 */

public interface UpdateCartsApiService {
    @FormUrlEncoded
    @POST("product/updateCarts")
    Observable<BaseBean> updateCarts(@Field("uid") String uid,
                                     @Field("sellerid") String sellerid,
                                     @Field("pid") String pid,
                                     @Field("num") String num,
                                     @Field("selected") String selected,
                                     @Field("token") String token);

}
