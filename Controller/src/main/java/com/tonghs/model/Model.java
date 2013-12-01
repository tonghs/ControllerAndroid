package com.tonghs.model;

/**
 * Created by 华帅 on 13-12-1.
 */
public class Model {
    private String name;
    private String ip;
    private String fun1Name;
    private String fun2Name;
    private String fun3Name;
    private String fun4Name;
    private String fun5Name;
    private String fun6Name;

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

    public String getFun1Name() {
        return fun1Name;
    }

    public void setFun1Name(String fun1Name) {
        this.fun1Name = fun1Name;
    }

    public String getFun2Name() {
        return fun2Name;
    }

    public void setFun2Name(String fun2Name) {
        this.fun2Name = fun2Name;
    }

    public String getFun3Name() {
        return fun3Name;
    }

    public void setFun3Name(String fun3Name) {
        this.fun3Name = fun3Name;
    }

    public String getFun4Name() {
        return fun4Name;
    }

    public void setFun4Name(String fun4Name) {
        this.fun4Name = fun4Name;
    }

    public String getFun5Name() {
        return fun5Name;
    }

    public void setFun5Name(String fun5Name) {
        this.fun5Name = fun5Name;
    }

    public String getFun6Name() {
        return fun6Name;
    }

    public void setFun6Name(String fun6Name) {
        this.fun6Name = fun6Name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
