package com.tonghs.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.tonghs.R;

import java.util.List;
import java.util.Map;

/**
 * Created by 华帅 on 13-12-9.
 */
public class MySimpleAdapter extends SimpleAdapter {
    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= super.getView(position, convertView, parent);
        Button btnMod = (Button)v.findViewById(R.id.btn_mod);
        Button btnDel = (Button)v.findViewById(R.id.btn_del);
        Button btnView = (Button)v.findViewById(R.id.btn_view);
        TextView txtId = (TextView)v.findViewById(R.id.id);
        btnMod.setTag(txtId.getText().toString());
        btnDel.setTag(txtId.getText().toString());

        if (btnView != null){
            btnView.setTag(txtId.getText().toString());
        }

        return v;
    }
}
