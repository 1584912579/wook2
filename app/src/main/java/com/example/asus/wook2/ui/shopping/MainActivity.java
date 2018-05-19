package com.example.asus.wook2.ui.shopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.asus.wook2.R;
import com.example.asus.wook2.bean.GetCartsBean;
import com.example.asus.wook2.bean.SellerBean;
import com.example.asus.wook2.component.DaggerHttpComponent;
import com.example.asus.wook2.module.HttpModule;
import com.example.asus.wook2.ui.base.BaseActivity;
import com.example.asus.wook2.ui.shopping.adapter.ElvShopcartAdapter;
import com.example.asus.wook2.ui.shopping.contract.ShopContract;
import com.example.asus.wook2.ui.shopping.presenter.ShopPresenter;

import java.util.List;

public class MainActivity extends BaseActivity<ShopPresenter> implements ShopContract.View {

    private ExpandableListView mElvvv;
    private CheckBox mCbAll;
    private TextView mTvMoney;
    private TextView mTvTotal;
    private ElvShopcartAdapter adapter;
    private String uid="14530";
    private String token="A753C3EF1D5CD17396618D2DF49D1544";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        intview();
        mPresenter.getGetCartsPresenter(uid,token);
    }

    private void intview() {
        mElvvv = (ExpandableListView) findViewById(R.id.elvvv);
        mCbAll = (CheckBox) findViewById(R.id.cbAll);
        mTvMoney = (TextView) findViewById(R.id.tvMoney);
        mTvTotal = (TextView) findViewById(R.id.tvTotal);
        mCbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mCbAll.isChecked();
                adapter.isQuanXuan(checked);
            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void getGetCartsApiSuuess(List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList) {
        //判断所有商家是否全部选中
        mCbAll.setChecked(isSellerAddSelected(groupList));
        adapter= new ElvShopcartAdapter(this,groupList,childList,mPresenter);
        mElvvv.setAdapter(adapter);
        String[] strings = adapter.computeMoneyAndNum();
        mTvMoney.setText("总计：" + strings[0] + "元");
        mTvTotal.setText("去结算("+strings[1]+"个)");
        //默认展开
        for (int i = 0; i <groupList.size() ; i++) {
            mElvvv.expandGroup(i);
        }
    }
    public boolean isSellerAddSelected(List<SellerBean> groupList){
        for (int i = 0; i <groupList.size() ; i++) {
            SellerBean sellerBean = groupList.get(i);
            if (!sellerBean.isSelected()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void getUpdateCartsApiSuuess() {
        if (adapter!=null){
            adapter.updataSuccess();
        }
    }

    @Override
    public void getDeleteCartApiSuuess() {
        if (adapter!=null){
            adapter.delSuccess();
        }
    }
}
