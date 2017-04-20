package com.panachai.priceshared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.otto.Subscribe;

public class AddReviewActivity extends AppCompatActivity {
    private final String MY_PREFS = "CustomerLogin";

    private int proID;
    private Spinner kuy;
    private EditText etPrice, etDetailM;
    Spinner spinner;

    //ค่าส่งไป database;
    private String proDePrice, proDeDes, supDeName, cusID, supDeID;


    private SharedPreferences shared;

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        //ประกาศ edittext
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDetailM = (EditText) findViewById(R.id.etDetailM);

        proID = getIntent().getIntExtra("proID", 0);
        String proName = getIntent().getStringExtra("proName");
        String image = getIntent().getStringExtra("image");
        String proDes = getIntent().getStringExtra("proDes");





        //set header
        ImageView display = (ImageView) findViewById(R.id.imageView1);
        Glide.with(this.getApplicationContext())
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   //บังคับให้ Glide เซฟรูปทุกขนาด ลง Cache
                .into(display);
        TextView textView1 = (TextView) findViewById(R.id.tvTitleadd);
        TextView des = (TextView) findViewById(R.id.dess);

        textView1.setText(proName);
        des.setText(proDes);

        shared = getApplicationContext().getSharedPreferences(MY_PREFS,
                Context.MODE_PRIVATE);

//spinner
        kuy = (Spinner) findViewById(R.id.supName);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.kuy, android.R.layout.simple_spinner_item);
        kuy.setAdapter(adapter);

    }

    public void OnAdd(View view) {

//set value to variable //proDePrice,proDeDes,supDeName, cusID
        proDePrice = etPrice.getText().toString();
        proDeDes = etDetailM.getText().toString();

        spinner = (Spinner) AddReviewActivity.this.findViewById(R.id.supName);
        Object selectedItem = spinner.getSelectedItem();
        supDeName = selectedItem.toString();

        if (supDeName.equals("7-11 มหาวิทยาลัยสยาม")) {
            supDeID = "00000000001";
        }

        cusID = shared.getString("cusID", "");//gen จาก SharedPreferences

        //Log.d("selectedText", "" + selectedText);

        //db Select
        String type = "addItem";
        DBHelper dbhelper = new DBHelper(this.getApplicationContext());
        dbhelper.execute(type, String.valueOf(proID), proDePrice, proDeDes, supDeID, cusID);
        Log.d("proID",""+proID);
        Log.d("proDePrice",proDePrice);
        Log.d("proDeDes",proDeDes);
        Log.d("supDeID",supDeID);
        Log.d("cusID",cusID);
    }

    /**
     *
     $pID = $_POST["proID"];
     $pDePrice = $_POST["proDePrice"];
     $pDeDes = $_POST["proDeDes"];
     $sDeID = $_POST["supDeID"];
     $cID = $_POST["cusID"];

     */

    @Subscribe
    public void onCustomer(int[] s) {
        Log.d("zxcasd","onCustomer");
        Intent intent = new Intent(AddReviewActivity.this, Main2Activity.class);
        startActivity(intent);
    }

}
