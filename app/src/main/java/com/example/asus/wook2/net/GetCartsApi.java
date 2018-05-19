package com.example.asus.wook2.net;

import com.example.asus.wook2.bean.GetCartsBean;

import io.reactivex.Observable;

/**
 * Created by asus on 2018/5/19.
 */

public class GetCartsApi {
    private static GetCartsApi getCartsApi;
    private GetCartsApiService getCartsApiService;

    public GetCartsApi(GetCartsApiService getCartsApiService) {
        this.getCartsApiService = getCartsApiService;
    }
    public static GetCartsApi getGetCartsApi(GetCartsApiService getCartsApiService){
        if (getCartsApi==null){
            getCartsApi=new GetCartsApi(getCartsApiService);
        }
        return getCartsApi;
    }
    public Observable<GetCartsBean> GetCartsApi(String uid, String token) {
        return getCartsApiService.getGetCarts(uid,token);
    }
}
