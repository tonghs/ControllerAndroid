package com.tonghs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tonghs.manager.AreaMgr;
import com.tonghs.manager.ModuleMgr;
import com.tonghs.model.Area;
import com.tonghs.model.Module;
import com.tonghs.util.RequestCode;

import java.util.ArrayList;
import java.util.List;

public class AddModuleActivity extends ActionBarActivity {
    int action;
    int moduleId;
    ModuleMgr mm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        mm = new ModuleMgr(this);
        final Spinner spinnerArea = (Spinner)findViewById(R.id.areas);

        List<Area> listArea = new ArrayList<Area>();

        //获取请求数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int requestCode = bundle.getInt("requestCode");
        action = requestCode;

        switch (requestCode){
            case RequestCode.SEND_SMS_ADD:
                listArea = new AreaMgr(this).getAreas();
                break;

            case RequestCode.SEND_SMS_VIEW:
                moduleId = bundle.getInt("moduleId");
                listArea = initField(moduleId);
                break;

            case RequestCode.SEND_SMS_MOD:
                moduleId = bundle.getInt("moduleId");
                listArea = initField(moduleId);
                break;

        }

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
                    ((Button)findViewById(R.id.btn_add_module)).performClick();
                    return true;
                }
                return false;
            }
        });

    }


    public List<Area> initField(int moduleId){
        Module module = new Module();
        List<Area> listArea = new ArrayList<Area>();
        module = mm.getModuleById(moduleId);
        //获取区域
        Area area = new AreaMgr(this).getAreaById(module.getAreaId());
        listArea.add(area);

        //初始化默认值
        EditText txtName = (EditText)findViewById(R.id.txt_module_name);
        EditText txtIP = (EditText)findViewById(R.id.txt_module_ip);
        EditText txtPort = (EditText)findViewById(R.id.txt_module_port);
        EditText txtFun1 = (EditText)findViewById(R.id.txt_module_fun1);
        EditText txtFun2 = (EditText)findViewById(R.id.txt_module_fun2);
        EditText txtFun3 = (EditText)findViewById(R.id.txt_module_fun3);
        EditText txtFun4 = (EditText)findViewById(R.id.txt_module_fun4);
        EditText txtFun5 = (EditText)findViewById(R.id.txt_module_fun5);
        EditText txtFun6 = (EditText)findViewById(R.id.txt_module_fun6);

        txtName.setText(module.getName());
        txtIP.setText(module.getIp());
        txtPort.setText(String.valueOf(module.getPort()));
        txtFun1.setText(module.getFun1());
        txtFun2.setText(module.getFun2());
        txtFun3.setText(module.getFun3());
        txtFun4.setText(module.getFun4());
        txtFun5.setText(module.getFun5());
        txtFun6.setText(module.getFun6());

        return listArea;
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

        Module module = new Module();

        if (name.equals("") || ip.equals("") || port.equals("")){
            alert("必填项不可为空");
        } else {
            module.setId(moduleId);
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
        }
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);

        switch (action){
            case RequestCode.SEND_SMS_ADD:
                addModule(module);
                break;

            case RequestCode.SEND_SMS_VIEW:
                this.finish();
                break;

            case RequestCode.SEND_SMS_MOD:
                modModule(module);
                break;
        }
    }

    public void addModule(Module module){
        ModuleMgr mm = new ModuleMgr(this);
        mm.add(module);
        alert("添加成功");
    }

    public void modModule(Module module){
        ModuleMgr mm = new ModuleMgr(this);
        mm.update(module);

        //数据是使用Intent返回
        Intent intent = new Intent();
        this.setResult(1, intent);
        this.finish();
    }
}
