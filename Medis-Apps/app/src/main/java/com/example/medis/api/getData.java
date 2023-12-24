package com.example.medis.api;

import java.util.List;

public class getData {
    private List<getData.Result> data;

    public List<getData.Result> getResult() {
        return data;
    }

    public void setResult(List<getData.Result> data) {
        this.data = data;
    }

    public class Result {
        private int id;
        private String img;
        private String title;
        private String desc;


        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return img;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return
                    "DataItem{" +
                            "id = '" + id + '\'' +
                            ",img= '" + img + '\'' +
                            ", title= '" + title + '\'' +
                            ",desc = '" + desc + '\'' +
                            "}";
        }
    }
}