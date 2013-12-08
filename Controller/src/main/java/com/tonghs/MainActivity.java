package com.tonghs;

import android.app.AlertDialog;
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
import android.widget.Spinner;
import android.widget.Switch;

import com.tonghs.manager.AreaMgr;
import com.tonghs.manager.ModuleMgr;
import com.tonghs.model.Area;
import com.tonghs.model.MessageUtil;
import com.tonghs.model.Module;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class MainActivity extends Activity {

    AreaMgr am;
    ModuleMgr mm;
    Socket clientSocket;
    private ReceiveThread mReceiveThread = null;
    private ServerSocket mServerSocket = null;
    Switch fun1;
    Switch fun2;
    Switch fun3;
    Switch fun4;
    Switch fun5;
    Switch fun6;
    String ip;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am = new AreaMgr(this);
        mm = new ModuleMgr(this);
        getSwitch();
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
                    //设置功能名
                    fun1.setText(module.getFun1());
                    fun2.setText(module.getFun2());
                    fun3.setText(module.getFun3());
                    fun4.setText(module.getFun4());
                    fun5.setText(module.getFun5());
                    fun6.setText(module.getFun6());
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
    private void getSwitch(){
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        am.closeDB();
        mm.closeDB();
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
				/* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, AddModuleActivity.class);
                break;
            case R.id.action_add_area:
				/* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, AddAreaActivity.class);
                break;
            case R.id.action_module_mg:
                /* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, ModuleMgrActivity.class);
                break;
            case R.id.action_area_mg:
                /* 指定intent要启动的类 */
                intent.setClass(MainActivity.this, AreaMgrActivity.class);
                break;
        }

        /* 启动一个新的Activity */
        startActivity(intent);
//		/* 关闭当前的Activity */
//        this.finish();
        return true;
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
                clientSocket = new Socket(ip, port);
                OutputStream outStream = clientSocket.getOutputStream();
                outStream.write(msg);

                mReceiveThread = new ReceiveThread(clientSocket);
                //开启线程
                mReceiveThread.start();
            }
            catch (Exception e)
            {
                alert("连接失败");
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
            } else{
                String text = "获取超时";
//                fun1.setText(text);
//                fun2.setText(text);
//                fun3.setText(text);
//                fun4.setText(text);
//                fun5.setText(text);
//                fun6.setText(text);
                alert(text);
            }
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
                s.setSoTimeout(4000);
                //获得输入流
                this.mInputStream = s.getInputStream();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            while(!stop)
            {
                this.buf = new byte[16];

                //读取输入的数据(阻塞读)
                try {
                    this.mInputStream.read(buf);
                } catch (Exception e1) {
                    stop = true;
                    //读取超时
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("msg", null);
                    Message m = new Message();
                    m.setData(bundle);
                    mHandler.sendMessage(m);
                }
                if (buf != null && buf.length > 0 && buf[0] == (byte)0xAA &&
                        buf[1] == 0x55 && buf[14] == (byte)0xcc && buf[15] == (byte)0xdd){
                    //报文验证成功
                    stop = true;
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("msg", buf);
                    Message m = new Message();
                    m.setData(bundle);
                    mHandler.sendMessage(m);
                    String ip = clientSocket.getLocalAddress().toString();
                    MessageUtil.currentStatus.put(ip, buf);
                }
            }
        }
    }
}


