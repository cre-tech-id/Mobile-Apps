package com.example.weddingoraganizer.api;

import java.util.List;

public class GetPenyedia {
    private List<Result> data;

    public List<Result> getResult() {
        return data;
    }

    public void setResult(List<Result> data) {
        this.data = data;
    }

    public static class Result {

        private String penyedia;
        private String alamat;
        private String no;

        private String totalrating;
        private Float ratingbulat;
        private String gambar;

        public String getPenyedia() { return penyedia; }

        public void setPenyedia(String penyedia) { this.penyedia = penyedia; }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setNoHp(String no) {
            this.no = no;
        }

        public String getNo() {
            return no;
        }

        public void setTotalrating(String totalrating) {
            this.totalrating = totalrating;
        }

        public String getTotalrating() {
            return totalrating;
        }

        public void setRatingbulat(Float ratingbulat) {
            this.ratingbulat = ratingbulat;
        }

        public Float getRatingbulat() {
            return ratingbulat;
        }

        public String getGambar() { return gambar; }

        public void setGambar(String gambar) { this.gambar = gambar; }

        @Override
        public String toString() {
            return
                    "DataItem{" +
                            ",penyedia = '" + penyedia + '\'' +
                            ",alamat = '" + no + '\'' +
                            ",no = '" + no + '\'' +
                            ",total rating = '" + totalrating + '\'' +
                            ",rating bulat = '" + ratingbulat + '\'' +
                            ",gambar = '" + gambar + '\'' +
                            "}";
        }
    }
}
