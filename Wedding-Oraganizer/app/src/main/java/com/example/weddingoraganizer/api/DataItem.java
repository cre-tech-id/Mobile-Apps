package com.example.weddingoraganizer.api;

public class DataItem{
	private String pemesan;
	private String penyedia;
	private String lokasi;
	private int id;
	private String tanggal;
	private String paket;
	private String status;

	public void setPemesan(String pemesan){
		this.pemesan = pemesan;
	}

	public String getPemesan(){
		return pemesan;
	}

	public void setPenyedia(String penyedia){
		this.penyedia = penyedia;
	}

	public String getPenyedia(){
		return penyedia;
	}

	public void setLokasi(String lokasi){
		this.lokasi = lokasi;
	}

	public String getLokasi(){
		return lokasi;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setPaket(String paket){
		this.paket = paket;
	}

	public String getPaket(){
		return paket;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"pemesan = '" + pemesan + '\'' + 
			",penyedia = '" + penyedia + '\'' + 
			",lokasi = '" + lokasi + '\'' + 
			",id = '" + id + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",paket = '" + paket + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
