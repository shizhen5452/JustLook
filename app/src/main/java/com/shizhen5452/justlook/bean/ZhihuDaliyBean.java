package com.shizhen5452.justlook.bean;

import java.util.List;

/**
 * Create by EminemShi on 2017/2/6
 */

public class ZhihuDaliyBean {

    /**
     * date : 20170206
     * stories : [{"ga_prefix":"020611","id":9198830,"images":["http://pic1.zhimg.com/f1204da91b9b1d71039a53679e6a08c8.jpg"],"title":"是艺术家也是时尚 icon，这是她的传奇一生","type":0},{"ga_prefix":"020610","id":9195071,"images":["http://pic4.zhimg.com/e84ea108c8967815724f966a823d2d0b.jpg"],"title":"我们感知这个世界的方式，从一出生就决定了","type":0},{"ga_prefix":"020609","id":9197561,"images":["http://pic3.zhimg.com/8cec702dfeac127c1f6df3b716d86792.jpg"],"title":"每天都离不开便利店，不过它们真的能赚到钱吗？","type":0},{"ga_prefix":"020608","id":9198585,"images":["http://pic4.zhimg.com/64a5ace8534ae7f562badfa7e4c3cfef.jpg"],"title":"美国发生过的杠杆收购大潮，会不会在中国出现？","type":0},{"ga_prefix":"020607","id":9198401,"images":["http://pic2.zhimg.com/13b4384639ad1a23788836f9d915032d.jpg"],"multipic":true,"title":"养猫的薛定谔说，生命现象原则上都可以用物理学来解释","type":0},{"ga_prefix":"020607","id":9170945,"images":["http://pic1.zhimg.com/2bbf77cf77e430910b84674ee26d14bc.jpg"],"title":"那些好玩的 3D 立体图如何「骗」了你的眼睛？","type":0},{"ga_prefix":"020607","id":9197566,"images":["http://pic3.zhimg.com/d114542662ddb6b3fb094356fa3e435e.jpg"],"title":"别只盯着大疆，无人机市场还有很多机会","type":0},{"ga_prefix":"020606","id":9194647,"images":["http://pic4.zhimg.com/3aba8bf71c97b9e81a7d237d8c566dc3.jpg"],"title":"瞎扯 · 如何正确地吐槽","type":0}]
     * top_stories : [{"ga_prefix":"020609","id":9197561,"image":"http://pic2.zhimg.com/8ded454b2f60b6f013e8b8c9cb638bd1.jpg","title":"每天都离不开便利店，不过它们真的能赚到钱吗？","type":0},{"ga_prefix":"020607","id":9197566,"image":"http://pic1.zhimg.com/acb96fcca7a65cdfaa323397cf786980.jpg","title":"别只盯着大疆，无人机市场还有很多机会","type":0},{"ga_prefix":"020514","id":9197108,"image":"http://pic3.zhimg.com/823b85cc17c2dac1c3a1ee5f30d7f48a.jpg","title":"年都过完了，还是不知道「金鸡贺岁」的金鸡是什么鸡？","type":0},{"ga_prefix":"020513","id":9197196,"image":"http://pic1.zhimg.com/0a419f36a87e29e456dcd0bc7c83028c.jpg","title":"被「超高辐射量」吓到，你应该了解一下出事的福岛核电站","type":0},{"ga_prefix":"020510","id":9195639,"image":"http://pic1.zhimg.com/19fe6240335c53e25a88613007a80de8.jpg","title":"广告、AI 和视频，这是 Facebook 眼下的三个重点","type":0}]
     */

    private String date;
    private List<StoriesBean>    stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * ga_prefix : 020611
         * id : 9198830
         * images : ["http://pic1.zhimg.com/f1204da91b9b1d71039a53679e6a08c8.jpg"]
         * title : 是艺术家也是时尚 icon，这是她的传奇一生
         * type : 0
         * multipic : true
         */

        private String ga_prefix;
        private int          id;
        private String       title;
        private int          type;
        private boolean      multipic;
        private List<String> images;

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

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * ga_prefix : 020609
         * id : 9197561
         * image : http://pic2.zhimg.com/8ded454b2f60b6f013e8b8c9cb638bd1.jpg
         * title : 每天都离不开便利店，不过它们真的能赚到钱吗？
         * type : 0
         */

        private String ga_prefix;
        private int    id;
        private String image;
        private String title;
        private int    type;

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
    }
}
