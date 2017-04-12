package com.panachai.priceshared;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DBHelper extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog alertDialog;
    private Gson gson = new Gson();
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final String url = "10.0.2.2/Webservice"; //"10.0.2.2/Webservice" //consolesaleth.esy.es

    private String resulttype;  //ไว้ใช้ return instand

    public DBHelper(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

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
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {

        alertDialog.setMessage(result);
        alertDialog.show();

        String[] resultsplit = result.split(":");
        resulttype = resultsplit[0];

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {


            public void onCancel(DialogInterface dialog) {

                if (resulttype.equals("register")) {
                    //to Register
                    Intent intent = new Intent(context, Login_Activity.class);
                    context.startActivity(intent);
                } else if (resulttype.equals("login")) {
                    Log.d("login", "นะจ๊ะ");
                }

                resulttype = "";    //เคลียร์ค่า type ทุกครั้ง

            }

        });

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
                .add("cusPass", md5(pass))
                .build();
        String response = null;

        System.out.println("pass md5 : " + md5(pass));

        try {
            response = http.run("http://" + url + "/check_login.php", formBody); //
            //http://10.0.2.2/Webservice/postString.php
            Log.d("Response : ", response);
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (response.isEmpty()) {
            Log.d("Response empty : ", "null");
            return "login:" + "notPass:" + response;    //login (type)
        } else {
            //ว่าจะใส่ intend ตรงนี้เลย
            return "login:" + "Pass:" + response; //response;
        }
    }

    public String regisHere(String name, String username, String password, String email) {
        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("cusName", name)
                .add("cusUser", username)
                .add("cusPass", md5(password))
                .add("cusEmail", email)
                .build();
        String response = null;

        try {
            response = http.run("http://" + url + "/Register.php", formBody); //http://10.0.2.2/Webservice/postString.php
            Log.d("Response : ", response);
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        //return response;

        if (response.isEmpty()) {
            Log.d("Response empty : ", "null");
            return "register:" + "NotPass:" + response;     //register (type)
        } else {
            //ว่าจะใส่ intend ตรงนี้เลย
            return "register:" + "Pass:" + response; //response;
        }

    }


}
