package com.example.asus.wook2.ui.shopping.presenter;

import com.example.asus.wook2.bean.BaseBean;
import com.example.asus.wook2.bean.GetCartsBean;
import com.example.asus.wook2.bean.SellerBean;
import com.example.asus.wook2.net.DeleteCartApi;
import com.example.asus.wook2.net.GetCartsApi;
import com.example.asus.wook2.net.UpdateCartsApi;
import com.example.asus.wook2.ui.base.BasePresenter;
import com.example.asus.wook2.ui.shopping.contract.ShopContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by asus on 2018/5/19.
 */

public class ShopPresenter extends BasePresenter<ShopContract.View> implements ShopContract.Presenter {
    private GetCartsApi getCartsApi;
    private UpdateCartsApi updateCartsApi;
    private DeleteCartApi deleteCartApi;

    @Inject
    public ShopPresenter(GetCartsApi getCartsApi, UpdateCartsApi updateCartsApi, DeleteCartApi deleteCartApi) {
        this.getCartsApi = getCartsApi;
        this.updateCartsApi = updateCartsApi;
        this.deleteCartApi = deleteCartApi;
    }

    @Override
    public void getGetCartsPresenter(String uid, String token) {
        getCartsApi.GetCartsApi(uid,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GetCartsBean, List<GetCartsBean.DataBean>>() {
                    @Override
                    public List<GetCartsBean.DataBean> apply(GetCartsBean getCartsBean) throws Exception {
                        return getCartsBean.getData();
                    }
                }).subscribe(new Consumer<List<GetCartsBean.DataBean>>() {
            @Override
            public void accept(List<GetCartsBean.DataBean> dataBeans) throws Exception {
                List<SellerBean> groupList = new ArrayList<>();//用于封装一级列表
                List<List<GetCartsBean.DataBean.ListBean>> childList = new ArrayList<>();//用于封装二级列表
                for (int i = 0; i < dataBeans.size(); i++) {
                    GetCartsBean.DataBean dataBean = dataBeans.get(i);
                    SellerBean sellerBean = new SellerBean();
                    sellerBean.setSellerName(dataBean.getSellerName());
                    sellerBean.setSellerid(dataBean.getSellerid());
                    sellerBean.setSelected(isSellerProductAllSelect(dataBean));
                    groupList.add(sellerBean);

                    List<GetCartsBean.DataBean.ListBean> list = dataBean.getList();
                    childList.add(list);
                }
                if (mView!=null){
                    mView.getGetCartsApiSuuess(groupList,childList);
                }

            }
        });
    }

    @Override
    public void getupdatePresenter(String uid, String sellerid, String pid, String num, String selected, String token) {
        updateCartsApi.getupdateCartsApi(uid, sellerid, pid, num, selected, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseBean, String>() {
                    @Override
                    public String apply(BaseBean baseBean) throws Exception {
                        return baseBean.getMsg();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (mView != null) {
                    mView.getUpdateCartsApiSuuess();
                }
            }
        });
    }

    @Override
    public void getDeletePresenter(String uid, String pid, String token) {
        deleteCartApi.getDeleteCartApi(uid,pid,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseBean, String>() {
                    @Override
                    public String apply(BaseBean baseBean) throws Exception {
                        return baseBean.getMsg();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (mView != null) {
                    mView.getDeleteCartApiSuuess();
                }
            }
        });
    }
    private boolean isSellerProductAllSelect(GetCartsBean.DataBean dataBean) {
        //获取该商家下面的所有商品
        List<GetCartsBean.DataBean.ListBean> list = dataBean.getList();
        for (int i = 0; i < list.size(); i++) {
            GetCartsBean.DataBean.ListBean listBean = list.get(i);
            if (0 == listBean.getSelected()) {
                //如果是0的话，表示有一个商品未选中
                return false;
            }
        }
        return true;

    }
}
