package com.tonghs;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tonghs.manager.AreaMgr;
import com.tonghs.manager.ModuleMgr;
import com.tonghs.model.Area;
import com.tonghs.model.Module;

import java.util.List;

public class MainActivity extends Activity {

    AreaMgr am;
    ModuleMgr mm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am = new AreaMgr(this);
        mm = new ModuleMgr(this);

        final Spinner spinnerModule = (Spinner)findViewById(R.id.modules);
        final Spinner spinnerArea = (Spinner)findViewById(R.id.areas);

        List<Area> listArea = am.getAreas();

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
                    int id = area.getId();

                    // 绑定模块
                    List<Module> listModule = mm.getModulesByArea(id);
                    ArrayAdapter<Module> adapter = new ArrayAdapter<Module>(getBaseContext(),
                            android.R.layout.simple_spinner_item, listModule);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerModule.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //区域下拉列表选择事件
            spinnerModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Module module = (Module) spinnerModule.getSelectedItem();
                    String ip = module.getIp();
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//                    dialog.setTitle(String.valueOf(ip)).show();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onOnBtnClick(View v) {
        int index = Integer.parseInt(v.getTag().toString());
//        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
//        dialog.setTitle(String.valueOf(index)).show();
    }


    public void onOffBtnClick(View v) {

    }
    
}
