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

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner dropdown = (Spinner)findViewById(R.id.modules);

        List<Model> list = getModuls();
        if(list != null && list.size() > 0){

            ArrayAdapter<Model> adapter = new ArrayAdapter<Model>(this,
                    android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);

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


    public List<Model> getModuls(){
        List<Model> listModel = new ArrayList<Model>();
        Resources res = getResources();
        XmlResourceParser xres = res.getXml(R.xml.modules);
        try{
            xres.next();
            int eventType = xres.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG){
                    //get tag name
                    String tagName = xres.getName();
                    if(tagName.equals("module")){
                        Model m = new Model();
                        m.setIp(xres.getAttributeValue(null, "ip"));
                        m.setFun1Name(xres.getAttributeValue(null, "switch1"));
                        m.setFun2Name(xres.getAttributeValue(null, "switch2"));
                        m.setFun3Name(xres.getAttributeValue(null, "switch3"));
                        m.setFun4Name(xres.getAttributeValue(null, "switch4"));
                        m.setFun5Name(xres.getAttributeValue(null, "switch5"));
                        m.setFun6Name(xres.getAttributeValue(null, "switch6"));
                        m.setName(xres.nextText());
                        listModel.add(m);
                    }
                }

                eventType = xres.next();
            }

        }catch (Exception e){

        }


        return listModel;
    }
    
}
