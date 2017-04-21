package com.panachai.priceshared;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private final String url = "10.0.2.2/Webservice"; //"10.0.2.2/Webservice" //consolesaleth.esy.es

    private final String MY_PREFS = "CustomerLogin";

    private Context context;
    private AlertDialog alertDialog;
    private Gson gson = new Gson();
    private final OkHttpClient okHttpClient = new OkHttpClient();

    //private String resulttype;  //ไว้ใช้ return instand
    //private String resultvalue; //ใช้ check ตอนปิด alert
    private String[] resultsplit;

    //ไว้แก้ ส่ง busprovider ไม่ได้
    private DB_ProductResponse[] productM;
    private DB_ProductdetailResponse[] productDR;
    //private DB_CustomerResponse[] customerR;


    public DBHelper(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

        //register EventBus
        BusProvider.getInstance().register(this);

        Log.d("onPreExecute : ", "ok 3");
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
        } else if (type.equals("selectItem")) {

            return selectItem();
        } else if (type.equals("review")) {
            String proID_use = params[1];

            return reviewItem(proID_use);
        }


        else if (type.equals("addItem")) {//type, proID,proDePrice ,proDeDes,supDeID,cusID);
            String proID = params[1];
            String proDePrice = params[2];
            String proDeDes = params[3];
            String supDeID = params[4];
            String cusID = params[5];

            /*
            Log.d("proID",proID);
            Log.d("proDePrice",proDePrice);
            Log.d("proDeDes",proDeDes);
            Log.d("supDeID",supDeID);
            Log.d("cusID",cusID);
*/


            return addReview(proID,proDePrice,proDeDes,supDeID,cusID);
        }

        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {

        alertDialog.setMessage(result);
        //alertDialog.show();
        Log.d("onPost", result);

        resultsplit = new String[5];
        resultsplit = result.split(":");

        Log.d("result ", resultsplit[0]);

        Log.d("size(inPost_DBHelper)", "" + resultsplit.length);

        //---------------TEST-----------
        if (resultsplit[0].equals("register")) {
            if (resultsplit[0].equals("pass")) {
                //to Register
                Intent intent = new Intent(context, Login_Activity.class);
                context.startActivity(intent);
            }

        } else if (resultsplit[0].equals("login")) {
            if (resultsplit[1].equals("pass")) {
                Log.d("login", "นะจ๊ะ");
                Intent intent = new Intent(context, Main2Activity.class);
                context.startActivity(intent);
            }

        } else if (resultsplit[0].equals("selectItem")) {
            sendBusProduct();
        } else if (resultsplit[0].equals("review")) {
            sendBusProductdetail();
            //Log.d("reviewresult","pass");
        }else if (resultsplit[0].equals("addItem")){
            //Log.d("zxcasd","additem onpost");
            sendBusCustomer();
        }



        //---------------TEST-----------


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
                        Intent intent = new Intent(context, Main2Activity.class);
                        context.startActivity(intent);
                    }

                } else if (resultsplit[0].equals("selectItem")) {
                    sendBusProduct();
                } else if (resultsplit[0].equals("review")) {
                    sendBusProductdetail();
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

    public String loginHere(String name, String pass) {
        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("cusUser", name)
                .add("cusPass", pass)
                .add("cusType", "0")
                .build();
        String response = null;

        // System.out.println("pass : " + pass);

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
            //chang value login status
            SharedPreferences shared = context.getSharedPreferences(MY_PREFS,
                    Context.MODE_PRIVATE);
            String result = response;

            //นำค่ามาใช้ GSON
            Type collectionType = new TypeToken<Collection<DB_CustomerResponse>>() {
            }.getType();
            Collection<DB_CustomerResponse> enums = gson.fromJson(result, collectionType);
            DB_CustomerResponse[] cusResult = enums.toArray(new DB_CustomerResponse[enums.size()]);
            //setBusCustomer(cusResult);

            String cusID = cusResult[0].getCusID();
            String cusName = cusResult[0].getCusName();
            String cusUser = cusResult[0].getCusUser();
            String cusEmail = cusResult[0].getCusEmail();

            // Save SharedPreferences
            SharedPreferences.Editor editor = shared.edit();
            editor.putBoolean("statusLog", true);
            editor.putString("cusID", cusID);
            editor.putString("cusName", cusName);
            editor.putString("cusUser", cusUser);
            editor.putString("cusEmail", cusEmail);

            editor.apply();

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

    public String selectItem() {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://" + url + "/newsfeed.php").build(); //http://consolesaleth.esy.es/json/gen_json.php

        try {
            Response response = okHttpClient.newCall(request).execute();

            //ไว้ใช้ get สินค้าต่างๆ

            if (response.isSuccessful()) {
                //response สำเร็จเข้า if นี้

                String result = response.body().string();
                //Log.d("onPost",": "+response);
                //return result; //ถ้าไม่ทำไปใช้ต่อ return แค่นี้

                //นำค่ามาใช้ GSON
                Type collectionType = new TypeToken<Collection<DB_ProductResponse>>() {
                }.getType();
                Collection<DB_ProductResponse> enums = gson.fromJson(result, collectionType);
                DB_ProductResponse[] memberResult = enums.toArray(new DB_ProductResponse[enums.size()]);
                setBusProduct(memberResult);
                return "selectItem:" + "pass:" + result; //test result String.valueOf(memberResult[0].getProName())
                //return response.body().string();
            } else {
                return "Not Success - code : " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error - " + e.getMessage();
        }
    }

    public String reviewItem(String proid) {
        //post (proid)

        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("proID", proid)
                .build();
        String response = null;

        try {
            Log.d("dbHelper", "before");
            response = http.run("http://" + url + "/itemReview_comment.php", formBody); //http://10.0.2.2/Webservice/postString.php
            Log.d("dbHelper", response);
//http://" + url + "/


        /*
        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("proID", proid)
                .build();
        String response = null;

        // System.out.println("pass : " + pass);

        try {
            //reciver
            response = http.run("http://" + url + "/itemReview_comment.php", formBody);
            Log.d("dbHelper","before log");
Log.d("dbHelper",response);
        */
            //OkHttpClient okHttpClient = new OkHttpClient();

            //Request.Builder builder = new Request.Builder();
            //Request request = builder.url("http://" + url + "/itemReview_comment.php").build(); //http://consolesaleth.esy.es/json/gen_json.php


            //Response response = okHttpClient.newCall(request).execute();

            //ไว้ใช้ get สินค้าต่างๆ

            //response สำเร็จเข้า if นี้

            //String result = response.body().string();
            //String result = response;

            //return result; //ถ้าไม่ทำไปใช้ต่อ return แค่นี้

            //นำค่ามาใช้ GSON
            Type collectionType = new TypeToken<Collection<DB_ProductdetailResponse>>() {
            }.getType();
            Collection<DB_ProductdetailResponse> enums = gson.fromJson(response, collectionType);

            DB_ProductdetailResponse[] productdetailResponse = enums.toArray(new DB_ProductdetailResponse[enums.size()]);
            setBusProductdetail(productdetailResponse);
            return "review:" + "pass:"; //test result String.valueOf(memberResult[0].getProName())
            //return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error - " + e.getMessage();
        }
    }

//addReview
    public String addReview(String proID,String proDePrice,String proDeDes,String supDeID,String cusID) {
        //post (proid)

        postHttp http = new postHttp();
        RequestBody formBody = new FormEncodingBuilder()
                .add("proID", proID)
                .add("proDePrice", proDePrice)
                .add("proDeDes", proDeDes)
                .add("supDeID", supDeID)
                .add("cusID", cusID)
                .build();
        String response = null;

        try {
            Log.d("dbHelper", "before");
            response = http.run("http://" + url + "/cus_addreview.php", formBody); //http://10.0.2.2/Webservice/postString.php
            Log.d("dbHelper", response);

        /*
            //นำค่ามาใช้ GSON
            Type collectionType = new TypeToken<Collection<DB_ProductdetailResponse>>() {
            }.getType();
            Collection<DB_ProductdetailResponse> enums = gson.fromJson(response, collectionType);
            DB_ProductdetailResponse[] productdetailResponse = enums.toArray(new DB_ProductdetailResponse[enums.size()]);
            //setBusProductdetail(productdetailResponse);
        */
            return "addItem:" + "pass:"; //test result String.valueOf(memberResult[0].getProName())
            //return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error - " + e.getMessage();
        }
    }

    //ใช้กับ selectItemALL
    public void setBusProduct(DB_ProductResponse[] m) {
        productM = m;
    }

    //ใช้กับ review (comment) productdetailResponse
    public void setBusProductdetail(DB_ProductdetailResponse[] m) {
        productDR = m;
    }

    //ใช้กับ itemselectv newsfeed
    public void sendBusProduct() {
        //ส่งข้อมูลไปให้ NewsfeedFragment

        //DB_ProductResponse
        BusProvider.getInstance().post(productM);

    }

    //ใช้กับ review
    public void sendBusProductdetail() {

        //DB_ProductdetailResponse วิ่งไป ReviewItemActivity
        BusProvider.getInstance().post(productDR);
        //Log.d("sendBusProductdetail", "pass");
    }

    public void sendBusCustomer(){
        //Log.d("zxcasd","sss");
        int[] s = new int[0];
        BusProvider.getInstance().post(s);
    }

    /*
    //add review
    public void setBusCustomer(DB_CustomerResponse[] m){
        customerR = m;
    }

    //add review
    public void sendBusCustomer() {
        //DB_CustomerResponse วิ่งไป AddReview
        BusProvider.getInstance().post(customerR);
    }
*/

}
