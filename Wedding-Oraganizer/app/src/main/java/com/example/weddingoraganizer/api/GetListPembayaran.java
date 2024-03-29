package com.example.weddingoraganizer.api;

import java.util.List;

public class GetListPembayaran {
    private List<Result> data;

    public List<Result> getResult() {
        return data;
    }

    public void setResult(List<Result> data) {
        this.data = data;
    }

    public static class Result {
        private String tanggal;
        private String nominal;

        public String getTanggal() { return tanggal; }

        public void setTanggal(String tanggal) { this.tanggal = tanggal; }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        public String getNominal() {
            return nominal;
        }

        @Override
        public String toString() {
            return
                    "DataItem{" +
                            ",nominal = '" + nominal + '\'' +
                            ",tanggal = '" + tanggal + '\'' +
                            "}";
        }
    }
}
