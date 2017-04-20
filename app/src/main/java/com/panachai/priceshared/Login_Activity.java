package com.panachai.priceshared;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login_Activity extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsernameEt = (EditText) findViewById(R.id.etUserName);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
    }

    public void OnLogin(View view) {

        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        DBHelper dbhelper = new DBHelper(this);
        dbhelper.execute(type, username, password);

    }

    public void OnRegis(View view) {
        Intent intent = new Intent(this, Register_Activity.class);
        startActivity(intent);
    }

}
