package com.example.asus.wook2.ui.base;

/**
 * Created by asus on 2018/5/19.
 */

public interface BaseContract {
    interface BasePresenter<T extends  BaseView>{
        //绑定
        void attchView(T view);
        //解绑
        void detachView();
    }

    interface BaseView {
        void showLoading();
        void dismissLoading();
    }
}
