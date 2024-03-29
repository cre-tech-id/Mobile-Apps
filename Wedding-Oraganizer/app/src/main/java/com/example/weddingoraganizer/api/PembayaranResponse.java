package com.example.weddingoraganizer.api;

import java.util.List;

public class PembayaranResponse {
    private List<Result> data;

    public List<Result> getResult() {
        return data;
    }

    public void setResult(List<Result> data) {
        this.data = data;
    }

    public class Result {

        private String lunas;
        private String dp;

        public String getLunas() { return lunas; }

        public void setLunas(String lunas) { this.lunas = lunas; }

        public String getDp() { return dp; }

        public void setDp(String dp) { this.dp = dp; }



        @Override
        public String toString() {
            return
                    lunas;
        }
    }
}