package com.tonghs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        final Spinner spinnerArea = (Spinner)findViewById(R.id.areas);

        List<Area> listArea = new ArrayList<Area>();

        //获取请求数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int requestCode = bundle.getInt("requestCode");
        action = requestCode;

        switch (requestCode){
            case RequestCode.SEND_SMS_ADD:
                AreaMgr am = new AreaMgr(this);
                listArea = am.getAreas();
                am.closeDB();
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
        EditText txtEp8 = (EditText)findViewById(R.id.txt_module_ep8);
        txtEp8.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
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
        ModuleMgr mm = new ModuleMgr(getBaseContext());
        module = mm.getModuleById(moduleId);
        mm.closeDB();
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

        EditText txtEp1 = (EditText)findViewById(R.id.txt_module_ep1);
        EditText txtEp2 = (EditText)findViewById(R.id.txt_module_ep2);
        EditText txtEp3 = (EditText)findViewById(R.id.txt_module_ep3);
        EditText txtEp4 = (EditText)findViewById(R.id.txt_module_ep4);
        EditText txtEp5 = (EditText)findViewById(R.id.txt_module_ep5);
        EditText txtEp6 = (EditText)findViewById(R.id.txt_module_ep6);
        EditText txtEp7 = (EditText)findViewById(R.id.txt_module_ep7);
        EditText txtEp8 = (EditText)findViewById(R.id.txt_module_ep8);

        txtName.setText(module.getName());
        txtIP.setText(module.getIp());
        txtPort.setText(String.valueOf(module.getPort()));
        txtFun1.setText(module.getFun1());
        txtFun2.setText(module.getFun2());
        txtFun3.setText(module.getFun3());
        txtFun4.setText(module.getFun4());
        txtFun5.setText(module.getFun5());
        txtFun6.setText(module.getFun6());

        txtEp1.setText(module.getEp1());
        txtEp2.setText(module.getEp2());
        txtEp3.setText(module.getEp3());
        txtEp4.setText(module.getEp4());
        txtEp5.setText(module.getEp5());
        txtEp6.setText(module.getEp6());
        txtEp7.setText(module.getEp7());
        txtEp8.setText(module.getEp8());

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

        EditText txtEp1 = (EditText)findViewById(R.id.txt_module_ep1);
        EditText txtEp2 = (EditText)findViewById(R.id.txt_module_ep2);
        EditText txtEp3 = (EditText)findViewById(R.id.txt_module_ep3);
        EditText txtEp4 = (EditText)findViewById(R.id.txt_module_ep4);
        EditText txtEp5 = (EditText)findViewById(R.id.txt_module_ep5);
        EditText txtEp6 = (EditText)findViewById(R.id.txt_module_ep6);
        EditText txtEp7 = (EditText)findViewById(R.id.txt_module_ep7);
        EditText txtEp8 = (EditText)findViewById(R.id.txt_module_ep8);

        if (spinnerArea.getSelectedItem() != null){
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

            String ep1 = txtEp1.getText().toString();
            String ep2 = txtEp2.getText().toString();
            String ep3 = txtEp3.getText().toString();
            String ep4 = txtEp4.getText().toString();
            String ep5 = txtEp5.getText().toString();
            String ep6 = txtEp6.getText().toString();
            String ep7 = txtEp7.getText().toString();
            String ep8 = txtEp8.getText().toString();

            Module module = new Module();

            if (name.equals("") || ip.equals("") || port.equals("")){
                if (name.equals("")){
                    txtName.setError(getString(R.string.error_field_required));
                }

                if (ip.equals("")){
                    txtIP.setError(getString(R.string.error_field_required));
                }

                if (port.equals("")){
                    txtPort.setError(getString(R.string.error_field_required));
                }

                return;
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

                module.setEp1(ep1.equals("") ? txtEp1.getHint().toString() : ep1);
                module.setEp2(ep2.equals("") ? txtEp2.getHint().toString() : ep2);
                module.setEp3(ep3.equals("") ? txtEp3.getHint().toString() : ep3);
                module.setEp4(ep4.equals("") ? txtEp4.getHint().toString() : ep4);
                module.setEp5(ep5.equals("") ? txtEp5.getHint().toString() : ep5);
                module.setEp6(ep6.equals("") ? txtEp6.getHint().toString() : ep6);
                module.setEp7(ep7.equals("") ? txtEp7.getHint().toString() : ep7);
                module.setEp8(ep8.equals("") ? txtEp8.getHint().toString() : ep8);

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
        } else {
            alert("请选择区域");
        }

    }

    public void addModule(Module module){
        ModuleMgr mm = new ModuleMgr(this);
        mm.add(module);
        mm.closeDB();
        alert("添加成功");
    }

    public void modModule(Module module){
        ModuleMgr mm = new ModuleMgr(this);
        mm.update(module);
        mm.closeDB();

        //数据是使用Intent返回
        Intent intent = new Intent();
        this.setResult(1, intent);
        this.finish();
    }
}
