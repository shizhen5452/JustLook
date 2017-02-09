package com.shizhen5452.justlook.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyBean {

    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private List<ZhihuDaliyItemBean>    stories;
    @SerializedName("top_stories")
    private List<ZhihuTopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuDaliyItemBean> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuDaliyItemBean> stories) {
        this.stories = stories;
    }

    public List<ZhihuTopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhihuTopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }



}
