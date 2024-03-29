package com.example.weddingoraganizer.api;

import java.util.List;

public class EventItem {
    private List<EventItem.Result> data;

    public List<EventItem.Result> getResult() {
        return data;
    }

    public void setResult(List<EventItem.Result> data) {
        this.data = data;
    }

    public class Result {

        private String penyedia;
        private String lokasi;
        private int id;
        private String tanggal;
        private String deskripsi;
        private String event;
        private String gambar;

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

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setDeskripsi(String deskripsi) {
            this.deskripsi = deskripsi;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getEvent() {
            return event;
        }

        public String getGambar() {
            return gambar;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }

        @Override
        public String toString() {
            return
                    "DataItem{" +
                            "penyedia = '" + penyedia + '\'' +
                            ",lokasi = '" + lokasi + '\'' +
                            ",id = '" + id + '\'' +
                            ",tanggal = '" + tanggal + '\'' +
                            ",deskripsi = '" + deskripsi + '\'' +
                            ",nama_event = '" + event + '\'' +
                            ",gambar = '" + gambar + '\'' +
                            "}";
        }
    }
}