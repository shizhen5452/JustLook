package com.shizhen5452.justlook.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhen5452.justlook.R;

/**
 * Create by EminemShi on 2017/2/11
 */

public class BookmarkViewHolder extends RecyclerView.ViewHolder {

    public TextView mTv;
    public ImageView mIv;

    public BookmarkViewHolder(View itemView) {
        super(itemView);
        mIv = (ImageView) itemView.findViewById(R.id.iv_bookmark);
        mTv = (TextView) itemView.findViewById(R.id.tv_bookmark);
    }
}
