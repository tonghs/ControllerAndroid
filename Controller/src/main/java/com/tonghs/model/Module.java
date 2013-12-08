package com.tonghs.model;

/**
 * Created by 华帅 on 13-12-1.
 */
public class Module {
    private int id;
    private String name;
    private String ip;
    int port;
    private String fun1;
    private String fun2;
    private String fun3;
    private String fun4;
    private String fun5;
    private String fun6;
    private int areaId;

    @Override
    public String toString() {
        return this.name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFun1() {
        return fun1;
    }

    public void setFun1(String fun1) {
        this.fun1 = fun1;
    }

    public String getFun2() {
        return fun2;
    }

    public void setFun2(String fun2) {
        this.fun2 = fun2;
    }

    public String getFun3() {
        return fun3;
    }

    public void setFun3(String fun3) {
        this.fun3 = fun3;
    }

    public String getFun4() {
        return fun4;
    }

    public void setFun4(String fun4) {
        this.fun4 = fun4;
    }

    public String getFun5() {
        return fun5;
    }

    public void setFun5(String fun5) {
        this.fun5 = fun5;
    }

    public String getFun6() {
        return fun6;
    }

    public void setFun6(String fun6) {
        this.fun6 = fun6;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

}
