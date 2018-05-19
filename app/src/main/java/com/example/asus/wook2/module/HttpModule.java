package com.example.asus.wook2.module;

import com.example.asus.wook2.net.DeleteCartApi;
import com.example.asus.wook2.net.DeleteCartApiServlce;
import com.example.asus.wook2.net.GetCartsApi;
import com.example.asus.wook2.net.GetCartsApiService;
import com.example.asus.wook2.net.UpdateCartsApi;
import com.example.asus.wook2.net.UpdateCartsApiService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus on 2018/5/19.
 */
@Module
public class HttpModule {
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
    }
    @Provides
    GetCartsApi provideGetCartsApi(OkHttpClient.Builder builder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        GetCartsApiService getCartsApiService = retrofit.create(GetCartsApiService.class);
        return GetCartsApi.getGetCartsApi(getCartsApiService);
    }
    @Provides
    UpdateCartsApi provideUpdateCartsApi(OkHttpClient.Builder builder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        UpdateCartsApiService updateCartsApiService = retrofit.create(UpdateCartsApiService.class);
        return UpdateCartsApi.getupdateCartsApi(updateCartsApiService);
    }
    @Provides
    DeleteCartApi provideDeleteCartApi(OkHttpClient.Builder builder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        DeleteCartApiServlce deleteCartApiServlce = retrofit.create(DeleteCartApiServlce.class);
        return DeleteCartApi.getDeleteCartApi(deleteCartApiServlce);
    }
}
