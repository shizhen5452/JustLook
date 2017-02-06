package com.shizhen5452.justlook.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhen5452.justlook.R;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyViewHolder extends RecyclerView.ViewHolder {

    public TextView  mTvTitle;
    public ImageView mIvPic;

    public ZhihuDaliyViewHolder(View itemView) {
        super(itemView);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mIvPic = (ImageView) itemView.findViewById(R.id.iv_pic);
    }
}
