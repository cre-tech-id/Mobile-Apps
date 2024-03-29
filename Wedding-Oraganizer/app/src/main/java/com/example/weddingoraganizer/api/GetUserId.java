package com.example.weddingoraganizer.api;

import java.util.List;

public class GetUserId {
    private List<Result> data;

    public List<Result> getResult() {
        return data;
    }

    public void setResult(List<Result> data) {
        this.data = data;
    }

    public class Result {
        private String nama;
        private String no_hp;
        private String updatedAt;
        private int roleId;
        private String createdAt;
        private int id;
        private String gambar;
        private String email;
        private String detail_alamat;
        private String nama_kota;
        private String nama_kecamatan;
        private String nama_kelurahan;

        public void setNama(String nama){
            this.nama = nama;
        }

        public String getNama(){
            return nama;
        }

        public void setGambar(String gambar){
            this.gambar = gambar;
        }

        public String getGambar(){
            return gambar;
        }

        public void setAlamat(String detail_alamat){
            this.detail_alamat = detail_alamat;
        }

        public String getAlamat(){
            return detail_alamat;
        }

        public void setKota(String kota){
            this.nama_kota = kota;
        }

        public String getKota(){
            return nama_kota;
        }

        public void setKecamatan(String kecamatan){
            this.nama_kecamatan = kecamatan;
        }

        public String getKecamatan(){
            return nama_kecamatan;
        }

        public void setKelurahan(String nama_kelurahan){
            this.nama_kelurahan = nama_kelurahan;
        }

        public String getKelurahan(){
            return nama_kelurahan;
        }

        public void setno_hp(String no_hp){
            this.no_hp = no_hp;
        }

        public String getno_hp(){
            return no_hp;
        }

        @Override
        public String toString(){
            return nama + "," +
                    nama_kota + "," +
                    nama_kecamatan + "," +
                    nama_kelurahan + "," +
                    detail_alamat + "," +
                    no_hp + "," +
                    gambar;

        }
    }
}
