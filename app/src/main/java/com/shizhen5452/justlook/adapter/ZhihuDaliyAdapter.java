package com.shizhen5452.justlook.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shizhen5452.justlook.R;
import com.shizhen5452.justlook.bean.ZhihuDaliyItemBean;
import com.shizhen5452.justlook.utils.Constant;
import com.shizhen5452.justlook.utils.DBUtils;

/**
 * Create by EminemShi on 2017/2/18
 */

public class ZhihuDaliyAdapter extends BaseQuickAdapter<ZhihuDaliyItemBean, BaseViewHolder> {

    public ZhihuDaliyAdapter() {
        super(R.layout.item_zhihu_daliy);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhihuDaliyItemBean item) {
        if (DBUtils.getDB(mContext).isRead(Constant.ZHIHU, item.getId() + "", 1)) {
            helper.setTextColor(R.id.tv_title,Color.GRAY);
        } else {
            helper.setTextColor(R.id.tv_title,Color.BLACK);
        }
        helper.setText(R.id.tv_title,item.getTitle());
        Glide.with(mContext).load(item.getImages()[0]).into((ImageView)helper.getView(R.id.iv_pic));
    }
}
