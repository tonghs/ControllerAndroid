package com.tonghs.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tonghs.model.Module;
import com.tonghs.util.DBHelper;

/**
 * Created by Administrator on 13-12-6.
 */
public class ModuleMgr {
    private DBHelper helper;
    private SQLiteDatabase db;

    public ModuleMgr(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add module
     * @param module
     */
    public void add(Module module) {
        db.beginTransaction();	//开始事务
        try {
            db.execSQL("INSERT INTO module (name, ip, port, fun1, fun2, fun3, fun4, fun5, fun6," +
                    "ep1, ep2, ep3, ep4, ep5, ep6, ep7, ep8, areaId) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{module.getName(), module.getIp(), module.getPort(), module.getFun1(),
                            module.getFun2(), module.getFun3(), module.getFun4(), module.getFun5(),
                            module.getFun6(), module.getEp1(), module.getEp2(), module.getEp3(),
                            module.getEp4(), module.getEp5(), module.getEp6(), module.getEp7(),
                            module.getEp8(), module.getAreaId()});
            db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
            db.endTransaction();	//结束事务
        }
    }

    /**
     * update module
     * @param module
     */
    public void update(Module module) {
        ContentValues cv = new ContentValues();
        cv.put("name", module.getName());
        cv.put("ip", module.getIp());
        cv.put("port", module.getPort());
        cv.put("fun1", module.getFun1());
        cv.put("fun2", module.getFun2());
        cv.put("fun3", module.getFun3());
        cv.put("fun4", module.getFun4());
        cv.put("fun5", module.getFun5());
        cv.put("fun6", module.getFun6());

        cv.put("ep1", module.getEp1());
        cv.put("ep2", module.getEp2());
        cv.put("ep3", module.getEp3());
        cv.put("ep4", module.getEp4());
        cv.put("ep5", module.getEp5());
        cv.put("ep6", module.getEp6());
        cv.put("ep7", module.getEp7());
        cv.put("ep8", module.getEp8());

        db.update("module", cv, "id = ?", new String[]{String.valueOf(module.getId())});
    }

    /**
     * delete old module
     * @param module
     */
    public void delete(Module module) {
        db.delete("module", "id = ?", new String[]{String.valueOf(module.getId())});
    }

    /**
     * delete old module by id
     * @param id
     */
    public void delete(int id) {
        db.delete("module", "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * query all modules by areaId, return list
     * @return List<Module>
     */
    public List<Module> getModulesByArea(int areaId) {
        ArrayList<Module> modules = new ArrayList<Module>();
        Cursor c = queryTheCursor(areaId);
        while (c.moveToNext()) {
            Module module = new Module();
            module.setId(c.getInt(c.getColumnIndex("id")));
            module.setName(c.getString(c.getColumnIndex("name")));
            module.setIp(c.getString(c.getColumnIndex("ip")));
            module.setPort(c.getInt(c.getColumnIndex("port")));
            module.setFun1(c.getString(c.getColumnIndex("fun1")));
            module.setFun2(c.getString(c.getColumnIndex("fun2")));
            module.setFun3(c.getString(c.getColumnIndex("fun3")));
            module.setFun4(c.getString(c.getColumnIndex("fun4")));
            module.setFun5(c.getString(c.getColumnIndex("fun5")));
            module.setFun6(c.getString(c.getColumnIndex("fun6")));

            module.setEp1(c.getString(c.getColumnIndex("ep1")));
            module.setEp2(c.getString(c.getColumnIndex("ep2")));
            module.setEp3(c.getString(c.getColumnIndex("ep3")));
            module.setEp4(c.getString(c.getColumnIndex("ep4")));
            module.setEp5(c.getString(c.getColumnIndex("ep5")));
            module.setEp6(c.getString(c.getColumnIndex("ep6")));
            module.setEp7(c.getString(c.getColumnIndex("ep7")));
            module.setEp8(c.getString(c.getColumnIndex("ep8")));

            module.setAreaId(c.getInt(c.getColumnIndex("areaId")));
            modules.add(module);
        }
        c.close();
        return modules;
    }


    public Module getModuleById(int id) {
        Module module = new Module();
        Cursor c = queryTheCursorById(id);
        while (c.moveToNext()) {
            module.setId(c.getInt(c.getColumnIndex("id")));
            module.setName(c.getString(c.getColumnIndex("name")));
            module.setIp(c.getString(c.getColumnIndex("ip")));
            module.setPort(c.getInt(c.getColumnIndex("port")));
            module.setFun1(c.getString(c.getColumnIndex("fun1")));
            module.setFun2(c.getString(c.getColumnIndex("fun2")));
            module.setFun3(c.getString(c.getColumnIndex("fun3")));
            module.setFun4(c.getString(c.getColumnIndex("fun4")));
            module.setFun5(c.getString(c.getColumnIndex("fun5")));
            module.setFun6(c.getString(c.getColumnIndex("fun6")));

            module.setEp1(c.getString(c.getColumnIndex("ep1")));
            module.setEp2(c.getString(c.getColumnIndex("ep2")));
            module.setEp3(c.getString(c.getColumnIndex("ep3")));
            module.setEp4(c.getString(c.getColumnIndex("ep4")));
            module.setEp5(c.getString(c.getColumnIndex("ep5")));
            module.setEp6(c.getString(c.getColumnIndex("ep6")));
            module.setEp7(c.getString(c.getColumnIndex("ep7")));
            module.setEp8(c.getString(c.getColumnIndex("ep8")));

            module.setAreaId(c.getInt(c.getColumnIndex("areaId")));
            break;
        }
        c.close();
        return module;
    }

    /**
     * query all module, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursor(int areaId) {
        Cursor c = db.rawQuery(String.format("SELECT * FROM module where areaId = %d", areaId), null);
        return c;
    }

    /**
     * query module, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursorById(int id) {
        Cursor c = db.rawQuery(String.format("SELECT * FROM module where id = %d", id), null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
