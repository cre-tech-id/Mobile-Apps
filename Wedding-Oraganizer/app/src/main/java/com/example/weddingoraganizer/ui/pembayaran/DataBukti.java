package com.example.weddingoraganizer.ui.pembayaran;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataBukti implements Parcelable {

    @SerializedName("nominal")
    private String nominal;

    @SerializedName("pemesanan_id")
    private Integer pemesanan_id;

    @SerializedName("pembayaran")
    private Integer pembayaran;

    public DataBukti() {
    }

    public DataBukti(String nominal, Integer pemesanan_id, Integer pembayaran) {
        this.nominal = nominal;
        this.pemesanan_id = pemesanan_id;
        this.pembayaran = pembayaran;
    }

    public final static Parcelable.Creator<DataBukti> CREATOR = new Creator<DataBukti>() {

        @SuppressWarnings({
                "unchecked"
        })
        public DataBukti createFromParcel(Parcel in) {
            DataBukti instance = new DataBukti();
            instance.nominal = ((String) in.readValue((String.class.getClassLoader())));
            instance.pemesanan_id = ((Integer) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DataBukti[] newArray(int size) {
            return (new DataBukti[size]);
        }

    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(nominal);
        dest.writeValue(pemesanan_id);
    }

    public int describeContents() {
        return  0;
    }

}
