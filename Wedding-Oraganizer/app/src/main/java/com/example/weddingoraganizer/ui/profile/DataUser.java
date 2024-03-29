package com.example.weddingoraganizer.ui.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataUser implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("nama")
    private String nama;

    @SerializedName("kota")
    private String kota;

    @SerializedName("kecamatan")
    private String kecamatan;

    @SerializedName("kelurahan")
    private String kelurahan;

    @SerializedName("alamat")
    private String alamat;
    @SerializedName("no")
    private String no;

    public DataUser() {
    }

    public DataUser(Integer id, String nama,String kota, String kecamatan, String kelurahan, String alamat, String no) {
        this.id = id;
        this.nama = nama;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.alamat = alamat;
        this.no = no;
    }

    public final static Parcelable.Creator<DataUser> CREATOR = new Creator<DataUser>() {

        @SuppressWarnings({
                "unchecked"
        })
        public DataUser createFromParcel(Parcel in) {
            DataUser instance = new DataUser();
            instance.nama = ((String) in.readValue((String.class.getClassLoader())));
            instance.alamat = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DataUser[] newArray(int size) {
            return (new DataUser[size]);
        }

    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(nama);
        dest.writeValue(alamat);
    }

    public int describeContents() {
        return  0;
    }

}
