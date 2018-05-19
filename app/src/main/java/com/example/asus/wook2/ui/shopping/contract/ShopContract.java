package com.example.asus.wook2.ui.shopping.contract;

import com.example.asus.wook2.bean.GetCartsBean;
import com.example.asus.wook2.bean.SellerBean;
import com.example.asus.wook2.ui.base.BaseContract;

import java.util.List;

/**
 * Created by asus on 2018/5/19.
 */

public interface ShopContract {
    interface View extends BaseContract.BaseView{
        void getGetCartsApiSuuess(List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList);
        void getUpdateCartsApiSuuess();
        void getDeleteCartApiSuuess();
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getGetCartsPresenter(String uid,String token);
        void getupdatePresenter(String uid, String sellerid, String pid, String num, String selected, String token);
        void getDeletePresenter(String uid, String pid, String token);
    }
}
