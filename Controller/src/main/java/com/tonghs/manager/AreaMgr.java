package com.tonghs.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tonghs.model.Area;
import com.tonghs.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-6.
 */
public class AreaMgr {
    private DBHelper helper;
    private SQLiteDatabase db;

    public AreaMgr(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add area
     * @param area
     */
    public void add(Area area) {
        db.beginTransaction();	//开始事务
        try {
            db.execSQL("INSERT INTO area (name) VALUES(?)",
                    new Object[]{area.getName()});
            db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
            db.endTransaction();	//结束事务
        }
    }

    /**
     * update area
     * @param area
     */
    public void update(Area area) {
        ContentValues cv = new ContentValues();
        cv.put("name", area.getName());
        db.update("area", cv, "id = ?", new String[]{String.valueOf(area.getId())});
    }

    /**
     * delete old area
     * @param area
     */
    public void delete(Area area) {
        db.delete("area", "id = ?", new String[]{String.valueOf(area.getId())});
    }

    /**
     * delete old area by id
     * @param id
     */
    public void delete(int id) {
        db.delete("area", "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * query all areas return list
     * @return List<Area>
     */
    public List<Area> getAreas() {
        ArrayList<Area> areas = new ArrayList<Area>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Area area = new Area();
            area.setId(c.getInt(c.getColumnIndex("id")));
            area.setName(c.getString(c.getColumnIndex("name")));
            areas.add(area);
        }
        c.close();
        return areas;
    }

    public Area getAreaById(int areaId){
        Area area = new Area();
        Cursor c = queryTheCursorById(areaId);
        while (c.moveToNext()) {
            area.setId(c.getInt(c.getColumnIndex("id")));
            area.setName(c.getString(c.getColumnIndex("name")));
            break;
        }
        c.close();
        return area;
    }

    /**
     * query all persons, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM area", null);
        return c;
    }

    /**
     * query all persons, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursorById(int id) {
        Cursor c = db.rawQuery(String.format("SELECT * FROM area where id = %d", id), null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
