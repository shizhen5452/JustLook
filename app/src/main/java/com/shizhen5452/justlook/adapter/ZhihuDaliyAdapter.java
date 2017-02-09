package com.shizhen5452.justlook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.activity.ZhihuDetailActivity;
import com.shizhen5452.justlook.bean.ZhihuDaliyItemBean;
import com.shizhen5452.justlook.db.DBUtils;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.viewholder.LoadingMoreViewHolder;
import com.shizhen5452.justlook.viewholder.ZhihuDaliyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyAdapter extends RecyclerView.Adapter{
    private List<ZhihuDaliyItemBean> mZhihuDaliyItemBeanList=new ArrayList<>();
    private static final int TYPE_NORMAL       = 1;
    private static final int TYPE_LOADING_MORE = 2;
    private Context mContext;
    private boolean isLoadingMore;


    public ZhihuDaliyAdapter(Context context) {
        mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                View zhihuDaliyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhihu_daliy, parent, false);
                return new ZhihuDaliyViewHolder(zhihuDaliyView);
            case TYPE_LOADING_MORE:
                View loadingMoreView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false);
                return new LoadingMoreViewHolder(loadingMoreView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_NORMAL:
                onBindZhihuDaliyViewHolder((ZhihuDaliyViewHolder)holder,position);
                break;
            case TYPE_LOADING_MORE:
                onBindLoadingMoreViewHolder((LoadingMoreViewHolder)holder,position);
                break;
        }

    }

    private void onBindLoadingMoreViewHolder(LoadingMoreViewHolder holder, int position) {
        holder.mLoadingMoreProgressBar.setVisibility(isLoadingMore==true?View.VISIBLE:View.GONE);
    }

    private void onBindZhihuDaliyViewHolder(final ZhihuDaliyViewHolder holder, int position) {
        final ZhihuDaliyItemBean zhihuDaliyItemBean = mZhihuDaliyItemBeanList.get(position);
        if (DBUtils.getDB(mContext).isRead(Constant.ZHIHU, zhihuDaliyItemBean.getId() + "", 1)) {
            holder.mTvTitle.setTextColor(Color.GRAY);
        } else {
            holder.mTvTitle.setTextColor(Color.BLACK);
        }
        holder.mTvTitle.setText(zhihuDaliyItemBean.getTitle());
        Glide.with(mContext.getApplicationContext()).load(zhihuDaliyItemBean.getImages()[0]).into(holder.mIvPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goZhihuDetailActivity(holder,zhihuDaliyItemBean);
            }
        });
    }

    private void goZhihuDetailActivity(ZhihuDaliyViewHolder holder, ZhihuDaliyItemBean zhihuDaliyItemBean) {
        DBUtils.getDB(mContext).putIsRead(Constant.ZHIHU,zhihuDaliyItemBean.getId()+"",1);
        holder.mTvTitle.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, ZhihuDetailActivity.class);
        intent.putExtra(Constant.ZHIHU_DALIY_ID,zhihuDaliyItemBean.getId());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mZhihuDaliyItemBeanList == null ? 0 : mZhihuDaliyItemBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mZhihuDaliyItemBeanList.size()&&mZhihuDaliyItemBeanList.size()>0) {
            return TYPE_NORMAL;
        }
        return TYPE_LOADING_MORE;
    }


    public void clearData() {
        mZhihuDaliyItemBeanList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<ZhihuDaliyItemBean> stories) {
        mZhihuDaliyItemBeanList.addAll(stories);
        notifyDataSetChanged();
    }
}

