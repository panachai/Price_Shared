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

/**
 * Created by KILLERCON on 3/30/2017.
 */

public class Login extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog alertDialog;
    private Gson gson = new Gson();
    private final OkHttpClient okHttpClient = new OkHttpClient();


    public Login(Context ctx) {
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
        String name = params[1];
        String pass = params[2];

        if (type.equals("login")) {
            postHttp http = new postHttp();
            RequestBody formBody = new FormEncodingBuilder()
                    .add("cusUser", name)
                    .add("cusPass", pass)
                    .build();
            String response = null;
            try {
                response = http.run("http://10.0.2.2/Webservice/check_login.php", formBody); //http://10.0.2.2/Webservice/postString.php
                Log.d("Response : ", response);
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (response.isEmpty()) {
                Log.d("Response empty : ", "null");
                return "notPass : "+ response;
            } else {
                //ว่าจะใส่ intend ตรงนี้เลย
                return "Pass : "+response; //response;
            }
        } else {
            return "no type login";
        }
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


}
