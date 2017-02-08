package com.shizhen5452.justlook.bean;

import java.util.List;

/**
 * Create by EminemShi on 2017/2/8
 */
public class ImageResponseBean {

    /**
     * reason :
     * data : {"images":[{"pub_time":"2017-02-08","description":"花正当春，人亦年少，替别人着想，为自己而活，且行且珍惜。","description_en":"","height":"1125","photo_user":{"user_id":0,"user_photo":"http://static.zuimeia.com/product/img/detail_portrait_default.png","user_name":"","user_link":""},"width":"1266","up_times":231,"desc_user":{"user_id":143970,"user_photo":"http://q.qlogo.cn/qqapp/101105874/D21774ECCF29B2BB7E078BB9C2D7509E/100","user_name":"LYZ.M"},"image_url":"images/8c894f0788645e0691d1af873b4f898f_1266x1125.jpg","id":13642}],"has_next":true,"base_url":"http://wpstatic.zuimeia.com/"}
     * result : 1
     */

    private String reason;
    private DataBean data;
    private int      result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static class DataBean {
        /**
         * images : [{"pub_time":"2017-02-08","description":"花正当春，人亦年少，替别人着想，为自己而活，且行且珍惜。","description_en":"","height":"1125","photo_user":{"user_id":0,"user_photo":"http://static.zuimeia.com/product/img/detail_portrait_default.png","user_name":"","user_link":""},"width":"1266","up_times":231,"desc_user":{"user_id":143970,"user_photo":"http://q.qlogo.cn/qqapp/101105874/D21774ECCF29B2BB7E078BB9C2D7509E/100","user_name":"LYZ.M"},"image_url":"images/8c894f0788645e0691d1af873b4f898f_1266x1125.jpg","id":13642}]
         * has_next : true
         * base_url : http://wpstatic.zuimeia.com/
         */

        private boolean has_next;
        private String           base_url;
        private List<ImagesBean> images;

        public boolean isHas_next() {
            return has_next;
        }

        public void setHas_next(boolean has_next) {
            this.has_next = has_next;
        }

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * pub_time : 2017-02-08
             * description : 花正当春，人亦年少，替别人着想，为自己而活，且行且珍惜。
             * description_en :
             * height : 1125
             * photo_user : {"user_id":0,"user_photo":"http://static.zuimeia.com/product/img/detail_portrait_default.png","user_name":"","user_link":""}
             * width : 1266
             * up_times : 231
             * desc_user : {"user_id":143970,"user_photo":"http://q.qlogo.cn/qqapp/101105874/D21774ECCF29B2BB7E078BB9C2D7509E/100","user_name":"LYZ.M"}
             * image_url : images/8c894f0788645e0691d1af873b4f898f_1266x1125.jpg
             * id : 13642
             */

            private String pub_time;
            private String        description;
            private String        description_en;
            private String        height;
            private PhotoUserBean photo_user;
            private String        width;
            private int           up_times;
            private DescUserBean  desc_user;
            private String        image_url;
            private int           id;

            public String getPub_time() {
                return pub_time;
            }

            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDescription_en() {
                return description_en;
            }

            public void setDescription_en(String description_en) {
                this.description_en = description_en;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public PhotoUserBean getPhoto_user() {
                return photo_user;
            }

            public void setPhoto_user(PhotoUserBean photo_user) {
                this.photo_user = photo_user;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public int getUp_times() {
                return up_times;
            }

            public void setUp_times(int up_times) {
                this.up_times = up_times;
            }

            public DescUserBean getDesc_user() {
                return desc_user;
            }

            public void setDesc_user(DescUserBean desc_user) {
                this.desc_user = desc_user;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public static class PhotoUserBean {
                /**
                 * user_id : 0
                 * user_photo : http://static.zuimeia.com/product/img/detail_portrait_default.png
                 * user_name :
                 * user_link :
                 */

                private int user_id;
                private String user_photo;
                private String user_name;
                private String user_link;

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getUser_photo() {
                    return user_photo;
                }

                public void setUser_photo(String user_photo) {
                    this.user_photo = user_photo;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getUser_link() {
                    return user_link;
                }

                public void setUser_link(String user_link) {
                    this.user_link = user_link;
                }
            }

            public static class DescUserBean {
                /**
                 * user_id : 143970
                 * user_photo : http://q.qlogo.cn/qqapp/101105874/D21774ECCF29B2BB7E078BB9C2D7509E/100
                 * user_name : LYZ.M
                 */

                private int user_id;
                private String user_photo;
                private String user_name;

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getUser_photo() {
                    return user_photo;
                }

                public void setUser_photo(String user_photo) {
                    this.user_photo = user_photo;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }
            }
        }
    }
}
