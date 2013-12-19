package com.tonghs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.tonghs.manager.AreaMgr;
import com.tonghs.manager.ModuleMgr;
import com.tonghs.model.Area;
import com.tonghs.model.Module;
import com.tonghs.util.MySimpleAdapter;
import com.tonghs.util.RequestCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleMgrActivity extends ActionBarActivity {
    int areaId;
    int moduleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_mgr);

        //绑定区域下拉列表
        final Spinner spinnerArea = (Spinner)findViewById(R.id.areas);
        AreaMgr am = new AreaMgr(this);
        List<Area> listArea = am.getAreas();
        am.closeDB();

        if(listArea != null && listArea.size() > 0){

            ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(this,
                    android.R.layout.simple_spinner_item, listArea);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArea.setAdapter(adapter);

            //区域下拉列表选择事件
            spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Area area = (Area) spinnerArea.getSelectedItem();
                    areaId = area.getId();
                    bindData();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.module_mgr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 绑定列表数据
     */
    public void bindData(){
        LinearLayout mLayout = (LinearLayout)findViewById(R.id.list_container);
        mLayout.removeAllViews();
        ModuleMgr mm = new ModuleMgr(this);
        List<Module> listModule = mm.getModulesByArea(areaId);
        mm.closeDB();
        if(listModule != null && listModule.size() > 0){
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            for (Module module : listModule){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(module.getId()));
                map.put("name", module.getName());
                list.add(map);
            }

            MySimpleAdapter adapter = new MySimpleAdapter(ModuleMgrActivity.this, list,
                    R.layout.module_item_list, new String[]{"id", "name"},
                    new int[]{R.id.id, R.id.name});
            ListView lv = new ListView(ModuleMgrActivity.this);
            lv.setAdapter(adapter);
            mLayout.addView(lv);
        }
    }

    public void alert(String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ModuleMgrActivity.this);
        dialog.setTitle(msg).show();
    }

    public void btnViewClick(View v){
        Button btnView = (Button)v;
        moduleId = Integer.parseInt(btnView.getTag().toString());

        Intent intent=new Intent();
        intent.setClass(ModuleMgrActivity.this, AddModuleActivity.class);

        Bundle bundle=new  Bundle();
        bundle.putInt("moduleId", moduleId);
        bundle.putInt("requestCode", RequestCode.SEND_SMS_VIEW);
        intent.putExtras(bundle);
        startActivityForResult(intent, RequestCode.SEND_SMS_VIEW);
    }

    public void btnModClick(View v){
        Button btnMod = (Button)v;
        moduleId = Integer.parseInt(btnMod.getTag().toString());

        Intent intent=new Intent();
        intent.setClass(ModuleMgrActivity.this, AddModuleActivity.class);

        Bundle bundle=new  Bundle();
        bundle.putInt("moduleId", moduleId);
        bundle.putInt("requestCode", RequestCode.SEND_SMS_MOD);
        intent.putExtras(bundle);
        startActivityForResult(intent, RequestCode.SEND_SMS_MOD);
    }

    public void btnDelClick(View v){
        Button btnDel = (Button)v;
        moduleId = Integer.parseInt(btnDel.getTag().toString());

        new AlertDialog.Builder(this).setTitle("确认").setMessage("确定删除吗？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ModuleMgr mm = new ModuleMgr(ModuleMgrActivity.this);
                        mm.delete(moduleId);
                        mm.closeDB();
                        bindData();
                        alert("删除成功");
                    }
                }).setNegativeButton("否", null).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        bindData();

        if (requestCode == RequestCode.SEND_SMS_MOD && resultCode == 1){
            alert("修改成功");
        }
    }
}
