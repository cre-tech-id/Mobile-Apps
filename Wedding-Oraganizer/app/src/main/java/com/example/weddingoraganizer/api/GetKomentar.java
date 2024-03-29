package com.example.weddingoraganizer.api;

import java.util.List;

public class GetKomentar {
    private List<GetKomentar.Result> data;

    public List<GetKomentar.Result> getResult() {
        return data;
    }

    public void setResult(List<GetKomentar.Result> data) {
        this.data = data;
    }

    public class Result {
        private String nama;
        private String komentar;
        private String paket;

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getNama() {
            return nama;
        }

        public void setKomentar(String komentar) {
            this.komentar = komentar;
        }

        public String getKomentar() {
            return komentar;
        }

//        public void setPaket(int paket) {
//            this.paket = paket;
//        }
//
//        public int getPaket() {
//            return paket;
//        }

        public void setPaket(String paket) {
            this.paket = paket;
        }

        public String getPaket() {
            return paket;
        }

        @Override
        public String toString() {
            return
                    "DataItem{" +
                            "nama = '" + nama + '\'' +
                            ",paket = '" + paket + '\'' +
                            ",komentar = '" + komentar +
                            "}";
        }
    }
}
