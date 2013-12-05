package com.tonghs.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.tonghs.R;
import com.tonghs.model.Model;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-5.
 */
public class XmlUtil {
    /**
     * 获取所有模块
     * @param areaName
     * @param context
     * @return
     */
    public List<Model> getModuls(String areaName, Context context){
        List<Model> listModel = new ArrayList<Model>();
        Resources res = context.getResources();
        XmlResourceParser xres = res.getXml(R.xml.modules);
        try{
            xres.next();
            int eventType = xres.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG){
                    //get tag name
                    String tagName = xres.getName();
                    if (tagName.equals("area") && xres.getAttributeValue(null, "name").equals(areaName)){
                        eventType = xres.next();
                        tagName = xres.getName();
                        while(tagName.equals("module")){
                            if(tagName.equals("module") && eventType != XmlPullParser.END_TAG){
                                Model m = new Model();
                                m.setIp(xres.getAttributeValue(null, "ip"));
                                m.setFun1Name(xres.getAttributeValue(null, "switch1"));
                                m.setFun2Name(xres.getAttributeValue(null, "switch2"));
                                m.setFun3Name(xres.getAttributeValue(null, "switch3"));
                                m.setFun4Name(xres.getAttributeValue(null, "switch4"));
                                m.setFun5Name(xres.getAttributeValue(null, "switch5"));
                                m.setFun6Name(xres.getAttributeValue(null, "switch6"));
                                m.setName(xres.getAttributeValue(null, "name"));
                                listModel.add(m);
                            }
                            eventType = xres.next();
                            tagName = xres.getName();
                        }
                    }
                }

                eventType = xres.next();
            }

        }catch (Exception e){

        }


        return listModel;
    }

    /**
     * 获取区域列表
     * @param context
     * @return
     */
    public List<String> getAreas(Context context){
        List<String> list = new ArrayList<String>();
        Resources res = context.getResources();
        XmlResourceParser xres = res.getXml(R.xml.modules);
        try{
            xres.next();
            int eventType = xres.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG){
                    //get tag name
                    String tagName = xres.getName();
                    if (tagName.equals("area")){
                        list.add(xres.getAttributeValue(null, "name"));
                    }
                }

                eventType = xres.next();
            }

        }catch (Exception e){

        }


        return list;
    }

    /**
     * 增加区域
     * @param areaName
     */
    public void addArea(String areaName, Context context){

    }

    /**
     * 添加模块
     * @param m
     * @param areaName
     * @param context
     */
    public void addModule(Model m, String areaName, Context context){

    }
}
