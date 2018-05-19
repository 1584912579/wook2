package com.example.asus.wook2.ui.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.wook2.R;
import com.example.asus.wook2.bean.GetCartsBean;
import com.example.asus.wook2.bean.SellerBean;
import com.example.asus.wook2.ui.shopping.presenter.ShopPresenter;
import com.example.asus.wook2.utils.AddSubView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by asus on 2018/5/19.
 */

public class ElvShopcartAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<SellerBean> groupList;
    private List<List<GetCartsBean.DataBean.ListBean>> childList;
    private ShopPresenter mPresenter;
    private LayoutInflater inflater;
    private final String uid="14530";
    private final String token="A753C3EF1D5CD17396618D2DF49D1544";
    private int productIndex;
    private int groupPosition;
    private boolean Checked;
    private static final int GETCARTS = 0;//查询购物车
    private static final int UPDATE_PRODUCT = 1; //更新商品
    private static final int UPDATE_SELLER = 2; //更新卖家
    private static int state = GETCARTS;
    private boolean allSelected;
    public ElvShopcartAdapter(Context context, List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList, ShopPresenter mPresenter) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
        this.mPresenter = mPresenter;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.groupitem, null);
            groupViewHolder.cbSeller = convertView.findViewById(R.id.cbSeller);
            groupViewHolder.tvSeller = convertView.findViewById(R.id.tvSeller);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        //设置值
        SellerBean sellerBean = groupList.get(groupPosition);
        groupViewHolder.tvSeller.setText(sellerBean.getSellerName());
        groupViewHolder.cbSeller.setChecked(sellerBean.isSelected());
        groupViewHolder.cbSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变选重状态
                state = UPDATE_PRODUCT;
                //默认从第一个商品开始更新购物车状态
                productIndex = 0;
                //全局记录一下当前更新的商家
                ElvShopcartAdapter.this.groupPosition = groupPosition;
                Checked = groupViewHolder.cbSeller.isChecked();
                //更新商家下的商品状态
                updateProductInSeller();

            }
        });

        return convertView;
    }



    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.childitem, null);
            childViewHolder.cbProduct = convertView.findViewById(R.id.cbProduct);
            childViewHolder.iv = convertView.findViewById(R.id.iv);
            childViewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            childViewHolder.tvPrice = convertView.findViewById(R.id.tvPrice);
            childViewHolder.tvDel = convertView.findViewById(R.id.tvDel);
            childViewHolder.addSubView = convertView.findViewById(R.id.addSubCard);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final GetCartsBean.DataBean.ListBean listBean = childList.get(groupPosition).get(childPosition);
        //根据服务器返回的select值，给checkBox设置是否选中
        childViewHolder.cbProduct.setChecked(listBean.getSelected() == 1 ? true : false);
        childViewHolder.tvTitle.setText(listBean.getTitle());
        childViewHolder.tvPrice.setText(listBean.getPrice() + "");
        childViewHolder.iv.setImageURI(listBean.getImages().split("\\|")[0]);
        //Glide.with(context).load(listBean.getImages().split("\\|")[0]).into(childViewHolder.iv);
        childViewHolder.addSubView.setNum(listBean.getNum() + "");
        childViewHolder.cbProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = GETCARTS;
                ElvShopcartAdapter.this.groupPosition = groupPosition;
                String sellerid = groupList.get(groupPosition).getSellerid();

                int pid = listBean.getPid();
                int num = listBean.getNum();
                boolean childchecked = childViewHolder.cbProduct.isChecked();
                mPresenter.getupdatePresenter(uid,sellerid,pid+"",num+"",childchecked?"1":"0",token);

            }
        });
        //加
        childViewHolder.addSubView.setAddOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = GETCARTS;
                ElvShopcartAdapter.this.groupPosition = groupPosition;
                String sellerid = groupList.get(groupPosition).getSellerid();

                int pid = listBean.getPid();
                int num = listBean.getNum();
                num+=1;
                boolean childchecked = childViewHolder.cbProduct.isChecked();
                mPresenter.getupdatePresenter(uid,sellerid,pid+"",num+"",childchecked?"1":"0",token);

            }
        });
        //减
        childViewHolder.addSubView.setSubOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = GETCARTS;
                ElvShopcartAdapter.this.groupPosition = groupPosition;
                String sellerid = groupList.get(groupPosition).getSellerid();

                int pid = listBean.getPid();
                int num = listBean.getNum();
                if (num <= 1) {
                    Toast.makeText(context, "数量不能小于1", Toast.LENGTH_SHORT).show();
                    return;
                }
                num-=1;

                boolean childchecked = childViewHolder.cbProduct.isChecked();
                mPresenter.getupdatePresenter(uid,sellerid,pid+"",num+"",childchecked?"1":"0",token);

            }
        });
        //删除
        childViewHolder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = GETCARTS;
                ElvShopcartAdapter.this.groupPosition = groupPosition;
                int pid = listBean.getPid();
                mPresenter.getDeletePresenter(uid,pid+"",token);

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    //删除成功回调接口
    public void delSuccess() {
        mPresenter.getGetCartsPresenter(uid, token);
    }
    class GroupViewHolder {
        CheckBox cbSeller;
        TextView tvSeller;
    }

    class ChildViewHolder {
        CheckBox cbProduct;
        SimpleDraweeView iv;
        TextView tvTitle;
        TextView tvPrice;
        TextView tvDel;
        AddSubView addSubView;
    }
    public void updataSuccess(){
        switch (state){
            case GETCARTS:
                productIndex=0;
                groupPosition=0;
                mPresenter.getGetCartsPresenter(uid,token);
                break;
            case UPDATE_PRODUCT:
                productIndex++;
                if (productIndex<childList.get(groupPosition).size()){
                    updateProductInSeller();
                }else {
                    state = GETCARTS;
                    updataSuccess();
                }
                break;
            case UPDATE_SELLER:
                //遍历所有商家下的商品，并更新状态
                productIndex++;
                //下标是否越界
                if (productIndex < childList.get(groupPosition).size()) {
                    //可以继续跟新商品
                    updateProductInSeller(allSelected);
                } else {
                    //商品已经全部更新完成，请查询购物车
                    productIndex = 0;
                    groupPosition++;
                    if (groupPosition < groupList.size()) {
                        //可以继续跟新商品
                        updateProductInSeller(allSelected);
                    } else {
                        //商品已经全部更新完成，请查询购物车
                        state = GETCARTS;
                        updataSuccess();
                    }
                }
                break;

        }
    }

    private void updateProductInSeller() {
        //获取SellerId
        SellerBean sellerBean = groupList.get(groupPosition);
        String sellerid = sellerBean.getSellerid();
        GetCartsBean.DataBean.ListBean listBean = childList.get(groupPosition).get(productIndex);
        int pid = listBean.getPid();
        int num = listBean.getNum();

        mPresenter.getupdatePresenter(uid,sellerid,pid+"",num+"",Checked?"1":"0",token);
    }
    private void updateProductInSeller(boolean bool) {
        //获取SellerId
        SellerBean sellerBean = groupList.get(groupPosition);
        String sellerid = sellerBean.getSellerid();
        GetCartsBean.DataBean.ListBean listBean = childList.get(groupPosition).get(productIndex);
        int pid = listBean.getPid();
        int num = listBean.getNum();

        mPresenter.getupdatePresenter(uid,sellerid,pid+"",num+"",bool?"1":"0",token);
    }
    //计算数量和价钱

    public String[] computeMoneyAndNum() {
        double sum = 0;
        int num = 0;
        for (int i = 0; i < groupList.size(); i++) {
            for (int j = 0; j < childList.get(i).size(); j++) {
                //判断商品是否选中
                GetCartsBean.DataBean.ListBean listBean = childList.get(i).get(j);
                if (listBean.getSelected() == 1) {
                    //该商品为选中状态
                    sum += listBean.getPrice() * listBean.getNum();
                    num += listBean.getNum();
                }
            }
        }
        return new String[]{sum + "", num + ""};
    }

    //全选
    public void isQuanXuan(boolean bool){
        this.allSelected=bool;
        state=UPDATE_SELLER;
        updateProductInSeller(bool);
    }
}
