package com.tonghs;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.tonghs.manager.AreaMgr;
import com.tonghs.manager.ModuleMgr;
import com.tonghs.model.Area;
import com.tonghs.model.Module;

import java.util.List;

public class AddModuleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        final Spinner spinnerArea = (Spinner)findViewById(R.id.areas);

        List<Area> listArea = new AreaMgr(this).getAreas();

        if(listArea != null && listArea.size() > 0){
            ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(this,
                    android.R.layout.simple_spinner_item, listArea);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArea.setAdapter(adapter);
        }
        EditText txtFun6 = (EditText)findViewById(R.id.txt_module_fun6);
        txtFun6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    addModule();
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_module, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void alert(String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddModuleActivity.this);
        dialog.setTitle(msg).show();
    }

    public void btnAddModuleClick(View v){
        addModule();
    }

    public void addModule(){
        Spinner spinnerArea = (Spinner)findViewById(R.id.areas);
        EditText txtName = (EditText)findViewById(R.id.txt_module_name);
        EditText txtIP = (EditText)findViewById(R.id.txt_module_ip);
        EditText txtPort = (EditText)findViewById(R.id.txt_module_port);
        EditText txtFun1 = (EditText)findViewById(R.id.txt_module_fun1);
        EditText txtFun2 = (EditText)findViewById(R.id.txt_module_fun2);
        EditText txtFun3 = (EditText)findViewById(R.id.txt_module_fun3);
        EditText txtFun4 = (EditText)findViewById(R.id.txt_module_fun4);
        EditText txtFun5 = (EditText)findViewById(R.id.txt_module_fun5);
        EditText txtFun6 = (EditText)findViewById(R.id.txt_module_fun6);

        int areaId = ((Area)spinnerArea.getSelectedItem()).getId();
        String name = txtName.getText().toString();
        String ip = txtIP.getText().toString();
        String port = txtPort.getText().toString();
        String fun1 = txtFun1.getText().toString();
        String fun2 = txtFun2.getText().toString();
        String fun3 = txtFun3.getText().toString();
        String fun4 = txtFun4.getText().toString();
        String fun5 = txtFun5.getText().toString();
        String fun6 = txtFun6.getText().toString();

        if (name.equals("") || ip.equals("") || port.equals("")){
            alert("必填项不可为空");
        } else {
            Module module = new Module();
            module.setName(name);
            module.setIp(ip);
            module.setPort(Integer.parseInt(port));
            module.setFun1(fun1.equals("") ? txtFun1.getHint().toString() : fun1);
            module.setFun2(fun2.equals("") ? txtFun2.getHint().toString() : fun2);
            module.setFun3(fun3.equals("") ? txtFun3.getHint().toString() : fun3);
            module.setFun4(fun4.equals("") ? txtFun4.getHint().toString() : fun4);
            module.setFun5(fun5.equals("") ? txtFun5.getHint().toString() : fun5);
            module.setFun6(fun6.equals("") ? txtFun6.getHint().toString() : fun6);
            module.setAreaId(areaId);

            ModuleMgr mm = new ModuleMgr(this);
            mm.add(module);
            alert("添加成功");
        }
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);
    }
}
