package com.panachai.priceshared;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText UsernameEt, NameEt, EmailEt, PassEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsernameEt = (EditText) findViewById(R.id.etUserName);
        NameEt = (EditText) findViewById(R.id.etName);
        EmailEt = (EditText) findViewById(R.id.etEmail);
        PassEt = (EditText) findViewById(R.id.etPass);

    }

    public void OnRegister(View view) {
        String type = "register";
        String name = NameEt.getText().toString();
        String password = PassEt.getText().toString();
        String username = UsernameEt.getText().toString();
        String email = EmailEt.getText().toString();

        /*
        Register register = new Register(this);
        register.execute(type, name,username, password, email);
        */
        DBHelper dbhelper = new DBHelper(this);
        dbhelper.execute(type, name, username, password, email);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


}
