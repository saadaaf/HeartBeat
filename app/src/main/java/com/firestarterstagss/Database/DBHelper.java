package com.firestarterstagss.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public final static String DataBaseName = "HeartBeatDB";
    public final static String TableName = "Users";
    public final static int Version = 1;
    Context context;

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id varchar, insID varchar, username varchar, cookie varchar, csrf varchar, profilepic varchar, IMEI varchar, Edge_array varchar, is_created DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

}
