package com.tonghs.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "controller.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS area" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS module" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR, " +
                "ip VARCHAR," +
                "port int," +
                "fun1 VARCHAR," +
                "fun2 VARCHAR," +
                "fun3 VARCHAR," +
                "fun4 VARCHAR," +
                "fun5 VARCHAR," +
                "fun6 VARCHAR," +
                "ep1 VARCHAR," +
                "ep2 VARCHAR," +
                "ep3 VARCHAR," +
                "ep4 VARCHAR," +
                "ep5 VARCHAR," +
                "ep6 VARCHAR," +
                "ep7 VARCHAR," +
                "ep8 VARCHAR," +
                "areaId int)");

        db.execSQL("CREATE TABLE IF NOT EXISTS user" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR)");

        db.execSQL("INSERT INTO USER (username, password) values ('admin', 'admin')");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
