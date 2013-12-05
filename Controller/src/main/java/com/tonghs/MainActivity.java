package com.tonghs;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tonghs.model.Model;
import com.tonghs.util.XmlUtil;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    XmlUtil xu = new XmlUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner dropdown = (Spinner)findViewById(R.id.modules);

        List<Model> list = xu.getModuls("area1", getBaseContext());

        if(list != null && list.size() > 0){

            ArrayAdapter<Model> adapter = new ArrayAdapter<Model>(this,
                    android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);

            //下拉列表选择事件
            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Model m = (Model) dropdown.getSelectedItem();
                    String ip = m.getIp();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle(String.valueOf(ip)).show();
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
