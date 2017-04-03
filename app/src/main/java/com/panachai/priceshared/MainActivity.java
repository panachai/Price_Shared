package com.panachai.priceshared;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsernameEt = (EditText) findViewById(R.id.etUserName);
        PasswordEt = (EditText) findViewById(R.id.etPassword);

    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        /*
        Login login = new Login(this);
        login.execute(type, username, password);*/

        DBHelper dbhelper = new DBHelper(this);
        dbhelper.execute(type, username, password);

    }

    public void OnRegis(View view) {
        /*
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        Register register = new Register(this);
        register.execute(username, password);
        */

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);


    }

}
