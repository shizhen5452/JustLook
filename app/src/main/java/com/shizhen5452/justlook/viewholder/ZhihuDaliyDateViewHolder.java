package com.shizhen5452.justlook.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhen5452.justlook.R;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyDateViewHolder extends RecyclerView.ViewHolder {

    public TextView  mTvDate;
    public TextView  mTvTitle;
    public ImageView mIvPic;

    public ZhihuDaliyDateViewHolder(View itemView) {
        super(itemView);
        mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mIvPic = (ImageView) itemView.findViewById(R.id.iv_pic);
    }
}
