package com.tonghs;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tonghs.manager.AreaMgr;
import com.tonghs.model.Area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AreaMgrActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_mgr);
        FrameLayout mLayout = (FrameLayout)findViewById(R.id.container);

        List<Area> listArea = new AreaMgr(this).getAreas();

        if(listArea != null && listArea.size() > 0){
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            for (Area area : listArea){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(area.getId()));
                map.put("name", area.getName());
                list.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(AreaMgrActivity.this, list,
                    R.layout.item_list, new String[]{"id", "name"},
                    new int[]{R.id.area_id, R.id.area_name});
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

    public void btnModClick(View v){
        Button btnMod = (Button)v;
    }
}
