package com.example.weddingoraganizer.api;

import java.util.List;

public class StatusPemesanan {
    private List<Result> data;

    public List<Result> getResult() {
        return data;
    }

    public void setResult(List<Result> data) {
        this.data = data;
    }

    public class Result {
        private String pemesan;
        private String penyedia;
        private String lokasi;
        private int id;
        private int id_paket;
        private String userid;
        private String penyediaid;
        private String tanggal;
        private String paket;
        private String status;
        private Integer pembayaran;
        private Integer harga_paket;

        private Integer dp;
        private String con_pembayaran;
        private String selesai;

        public void setPemesan(String pemesan) {
            this.pemesan = pemesan;
        }

        public String getPemesan() {
            return pemesan;
        }

        public void setPenyedia(String penyedia) {
            this.penyedia = penyedia;
        }

        public String getPenyedia() {
            return penyedia;
        }

        public void setLokasi(String lokasi) {
            this.lokasi = lokasi;
        }

        public String getLokasi() {
            return lokasi;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId_paket(int id_paket) {
            this.id_paket = id_paket;
        }

        public int getId_paket() {
            return id_paket;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUserid() {
            return userid;
        }

        public void setPenyediaid(String penyediaid) {
            this.penyediaid =penyediaid;
        }

        public String getPenyediaid() {
            return penyediaid;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setPaket(String paket) {
            this.paket = paket;
        }

        public String getPaket() {
            return paket;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setPembayaran(Integer pembayaran) {
            this.pembayaran = pembayaran;
        }

        public Integer getPembayaran() {
            return pembayaran;
        }

        public void setDp(Integer dp) {
            this.dp = dp;
        }

        public Integer getDp() {
            return dp;
        }

        public void setHarga_paket(Integer harga_paket) {
            this.harga_paket = harga_paket;
        }

        public Integer getHarga_paket() {
            return harga_paket;
        }

        public void setConPembayaran(String con_pembayaran) {
            this.con_pembayaran = con_pembayaran;
        }

        public String getConPembayaran() {
            return con_pembayaran;
        }

        public void setSelesai(String selesai) {
            this.selesai = selesai;
        }

        public String getSelesai() {
            return selesai;
        }

        @Override
        public String toString() {
            return pemesan + ";" +
                   penyedia + ";" +
                   lokasi + ";" +
                   id + ";" +
                   tanggal + ";" +
                   paket + ";" +
                   status + ";" +
                   pembayaran + ";" +
                    dp + ";" +
                    harga_paket + ";" +
                   selesai + ";";
        }
    }
}