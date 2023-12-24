package com.example.medis.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;


    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertCairan(String tanggal, Integer batas, Integer urin, Integer total, Integer berat) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TANGGAL, tanggal);
        contentValue.put(DatabaseHelper.BATAS, batas);
        contentValue.put(DatabaseHelper.URIN, urin);
        contentValue.put(DatabaseHelper.TOTAL, total);
        contentValue.put(DatabaseHelper.BERAT, berat);
        database.insert(DatabaseHelper.TABLE_CAIRAN, null, contentValue);
    }

    public Cursor fetchCairan() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.TANGGAL, DatabaseHelper.BATAS ,DatabaseHelper.URIN, DatabaseHelper.TOTAL,DatabaseHelper.BERAT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_CAIRAN, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateCairan(long _id, String tanggal, Integer batas, Integer urin, Integer total, Integer berat) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TANGGAL, tanggal);
        contentValues.put(DatabaseHelper.BATAS, batas);
        contentValues.put(DatabaseHelper.URIN, urin);
        contentValues.put(DatabaseHelper.TOTAL, total);
        contentValues.put(DatabaseHelper.BERAT, berat);
        int i = database.update(DatabaseHelper.TABLE_CAIRAN, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void deleteCairan(long _id) {
        database.delete(DatabaseHelper.TABLE_CAIRAN, DatabaseHelper._ID + "=" + _id, null);
    }

    public void insertKonsumsi(String tanggal, String menu, String jam, Integer cairan) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.MENU, menu);
        contentValue.put(DatabaseHelper.TANGGAL, tanggal);
        contentValue.put(DatabaseHelper.JAM, jam);
        contentValue.put(DatabaseHelper.CAIRAN, cairan);
        database.insert(DatabaseHelper.TABLE_CAIRAN_PERHARI, null, contentValue);
    }

    public int updateCairanPerhari(long _id, String menu, String tanggal, String jam, Integer konsumsi_cairan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MENU, menu);
        contentValues.put(DatabaseHelper.TANGGAL, tanggal);
        contentValues.put(DatabaseHelper.JAM, jam);
        contentValues.put(DatabaseHelper.CAIRAN, konsumsi_cairan);
        int i = database.update(DatabaseHelper.TABLE_CAIRAN_PERHARI, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void deleteCairanPerhari(long _id) {
        database.delete(DatabaseHelper.TABLE_CAIRAN_PERHARI, DatabaseHelper._ID + "=" + _id, null);
    }


}
