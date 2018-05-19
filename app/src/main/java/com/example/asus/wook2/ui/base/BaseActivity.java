package com.example.asus.wook2.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.asus.wook2.inter.IBase;

import javax.inject.Inject;

/**
 * Created by asus on 2018/5/19.
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity implements  BaseContract.BaseView,IBase {
    @Inject
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        inject();

        if (mPresenter!=null){
            mPresenter.attchView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
