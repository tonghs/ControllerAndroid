package com.tonghs.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tonghs.model.User;
import com.tonghs.util.DBHelper;


/**
 * Created by Administrator on 13-12-11.
 */
public class UserMgr {
    private DBHelper helper;
    private SQLiteDatabase db;

    public UserMgr(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * update password
     * @param password
     */
    public void update(String password) {
        ContentValues cv = new ContentValues();
        db.execSQL(String.format("update user set password = '%s'", password));
    }

    public User getUserById(int userId){
        User user = new User();
        Cursor c = queryTheCursorById(userId);
        while (c.moveToNext()) {
            user.setId(c.getInt(c.getColumnIndex("id")));
            user.setUsername(c.getString(c.getColumnIndex("username")));
            user.setPassword(c.getString(c.getColumnIndex("password")));
            break;
        }
        c.close();
        return user;
    }

    public boolean validPwd(String password){
        boolean isSuccess = false;
        Cursor c = queryTheCursorByPwd(password);
        if (c.moveToNext()) {
            isSuccess = true;
        }
        c.close();
        return isSuccess;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password){
        boolean isSuccess = false;
        try{
            Cursor c = db.rawQuery(String.format("SELECT * FROM user where username = '%s' " +
                    "and password = '%s'", username, password), null);

            while (c.moveToNext()) {
                if (username.equals(c.getString(c.getColumnIndex("username"))) && password.equals(c.getString(c.getColumnIndex("password")))){
                    isSuccess = true;
                }
                break;
            }
        } catch (Exception e){
            isSuccess = false;
        }

        return isSuccess;
    }

    /**
     * query all persons, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursorById(int id) {
        Cursor c = db.rawQuery(String.format("SELECT * FROM user where id = %d", id), null);
        return c;
    }

    public Cursor queryTheCursorByPwd(String password) {
        Cursor c = db.rawQuery(String.format("SELECT * FROM user where password = '%s'", password), null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
