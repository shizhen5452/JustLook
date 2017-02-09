package com.shizhen5452.justlook.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.shizhen5452.justlook.R;

/**
 * Create by EminemShi on 2017/2/6
 */

public class LoadingMoreViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar  mLoadingMoreProgressBar;

    public LoadingMoreViewHolder(View itemView) {
        super(itemView);
        mLoadingMoreProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar_loading_more);
    }
}
