package com.tonghs;

import android.app.AlertDialog;
import android.content.SharedPreferences;
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
import android.widget.EditText;

import com.tonghs.manager.AreaMgr;
import com.tonghs.model.Area;

public class AddAreaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_area, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void alert(String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(msg).show();
    }

    public void btnAddAreaClick(View v){
        EditText txtAreaName = (EditText)findViewById(R.id.txt_area_name);
        String text = txtAreaName.getText().toString();
        AreaMgr am = new AreaMgr(getBaseContext());
        Area area = new Area();
        area.setName(text);
        am.add(area);
        alert("添加成功");
    }
}