package com.shizhen5452.justlook.bean;

import java.util.List;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDetailBean {

    /**
     * body :
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     * ga_prefix : 050615
     * id : 3892357
     * image : http://p4.zhimg.com/30/59/30594279d368534c6c2f91b2c00c7806.jpg
     * image_source : Angel Abril Ruiz / CC BY
     * images : ["http://p3.zhimg.com/69/d0/69d0ab1bde1988bd475bc7e0a25b713e.jpg"]
     * js : []
     * share_url : http://daily.zhihu.com/story/3892357
     * title : 卖衣服的新手段：把耐用品变成「不停买新的」
     * type : 0
     */

    private String body;
    private String       ga_prefix;
    private int          id;
    private String       image;
    private String       image_source;
    private String       share_url;
    private String       title;
    private int          type;
    private List<String> css;
    private List<String> images;
    private List<?>      js;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }
}
