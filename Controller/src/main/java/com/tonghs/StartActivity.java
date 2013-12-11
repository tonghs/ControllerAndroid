package com.tonghs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.tonghs.manager.UserMgr;

public class StartActivity extends ActionBarActivity {

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        Intent intent = new Intent();
        UserMgr um = new UserMgr(this);
        if (!username.equals("") && !password.equals("") && um.login(username, password)){
            intent.setClass(StartActivity.this, MainActivity.class);
        } else {
            intent.setClass(StartActivity.this, LoginActivity.class);
        }
        um.closeDB();
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

}
