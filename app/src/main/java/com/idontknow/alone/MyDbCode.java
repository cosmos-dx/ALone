package com.idontknow.alone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbCode extends SQLiteOpenHelper {

    private static final String Database_Name = "Alone.db";
    private static final int Database_ID = 1;
    private static final String TABLE_NAME = "alonetable";
    private static final String TABLE_Data_NAME = "alonedata";
    private static final String userpass = "Pass";
    private static final String key_title = "key_title";
    private static final String key_data = "key_data";

    public MyDbCode(Context context) {
        super(context, Database_Name, null , Database_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_NAME + " (" + userpass + " TEXT)";
        String query2 = "CREATE TABLE " + TABLE_Data_NAME + " ("
                + key_title + " TEXT,"
                + key_data + " TEXT)";
        db.execSQL(query1);
        db.execSQL(query2);

        //insertData("pass");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void insertData (String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userpass, pass);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public boolean isexist(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select * from alonetable", null);
        if(cursor.getCount() > 0) return true;
        else return false;

    }
    public boolean Ismatched(String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select pass from alonetable WHERE pass = ?", new String[]{pass});
        if(cursor.getCount() > 0) return true;
        else return false;

    }


    public void update(String oldpass, String newpass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(userpass,newpass);
        db.update(TABLE_NAME, cv, "pass = ?", new String[]{oldpass});


        db.close();
    }

    public void insertDataStore (String key_titleent , String key_dataent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(key_title, key_titleent);
        values.put(key_data,key_dataent);
        db.insert(TABLE_Data_NAME, null, values);
        db.close();
    }

    Cursor readAllData () {
        String query = "SELECT * FROM "+ TABLE_Data_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }
    Cursor readDatastr (String s){
        String query = "SELECT * FROM " + TABLE_Data_NAME + " WHERE " +
                key_title + " LIKE '%" + s + "%'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }

    public void updateData(String Key_titleupd, String key_Dataupd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(key_data,key_Dataupd);
        db.update(TABLE_Data_NAME, cv, "key_title = ?", new String[]{Key_titleupd});


        db.close();
    }




    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Data_NAME);
    }

}
