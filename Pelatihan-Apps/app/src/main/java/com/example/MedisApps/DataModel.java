package com.medis.MedisApps;

import java.util.List;

public class DataModel {
    private List<Result> data;

    public List<Result> getResult() {
        return data;
    }

    public class Result {

        private String nipd;

        public String getNipd() {
            return nipd;
        }

        public void setNipd(String nipd) {
            this.nipd = nipd;
        }

        public String getH1() {
            return h1;
        }

        public void setH1(String h1) {
            this.h1 = h1;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getH2() {
            return h2;
        }

        public void setH2(String h2) {
            this.h2 = h2;
        }

        private String h1;
        private int id;
        private String h2;


        @Override
        public String toString() {
            return
                    "DataItem{" +
                            "nipd = '" + nipd + '\'' +
                            ",hasil = '" + h1 + '\'' +
                            ",id = '" + id + '\'' +
                            "jurusan = '" + h2 + '\'' +
                            "}";
        }
    }
}