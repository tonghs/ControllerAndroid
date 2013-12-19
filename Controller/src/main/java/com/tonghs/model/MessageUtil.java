package com.tonghs.model;

import java.util.Hashtable;

/**
 * Created by 华帅 on 13-12-8.
 */
public class MessageUtil {
    private byte[] requestMsg;
    private byte[] controlMsg;
    public static Hashtable<String, byte[]> currentStatus = new Hashtable<String, byte[]>();

    public byte[] getRequestMsg() {
        requestMsg = new byte[15];
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
        //电位
        requestMsg[10] = 0x00;

        requestMsg[11] = 0x00;
        requestMsg[12] = 0x00;
        requestMsg[13] = (byte)0xcc;
        requestMsg[14] = (byte)0xdd;

        return requestMsg;
    }

    public void setRequestMsg(byte[] requestMsg) {
        this.requestMsg = requestMsg;
    }

    public byte[] getControlMsg() {
        controlMsg = new byte[15];
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

        //电位
        controlMsg[10] = 0x00;

        controlMsg[11] = 0x00;
        controlMsg[12] = 0x00;
        controlMsg[13] = (byte)0xcc;
        controlMsg[14] = (byte)0xdd;

        return controlMsg;
    }

    public void setControlMsg(byte[] controlMsg) {
        this.controlMsg = controlMsg;
    }

    /**
     * get status from msg
     * @param msg
     * @param index
     * @return
     */
    public boolean getStatus(byte[] msg, int index)
    {
        int segment = index / 8;
        int offset = index % 8;

        byte b = msg[9 - segment];

        int temp = (b << (31 - offset)) >> 31;

        return temp == -1;
    }

    /**
     * 获取电位状态
     * @param msg
     * @return
     */
    public int[] getEpStatus(byte[] msg){
        int[] status = new int[8];
        byte b = msg[10];

        for(int i = 0; i < 8; i++){
            int temp = (b << (31 - i)) >> 31;

            status[i] = temp == -1 ? 1 : 0;
        }

        return status;
    }

    /**
     * 生成控制报文
     * @param isChecked
     * @param index
     * @return
     */
    public byte[] getControlMsg(String ip, boolean isChecked, int index){
        byte[] msg = null;
        try {
            msg = currentStatus.get("/" + ip);
            if(msg == null){
                msg = this.getControlMsg();
            }

            String byteStr = byteToBit(msg[9 - index / 8]);
            char[] temp = byteStr.toCharArray();
            temp[7 - index % 8] = isChecked ? '1' : '0';
            byteStr = String.valueOf(temp);
            msg[9 - index / 8] = decodeBinaryString(byteStr);
        }catch (Exception e){

        }

        return msg;
    }

    public String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    /**
     * 二进制字符串转byte
     */
    public byte decodeBinaryString(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {// 4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }
}
