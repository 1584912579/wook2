package com.example.asus.wook2.ui.base;

/**
 * Created by asus on 2018/5/19.
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    protected T mView;
    @Override
    public void attchView(T view) {
        this.mView=view;
    }

    @Override
    public void detachView() {
        if (mView!=null){
            mView=null;
        }
    }
}
