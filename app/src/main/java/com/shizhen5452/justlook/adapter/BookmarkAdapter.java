package com.shizhen5452.justlook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.activity.ZhihuDetailActivity;
import com.shizhen5452.justlook.bean.ZhihuDetailBean;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.viewholder.BookmarkViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by EminemShi on 2017/2/11
 */

public class BookmarkAdapter extends RecyclerView.Adapter {
    private List<ZhihuDetailBean> mZhihuDetailBeanList=new ArrayList<>();
    private Context mContext;

    public BookmarkAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ZhihuDetailBean zhihuDetailBean = mZhihuDetailBeanList.get(position);
        BookmarkViewHolder viewHolder= (BookmarkViewHolder) holder;
        viewHolder.mTv.setText(zhihuDetailBean.getTitle());
        Glide.with(mContext.getApplicationContext()).load(zhihuDetailBean.getImages().get(0)).into(viewHolder.mIv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goZhihuDetailActivity(zhihuDetailBean);
            }
        });
    }

    private void goZhihuDetailActivity(ZhihuDetailBean zhihuDetailBean) {
        Intent intent = new Intent(mContext, ZhihuDetailActivity.class);
        intent.putExtra(Constant.ZHIHU_DALIY_ID,zhihuDetailBean.getId());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mZhihuDetailBeanList==null?0:mZhihuDetailBeanList.size();
    }

    public void addItems(List<ZhihuDetailBean> zhihuDetailBeanList) {
        mZhihuDetailBeanList.clear();
        mZhihuDetailBeanList.addAll(zhihuDetailBeanList);
        notifyDataSetChanged();
    }
}
