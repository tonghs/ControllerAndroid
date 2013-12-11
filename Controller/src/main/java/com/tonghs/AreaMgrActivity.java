package com.tonghs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.tonghs.manager.AreaMgr;
import com.tonghs.model.Area;
import com.tonghs.util.MySimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AreaMgrActivity extends ActionBarActivity {
    int areaId;
    EditText txtAreaName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_mgr);
        bindData();
    }

    public void bindData(){
        FrameLayout mLayout = (FrameLayout)findViewById(R.id.container);
        mLayout.removeAllViews();
        AreaMgr am = new AreaMgr(this);
        List<Area> listArea = am.getAreas();
        am.closeDB();

        if(listArea != null && listArea.size() > 0){
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            for (Area area : listArea){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(area.getId()));
                map.put("name", area.getName());
                list.add(map);
            }

            MySimpleAdapter adapter = new MySimpleAdapter(AreaMgrActivity.this, list,
                    R.layout.item_list, new String[]{"id", "name"},
                    new int[]{R.id.id, R.id.name});
            ListView lv = new ListView(AreaMgrActivity.this);
            lv.setAdapter(adapter);
            mLayout.addView(lv);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.area_mgr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void alert(String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(AreaMgrActivity.this);
        dialog.setTitle(msg).show();
    }

    public void btnModClick(View v){
        txtAreaName = new EditText(this);
        Button btnMod = (Button)v;
        areaId = Integer.parseInt(btnMod.getTag().toString());
        txtAreaName.setText(new AreaMgr(AreaMgrActivity.this).getAreaById(areaId).getName());

        new AlertDialog.Builder(this).setTitle("请输入区域名").setIcon(android.R.drawable.ic_dialog_info)
                .setView(txtAreaName)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String areaName = txtAreaName.getText().toString();
                        Area area = new Area();
                        area.setId(areaId);
                        area.setName(areaName);
                        AreaMgr am = new AreaMgr(AreaMgrActivity.this);
                        am.update(area);
                        am.closeDB();
                        bindData();
                        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(txtAreaName.getWindowToken(), 0);
                        alert("修改成功");
                    }
                }).setNegativeButton("否", null).show();
    }

    public void btnDelClick(View v){
        Button btnDel = (Button)v;
        areaId = Integer.parseInt(btnDel.getTag().toString());

        new AlertDialog.Builder(this).setTitle("确认").setMessage("确定删除吗？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        AreaMgr am = new AreaMgr(AreaMgrActivity.this);
                        am.delete(areaId);
                        am.closeDB();
                        bindData();
                        alert("删除成功");
                    }
                }).setNegativeButton("否", null).show();
    }
}
