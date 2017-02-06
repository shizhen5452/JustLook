package com.shizhen5452.justlook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.bean.ZhihuDaliyBean;
import com.shizhen5452.justlook.utils.DateUtils;
import com.shizhen5452.justlook.viewholder.ZhihuDaliyDateViewHolder;
import com.shizhen5452.justlook.viewholder.ZhihuDaliyViewHolder;

import java.util.List;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyAdapter extends RecyclerView.Adapter {
    private List<ZhihuDaliyBean.StoriesBean> mStoriesBeanList;
    private String                           mDate;
    private static final int TYPE_NO_DATE   = 1;
    private static final int TYPE_WITH_DATE = 2;
    private Context mContext;

    public ZhihuDaliyAdapter(String date, List<ZhihuDaliyBean.StoriesBean> storiesBeanList) {
        mDate = date;
        mStoriesBeanList = storiesBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_NO_DATE:
                View noDateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhihu_daliy, parent, false);
                return new ZhihuDaliyViewHolder(noDateView);
            case TYPE_WITH_DATE:
                View withDateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhihu_daliy_date, parent, false);
                return new ZhihuDaliyDateViewHolder(withDateView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ZhihuDaliyBean.StoriesBean storiesBean = mStoriesBeanList.get(position);
        if (holder instanceof ZhihuDaliyViewHolder) {
            ZhihuDaliyViewHolder zhihuDaliyViewHolder = (ZhihuDaliyViewHolder) holder;
            zhihuDaliyViewHolder.mTvTitle.setText(storiesBean.getTitle());
            Glide.with(mContext.getApplicationContext()).load(storiesBean.getImages().get(0)).into(zhihuDaliyViewHolder.mIvPic);
        } else if (holder instanceof ZhihuDaliyDateViewHolder) {
            ZhihuDaliyDateViewHolder zhihuDaliyDateViewHolder = (ZhihuDaliyDateViewHolder) holder;
            zhihuDaliyDateViewHolder.mTvTitle.setText(storiesBean.getTitle());
            Glide.with(mContext.getApplicationContext()).load(storiesBean.getImages().get(0)).into(zhihuDaliyDateViewHolder.mIvPic);
            zhihuDaliyDateViewHolder.mTvDate.setText(DateUtils.getDate(mDate));
        }
        final int id = storiesBean.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnZhihuItemClickListener != null) {
                    mOnZhihuItemClickListener.onZhihuItemClickListener(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStoriesBeanList == null ? 0 : mStoriesBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_WITH_DATE;
        }
        return TYPE_NO_DATE;
    }

    public interface OnZhihuItemClickListener {
        void onZhihuItemClickListener(int id);
    }

    private OnZhihuItemClickListener mOnZhihuItemClickListener;

    public void setOnZhihuItemClickListener(OnZhihuItemClickListener onZhihuItemClickListener) {
        mOnZhihuItemClickListener = onZhihuItemClickListener;
    }
}

