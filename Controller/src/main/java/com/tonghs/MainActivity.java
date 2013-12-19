package com.tonghs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.tonghs.manager.AreaMgr;
import com.tonghs.manager.ModuleMgr;
import com.tonghs.manager.UserMgr;
import com.tonghs.model.Area;
import com.tonghs.model.MessageUtil;
import com.tonghs.model.Module;
import com.tonghs.util.RequestCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import android.view.LayoutInflater;
import java.util.List;

public class MainActivity extends Activity {

    static final int TIME_OUT = 1500;

    Socket clientSocket;
    private ReceiveThread mReceiveThread = null;
    Switch fun1;
    Switch fun2;
    Switch fun3;
    Switch fun4;
    Switch fun5;
    Switch fun6;
    TextView lblEp1;
    TextView lblEp2;
    TextView lblEp3;
    TextView lblEp4;
    TextView lblEp5;
    TextView lblEp6;
    TextView lblEp7;
    TextView lblEp8;

    Spinner spinnerModule;
    Spinner spinnerArea;
    String ip;
    int port;

    OutputStream outStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AreaMgr am = new AreaMgr(this);
        getCmp();
        spinnerModule = (Spinner)findViewById(R.id.modules);
        spinnerArea = (Spinner)findViewById(R.id.areas);

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
                    int id = area.getId();

                    // 绑定模块
                    ModuleMgr mm = new ModuleMgr(MainActivity.this);
                    List<Module> listModule = mm.getModulesByArea(id);
                    mm.closeDB();
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
                    //设置功能名
                    fun1.setText(module.getFun1());
                    fun2.setText(module.getFun2());
                    fun3.setText(module.getFun3());
                    fun4.setText(module.getFun4());
                    fun5.setText(module.getFun5());
                    fun6.setText(module.getFun6());

                    lblEp1.setText(module.getEp1());
                    lblEp2.setText(module.getEp2());
                    lblEp3.setText(module.getEp3());
                    lblEp4.setText(module.getEp4());
                    lblEp5.setText(module.getEp5());
                    lblEp6.setText(module.getEp6());
                    lblEp7.setText(module.getEp7());
                    lblEp8.setText(module.getEp8());
                    //获取ip
                    ip = module.getIp();
                    port = module.getPort();
                    //获取请求报文
                    byte[] msg = new MessageUtil().getRequestMsg();
                    send(ip, port, msg);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }


    public CompoundButton.OnCheckedChangeListener switchHandler = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int index = Integer.parseInt(buttonView.getTag().toString()) - 1;
            byte[] msg = new MessageUtil().getControlMsg(ip, isChecked, index);
            send(ip, port, msg);
        }
    };

    /**
     * get switch and set listener
     */
    private void getCmp(){
        fun1 = (Switch)findViewById(R.id.fun1);
        fun2 = (Switch)findViewById(R.id.fun2);
        fun3 = (Switch)findViewById(R.id.fun3);
        fun4 = (Switch)findViewById(R.id.fun4);
        fun5 = (Switch)findViewById(R.id.fun5);
        fun6 = (Switch)findViewById(R.id.fun6);

        fun1.setOnCheckedChangeListener(switchHandler);
        fun2.setOnCheckedChangeListener(switchHandler);
        fun3.setOnCheckedChangeListener(switchHandler);
        fun4.setOnCheckedChangeListener(switchHandler);
        fun5.setOnCheckedChangeListener(switchHandler);
        fun6.setOnCheckedChangeListener(switchHandler);

        lblEp1 = (TextView)findViewById(R.id.lbl_ep1);
        lblEp2 = (TextView)findViewById(R.id.lbl_ep2);
        lblEp3 = (TextView)findViewById(R.id.lbl_ep3);
        lblEp4 = (TextView)findViewById(R.id.lbl_ep4);
        lblEp5 = (TextView)findViewById(R.id.lbl_ep5);
        lblEp6 = (TextView)findViewById(R.id.lbl_ep6);
        lblEp7 = (TextView)findViewById(R.id.lbl_ep7);
        lblEp8 = (TextView)findViewById(R.id.lbl_ep8);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //得到当前选中的MenuItem的ID,
        int item_id = item.getItemId();
        /* 新建一个Intent对象 */
        Intent intent = new Intent();
        switch (item_id)
        {
            case R.id.action_add_module:
                AreaMgr am = new AreaMgr(getBaseContext());
                List<Area> list = am.getAreas();
                am.closeDB();
                if (list != null && list.size() > 0){
                    /* 指定intent要启动的类 */
                    Bundle bundle=new  Bundle();
                    bundle.putInt("requestCode", RequestCode.SEND_SMS_ADD);
                    intent.putExtras(bundle);
                    intent.setClass(MainActivity.this, AddModuleActivity.class);
                    /* 启动一个新的Activity */
                    startActivityForResult(intent, RequestCode.SEND_SMS_ADD);
                } else {
                    alert("请先建立区域");
                }
                break;
            case R.id.action_add_area:
				/* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, AddAreaActivity.class);
                /* 启动一个新的Activity */
                startActivityForResult(intent, RequestCode.SEND_SMS_ADD);
                break;
            case R.id.action_module_mg:
                /* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, ModuleMgrActivity.class);
                /* 启动一个新的Activity */
                startActivityForResult(intent, RequestCode.SEND_SMS_ADD);
                break;
            case R.id.action_area_mg:
                /* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, AreaMgrActivity.class);
                /* 启动一个新的Activity */
                startActivityForResult(intent, RequestCode.SEND_SMS_ADD);
                break;
            case R.id.action_update_pwd:
                updatePwd();
                break;
        }

        return true;
    }

    private void setClosable(DialogInterface dialog, boolean b) {
        try {
            Field field = dialog.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePwd(){
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.update_pwd, null);
        new AlertDialog.Builder(this).setTitle("修改密码").setIcon(android.R.drawable.ic_dialog_info)
                .setView(view)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 不关闭
                        setClosable(dialogInterface, false);
                        EditText txtOldPwd = (EditText) view.findViewById(R.id.txt_old_pwd);
                        EditText txtNewPwd = (EditText) view.findViewById(R.id.txt_new_pwd);
                        EditText txtNewPwdRe = (EditText) view.findViewById(R.id.txt_new_pwd_re);

                        String oldPwd = txtOldPwd.getText().toString();
                        String newPwd = txtNewPwd.getText().toString();
                        String newPwdRe = txtNewPwdRe.getText().toString();

                        if (!oldPwd.equals("") && !newPwd.equals("") && !newPwdRe.equals("")) {
                            UserMgr um = new UserMgr(getBaseContext());
                            if (um.validPwd(oldPwd)) {
                                //两次输入相同
                                if (newPwd.equals(newPwdRe)) {
                                    um.update(newPwd);
                                    alert("修改成功");
                                    // 关闭对话框
                                    setClosable(dialogInterface, true);
                                    dialogInterface.dismiss();
                                } else {
                                    //两次输入不同
                                    txtNewPwdRe.setError(getString(R.string.error_not_match));
                                }
                                um.closeDB();
                            } else {
                                //原密码错误
                                txtOldPwd.setError(getString(R.string.error_old_pwd_invalid));
                                um.closeDB();
                                return;
                            }
                        } else {
                            //有空值
                            if (oldPwd.equals("")) {
                                txtOldPwd.setError(getString(R.string.error_field_required));
                            }
                            if (newPwd.equals("")) {
                                txtNewPwd.setError(getString(R.string.error_field_required));
                            }
                            if (newPwdRe.equals("")) {
                                txtNewPwdRe.setError(getString(R.string.error_field_required));
                            }
                            return;
                        }
                    }
                }).setNegativeButton("否", null).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        AreaMgr am = new AreaMgr(this);
        List<Area> listArea = am.getAreas();
        am.closeDB();

        if(listArea != null){

            ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(this,
                    android.R.layout.simple_spinner_item, listArea);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArea.setAdapter(adapter);

            Area area = (Area) spinnerArea.getSelectedItem();
            if (area != null){
                int id = area.getId();

                // 绑定模块
                ModuleMgr mm = new ModuleMgr(this);
                List<Module> listModule = mm.getModulesByArea(id);
                mm.closeDB();
                ArrayAdapter<Module> adapterModule = new ArrayAdapter<Module>(getBaseContext(),
                        android.R.layout.simple_spinner_item, listModule);
                adapterModule.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerModule.setAdapter(adapterModule);
            }
        }
    }

    public void alert(String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(msg).show();
    }

    /**
     * send data to module
     * @param ip
     * @param port
     * @param msg
     */
    public void send(String ip, int port, byte[] msg){
        SendHandler handler = new SendHandler(ip, port, msg);
        new Thread(handler).start();
    }

    /**
     * send data runnable implement
     */
    public class SendHandler implements Runnable{
        public String ip;
        public int port;
        public byte[] msg;

        public SendHandler(String ip, int port, byte[] msg){
            this.ip = ip;
            this.port = port;
            this.msg = msg;
        }

        @Override
        public void run() {
            try
            {
                //实例化对象并连接到服务器
                clientSocket = new Socket();
                SocketAddress socAddress = new InetSocketAddress(ip, port);
                clientSocket.connect(socAddress, TIME_OUT);
                outStream = clientSocket.getOutputStream();
                outStream.write(msg);

                mReceiveThread = new ReceiveThread(clientSocket);
                //开启线程
                mReceiveThread.start();
            }
            catch (Exception e)
            {
                Bundle bundle = new Bundle();
                bundle.putString("msg", "连接失败");
                Message m = new Message();
                m.setData(bundle);
                alertHandler.sendMessage(m);
            }

        }
    }

    public Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            byte[] m = msg.getData().getByteArray("msg");

            MessageUtil mu = new MessageUtil();
            if(m != null){
                //设置状态
                fun1.setChecked(mu.getStatus(m, 0));
                fun2.setChecked(mu.getStatus(m, 1));
                fun3.setChecked(mu.getStatus(m, 2));
                fun4.setChecked(mu.getStatus(m, 3));
                fun5.setChecked(mu.getStatus(m, 4));
                fun6.setChecked(mu.getStatus(m, 5));

                int[] status = mu.getEpStatus(m);
                lblEp1.setBackgroundColor(getResources().getColor(status[0] == 1 ? R.color.red : R.color.green));
                lblEp2.setBackgroundColor(getResources().getColor(status[1] == 1 ? R.color.red : R.color.green));
                lblEp3.setBackgroundColor(getResources().getColor(status[2] == 1 ? R.color.red : R.color.green));
                lblEp4.setBackgroundColor(getResources().getColor(status[3] == 1 ? R.color.red : R.color.green));
                lblEp5.setBackgroundColor(getResources().getColor(status[4] == 1 ? R.color.red : R.color.green));
                lblEp6.setBackgroundColor(getResources().getColor(status[5] == 1 ? R.color.red : R.color.green));
                lblEp7.setBackgroundColor(getResources().getColor(status[6] == 1 ? R.color.red : R.color.green));
                lblEp8.setBackgroundColor(getResources().getColor(status[7] == 1 ? R.color.red : R.color.green));

            } else{
                String text = "获取超时";
                alert(text);
            }
        }
    };

    public Handler alertHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String m = msg.getData().getString("msg");
            alert(m);
        }
    };

    /**
     * receive data
     */
    private class ReceiveThread extends Thread
    {
        private InputStream mInputStream = null;
        private byte[] buf ;
        private boolean stop = false;

        ReceiveThread(Socket s)
        {
            try {
                s.setSoTimeout(TIME_OUT);
                //获得输入流
                this.mInputStream = s.getInputStream();
            } catch (Exception e) {
                try {
                    if (mInputStream != null){
                        mInputStream.close();
                    }
                    if (s != null){
                        s.close();
                    }
                } catch (IOException e1) {
                    //读取超时
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("msg", null);
                    Message m = new Message();
                    m.setData(bundle);
                    mHandler.sendMessage(m);
                }
            }
        }

        @Override
        public void run()
        {
            while(!stop)
            {
                this.buf = new byte[15];

                //读取输入的数据(阻塞读)
                try {
                    this.mInputStream.read(buf);

                    if (buf != null && buf.length > 0 && buf[0] == (byte)0xAA &&
                            buf[1] == 0x55 && buf[13] == (byte)0xcc && buf[14] == (byte)0xdd){
                        //报文验证成功
                        stop = true;
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("msg", buf);
                        Message m = new Message();
                        m.setData(bundle);
                        mHandler.sendMessage(m);
                        String ip = clientSocket.getInetAddress().toString();
                        MessageUtil.currentStatus.put(ip, buf);
                    }
                } catch (Exception e1) {
                    stop = true;
                    //读取超时
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("msg", null);
                    Message m = new Message();
                    m.setData(bundle);
                    mHandler.sendMessage(m);
                } finally {
                    try {
                        if (mInputStream != null){
                            mInputStream.close();
                            outStream.close();
                            clientSocket.close();
                        }
                    } catch (IOException e1) {
                        //读取超时
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("msg", null);
                        Message m = new Message();
                        m.setData(bundle);
                        mHandler.sendMessage(m);
                    }
                }

            }
        }
    }
}


