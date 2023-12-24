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

	public class Result {

		private String nama;
		private String noHp;
		private int id;
		private String alamat;

		public void setNama(String nama) {
			this.nama = nama;
		}

		public String getNama() {
			return nama;
		}

		public void setNoHp(String noHp) {
			this.noHp = noHp;
		}

		public String getNoHp() {
			return noHp;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setAlamat(String alamat) {
			this.alamat = alamat;
		}

		public String getAlamat() {
			return alamat;
		}

		@Override
		public String toString() {
			return
					"DataItem{" +
							"nama = '" + nama + '\'' +
							",no_hp = '" + noHp + '\'' +
							",id = '" + id + '\'' +
							",alamat = '" + alamat + '\'' +
							"}";
		}
	}
}