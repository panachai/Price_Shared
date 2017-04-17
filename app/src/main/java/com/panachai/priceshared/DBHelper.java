package com.panachai.priceshared;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;


public class DBHelper extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog alertDialog;
    private Gson gson = new Gson();
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final String url = "10.0.2.2/Webservice"; //"10.0.2.2/Webservice" //consolesaleth.esy.es

    //private String resulttype;  //ไว้ใช้ return instand
    //private String resultvalue; //ใช้ check ตอนปิด alert
    private String[] resultsplit;

    public DBHelper(Context ctx) {
        context = ctx;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

        //register EventBus
        //BusProvider.getInstance().register(this);

    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if (type.equals("login")) {
            //login
            String name = params[1];
            String pass = params[2];

            return loginHere(name, pass);
        } else if (type.equals("register")) {
            //register
            String name = params[1];
            String username = params[2];
            String password = params[3];
            String email = params[4];

            return regisHere(name, username, password, email);
        } else if (type.equals("selectitem")) {
            return selectitem();
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {

        alertDialog.setMessage(result);
        alertDialog.show();

        resultsplit = new String[5];
        resultsplit = result.split(":");

        Log.d("result ", resultsplit[0]);

        Log.d("size ", ""+resultsplit.length);

        //Log.d("result ", String.valueOf(resultsplit.get(1)));

        //ใช้เวลา dialog ขึ้นแล้วกด cancel
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

                if (resultsplit[0].equals("register")) {
                    if (resultsplit[0].equals("pass")) {
                        //to Register
                        Intent intent = new Intent(context, Login_Activity.class);
                        context.startActivity(intent);
                    }

                } else if (resultsplit[0].equals("login")) {
                    if (resultsplit[1].equals("pass")) {
                    Log.d("login", "นะจ๊ะ");
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    }

                }

            }

        });

        //unregister EventBus
        //BusProvider.getInstance().unregister(this);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public class postHttp {
        String run(String url, RequestBody body) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = okHttpClient.newCall(request).execute();

            return response.body().string();
        }
    }

    //ไม่ได้ใช้แล้ว ใช้ผ่าน php แทน
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String loginHere(String name, String pass) {
        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("cusUser", name)
                .add("cusPass", pass)
                .add("cusType", "0")
                .build();
        String response = null;

        System.out.println("pass : " + pass);

        try {
            response = http.run("http://" + url + "/check_login.php", formBody); //
            //http://10.0.2.2/Webservice/postString.php
            //Log.d("Response : ", response);
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (response.isEmpty()) {
            Log.d("Response empty : ", "null");
            return "login:" + "notPass:" + response;    //login (type)
        } else {
            //ว่าจะใส่ intend ตรงนี้เลย
            return "login:" + "pass:" + response; //response;
        }
    }

    public String regisHere(String name, String username, String password, String email) {
        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("cusName", name)
                .add("cusUser", username)
                .add("cusPass", password)
                .add("cusEmail", email)
                .build();
        String response = null;

        try {
            response = http.run("http://" + url + "/Register.php", formBody); //http://10.0.2.2/Webservice/postString.php
            //Log.d("Response : ", response);
        } catch (IOException e) {

            e.printStackTrace();
        }

        //return response;

        if (response.isEmpty()) {
            Log.d("Response empty : ", "null");
            return "register:" + "notPass:" + response;     //register (type)
        } else {
            //ว่าจะใส่ intend ตรงนี้เลย
            return "register:" + "pass:" + response; //response;
        }

    }


    public String selectitem() {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://" + url + "/newsfeed.php").build(); //http://consolesaleth.esy.es/json/gen_json.php

        try {
            Response response = okHttpClient.newCall(request).execute();

            //ไว้ใช้ get สินค้าต่างๆ

            if (response.isSuccessful()) {
                //response สำเร็จเข้า if นี้

                String result = response.body().string();
                //return result; //ถ้าไม่ทำไปใช้ต่อ return แค่นี้

                //นำค่ามาใช้ GSON
                Type collectionType = new TypeToken<Collection<DB_ProductResponse>>() {
                }.getType();
                Collection<DB_ProductResponse> enums = gson.fromJson(result, collectionType);
                DB_ProductResponse[] memberResult = enums.toArray(new DB_ProductResponse[enums.size()]);

                //register EventBus
                //BusProvider.getInstance().register(this);

                //ส่งข้อมูลไปให้ NewsfeedFragment
                //BusProvider.getInstance().post(memberResult);
                //BusProvider.getInstance().post("test send string");

                return "selectitem:" + "pass:"; //test result String.valueOf(memberResult[0].getProName())
                //return response.body().string();
            } else {
                return "Not Success - code : " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error - " + e.getMessage();
        }

    }

}
