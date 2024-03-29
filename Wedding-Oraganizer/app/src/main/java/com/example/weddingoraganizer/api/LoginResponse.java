package com.example.weddingoraganizer.api;

import java.util.List;

public class LoginResponse {
    private List<Result> data;
    public List<Result> getResult() {
        return data;
    }

    public void setResult(List<Result> data) {
        this.data = data;
    }

    public class Result {
        private String nama;
        private String no;
        private String updatedAt;
        private int roleId;
        private String createdAt;
        private int id;
        private String gambar;
        private String email;
        private String alamat;
        private String message;
        private boolean status;

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message + status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public boolean isStatus() {
            return status;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getNama() {
            return nama;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getNo() {
            return no;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }

        public String getGambar() {
            return gambar;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getAlamat() {
            return alamat;
        }
        @Override
        public String toString() {
            return id+
                    "," + nama +
                    "," + alamat+
                    "," + gambar+
                    "," + no;
        }
    }
    }
