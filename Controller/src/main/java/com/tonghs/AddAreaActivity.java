package com.tonghs;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tonghs.manager.AreaMgr;
import com.tonghs.model.Area;

public class AddAreaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);
        EditText txtAreaName = (EditText)findViewById(R.id.txt_area_name);
        txtAreaName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP){
                    addArea();
                    return true;
                }
                return false;
            }
        });
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
        addArea();
    }

    public void addArea(){
        EditText txtAreaName = (EditText)findViewById(R.id.txt_area_name);
        String text = txtAreaName.getText().toString();
        AreaMgr am = new AreaMgr(getBaseContext());
        Area area = new Area();
        area.setName(text);
        am.add(area);
        am.closeDB();
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtAreaName.getWindowToken(), 0);
        alert("添加成功");
    }
}
