package com.panachai.priceshared;

import android.app.AlertDialog;
import android.content.Context;
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

/**
 * Created by KILLERCON on 3/30/2017.
 */

public class Register extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog alertDialog;
    private Gson gson = new Gson();
    private final OkHttpClient okHttpClient = new OkHttpClient();


    public Register(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

    }

    @Override
    protected String doInBackground(String... params) {
        //รอใช้จริง
        String username = params[0];
        String name = params[1];
        String email = params[2];
        String password = params[3];

        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("cusName", name)
                .add("cusUser", username)
                .add("cusPass", md5(password))
                .add("cusEmail", email)
                .build();
        String response = null;

        try {
            response = http.run("http://10.0.2.2/Webservice/Register.php", formBody); //http://10.0.2.2/Webservice/postString.php
            Log.d("Response : ", response);
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;
        /*
        if (response.isEmpty()) {
            Log.d("Response empty : ", "null");
            return "notPass";
        } else {
            //ว่าจะใส่ intend ตรงนี้เลย
            return "Pass"; //response;
        }
        */
    }


    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
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


}
