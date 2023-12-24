package com.example.medis.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_CAIRAN = "cairan";
    public static final String TABLE_CAIRAN_PERHARI = "cairan_perhari";

    public static final String _ID = "id";
    public static final String TANGGAL = "tanggal";
    public static final String BATAS = "batas_cairan";
    public static final String URIN = "urin";
    public static final String TOTAL = "total_cairan";
    public static final String BERAT = "berat_badan";
    public static final String CAIRAN = "konsumsi_cairan";

    public static final String MENU = "menu";

    public static final String JAM = "jam";
    public static final String ID_CAIRAN = "id_cairan";

    // Database Information
    static final String DB_NAME = "konsumsi_cairan.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE_CAIRAN = "create table " + TABLE_CAIRAN + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TANGGAL + " TEXT NOT NULL" +
            ", " + BATAS + " INTEGER, " + URIN + " INTEGER, " + TOTAL + " INTEGER, " + BERAT + " INTEGER);";

    private static final String CREATE_TABLE_CAIRAN_PERHARI = "create table " + TABLE_CAIRAN_PERHARI + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MENU + " TEXT NOT NULL" +
            ", " + TANGGAL + " TEXT, "  + JAM + " TEXT, "  + CAIRAN + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CAIRAN);
        db.execSQL(CREATE_TABLE_CAIRAN_PERHARI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAIRAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAIRAN_PERHARI);
        onCreate(db);
    }

    public ArrayList<Cairan> getWhereCairan() {
        SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
        Date date = new Date();
        String today = formatter.format(date).toString();
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Cairan> cairan = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from " + TABLE_CAIRAN + " where " + TANGGAL + " = " + "\"" + today + "\"", null);
        Cursor cursor = db.rawQuery("select cairan.id,cairan.tanggal, cairan.urin, cairan.batas_cairan, " +
                "cairan.berat_badan, cairan.total_cairan,sum(cairan_perhari.konsumsi_cairan) as konsumsi_cairan, " +
                "cairan.total_cairan-(sum(cairan_perhari.konsumsi_cairan)) as sisa_konsumsi from cairan inner join cairan_perhari on cairan.tanggal = cairan_perhari.tanggal where cairan.tanggal" + " = " + "\"" + today + "\"" + " GROUP by cairan.id", null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String tanggal = cursor.getString(1);
                String urin = cursor.getString(2);
                String batas_cairan = cursor.getString(3);
                String berat_badan = cursor.getString(4);
                String total_cairan = cursor.getString(5);
                String konsumsi_cairan = cursor.getString(6);
                String sisa_cairan = cursor.getString(7);

                cairan.add(new Cairan(id, tanggal, urin, batas_cairan, berat_badan, total_cairan, konsumsi_cairan, sisa_cairan));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return cairan;
    }

        public ArrayList<DetailCairan> getKonsumsiCairan() {
            SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
            Date date = new Date();
            String today = formatter.format(date).toString();
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<DetailCairan> detailCairan = new ArrayList<>();
            Cursor cursor = db.rawQuery("select cairan_perhari.id, cairan.tanggal, cairan_perhari.menu, cairan_perhari.jam, cairan_perhari.konsumsi_cairan from cairan_perhari inner join cairan on cairan_perhari.tanggal = cairan.tanggal " + " where cairan.tanggal" + " = " + "\"" + today + "\"" + "and not cairan_perhari.menu = \"dummy\"", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    String tanggal = cursor.getString(1);
                    String menu = cursor.getString(2);
                    String jam = cursor.getString(3);
                    String konsumsi_cairan = cursor.getString(4);
                    detailCairan.add(new DetailCairan(id, menu, tanggal, konsumsi_cairan, jam));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return detailCairan;
        }

    public ArrayList<Cairan> getCairan() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Cairan> cairan = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from " + TABLE_CAIRAN + " where " + TANGGAL + " = " + "\"" + today + "\"", null);
        Cursor cursor = db.rawQuery("select cairan.id,cairan.tanggal, cairan.urin, cairan.batas_cairan, " +
                "cairan.berat_badan, cairan.total_cairan,sum(cairan_perhari.konsumsi_cairan) as konsumsi_cairan, " +
                "cairan.total_cairan-(sum(cairan_perhari.konsumsi_cairan)) as sisa_konsumsi from cairan inner join cairan_perhari on cairan.tanggal = cairan_perhari.tanggal GROUP by cairan.id", null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String tanggal = cursor.getString(1);
                String urin = cursor.getString(2);
                String batas_cairan = cursor.getString(3);
                String berat_badan = cursor.getString(4);
                String total_cairan = cursor.getString(5);
                String konsumsi_cairan = cursor.getString(6);
                String sisa_cairan = cursor.getString(7);

                cairan.add(new Cairan(id, tanggal, urin, batas_cairan, berat_badan, total_cairan, konsumsi_cairan, sisa_cairan));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return cairan;
    }
}
