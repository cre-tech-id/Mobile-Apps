package com.example.weddingoraganizer.api;

import java.util.List;

public class StoreItem {
	private List<Result> data;

	public List<Result> getResult() {
		return data;
	}

	public void setResult(List<Result> data) {
		this.data = data;
	}

	public static class Result {

		private String nama;
		private String no;
		private String id;
		private String alamat;
		private String gambar;

		private String status;

		private String gambaruser;

//		paket
		private String nama_paket;
		private String penyedia;
		private String detail;
		private String harga;
		private String dp;
		private String totalrating;
		private Float ratingbulat;

		public Result(String s) {
		}

		public String getNama_paket() { return nama_paket; }

		public void setNama_paket(String nama_paket) { this.nama_paket = nama_paket; }

		public String getPenyedia() { return penyedia; }

		public void setPenyedia(String penyedia) { this.penyedia = penyedia; }

		public String getStatus() { return status; }

		public void setStatus(String status) { this.status = status; }

		public String getDetail() { return detail; }

		public void setDetail(String detail) { this.detail = detail; }

		public String getHarga() { return harga; }

		public void setHarga(String harga) { this.harga = harga; }

		public String getGambar() { return gambar; }

		public void setGambar(String gambar) { this.gambar = gambar; }

		public String getDp() { return dp; }

		public void setDp(String dp) { this.dp = dp; }

		public String getGambaruser() { return gambaruser; }

		public void setGambaruser(String gambaruser) { this.gambaruser = gambaruser; }


//penyedia
		public void setNama(String nama) {
			this.nama = nama;
		}

		public String getNama() {
			return nama;
		}

		public void setNoHp(String no) {
			this.no = no;
		}

		public String getNo() {
			return no;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setAlamat(String alamat) {
			this.alamat = alamat;
		}

		public String getAlamat() {
			return alamat;
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

		@Override
		public String toString() {
			return
					"DataItem{" +
							"id = '" + id + '\'' +
							"nama = '" + nama + '\'' +
							",penyedia = '" + penyedia + '\'' +
							",id = '" + id + '\'' +
							",detail = '" + detail + '\'' +
							",harga = '" + harga + '\'' +
							",gambar = '" + gambar + '\'' +
							",no = '" + no + '\'' +
							"}";
		}
	}
}