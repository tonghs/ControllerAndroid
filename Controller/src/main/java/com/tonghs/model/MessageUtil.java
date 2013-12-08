package com.tonghs.model;

/**
 * Created by 华帅 on 13-12-8.
 */
public class MessageUtil {
    private byte[] requestMsg;
    private byte[] controlMsg;


    public byte[] getRequestMsg() {
        requestMsg = new byte[16];
        //协议头
        requestMsg[0] = (byte)0xAA;
        requestMsg[1] = 0x55;
        //包长
        requestMsg[2] = 0x00;
        requestMsg[3] = 0x00;
        //命令字
        requestMsg[4] = 0x00;
        //板类型
        requestMsg[5] = 0x00;
        //功能(控制/询问)
        requestMsg[6] = 0x01;
        //设备号
        requestMsg[7] = 0x00;
        //继电器及开关
        requestMsg[8] = 0x00;
        requestMsg[9] = 0x00;
        requestMsg[10] = 0x00;
        requestMsg[11] = 0x00;

        requestMsg[12] = 0x00;
        requestMsg[13] = 0x00;
        requestMsg[14] = (byte)0xcc;
        requestMsg[15] = (byte)0xdd;

        return requestMsg;
    }

    public void setRequestMsg(byte[] requestMsg) {
        this.requestMsg = requestMsg;
    }

    public byte[] getControlMsg() {
        controlMsg = new byte[16];
        //协议头
        controlMsg[0] = (byte)0xAA;
        controlMsg[1] = 0x55;
        //包长
        controlMsg[2] = 0x00;
        controlMsg[3] = 0x00;
        //命令字
        controlMsg[4] = 0x00;
        //板类型
        controlMsg[5] = 0x00;
        //功能(控制/询问)
        controlMsg[6] = 0x00;
        //设备号
        controlMsg[7] = 0x00;
        //继电器及开关
        controlMsg[8] = 0x00;
        controlMsg[9] = 0x00;
        controlMsg[10] = 0x00;
        controlMsg[11] = 0x00;

        controlMsg[12] = 0x00;
        controlMsg[13] = 0x00;
        controlMsg[14] = (byte)0xcc;
        controlMsg[15] = (byte)0xdd;

        return controlMsg;
    }

    public void setControlMsg(byte[] controlMsg) {
        this.controlMsg = controlMsg;
    }

    public boolean GetStatus(byte[] msg, int index)
    {

        return true;
    }
}
