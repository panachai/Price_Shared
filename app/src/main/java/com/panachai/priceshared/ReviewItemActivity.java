package com.panachai.priceshared;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.otto.Subscribe;

public class ReviewItemActivity extends AppCompatActivity {
    private int proID;
    private String proName;
    private String proDes;
    private String image;

    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDes;

    private final String url = "10.0.2.2/Webservice"; //"10.0.2.2/Webservice" //consolesaleth.esy.es

    private DB_ProductdetailResponse[] productdetailReview;


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
        setContentView(R.layout.activity_review_item);

        proID = getIntent().getIntExtra("proID", 0);
        proName = getIntent().getStringExtra("proName");
        proDes = getIntent().getStringExtra("proDes");
        image = getIntent().getStringExtra("proImage");

        String type = "review";
        //เอา proID ส่งไป where ต่อใน db
        DBHelper dbhelper = new DBHelper(this.getApplicationContext());
        dbhelper.execute(type);

        Log.d("ReviewItemActivity","DBHelper pass");
/*
        Log.d("proID",""+proID);
        Log.d("proName",proName);
        Log.d("proDes",proDes);
        Log.d("image",image);
*/
        imageView = (ImageView) findViewById(R.id.imageView1);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDes = (TextView) findViewById(R.id.tvDes);

        tvTitle.setText(proName);
        tvDes.setText(proDes);

        Glide.with(this.getApplicationContext())
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   //บังคับให้ Glide เซฟรูปทุกขนาด ลง Cache
                .into(imageView);

/*

//----------------------------------------
//Review part
        //review (tvReview)
        ListView LVReview = (ListView) findViewById(R.id.lvReview);

        CustomAdapterListviewReview adapter = new CustomAdapterListviewReview(this.getApplicationContext(), proName,proDes);

        LVReview.setAdapter(adapter);
//----------------------------------------
*/

    }


    @Subscribe
    public void onProductResponse(DB_ProductdetailResponse[] dataDetail) {
        productdetailReview = dataDetail;
        Log.d("ReviewItemActivity","reciver from dbhelper pass");

//productReview

        double[] proDePrice = new double[productdetailReview.length];
        String[] proDeDes = new String[productdetailReview.length];
        int[] supDeID = new int[productdetailReview.length];
        int[] cusID = new int[productdetailReview.length];
        String[] proDeDate = new String[productdetailReview.length];
        Log.d("ReviewItemActivity","set variable pass");

        for (int i = 0; i < productdetailReview.length; i++) {
            Log.d("ReviewItemActivity","in for");
            proDePrice[i] = productdetailReview[i].getProDePrice();
            proDeDes[i] = productdetailReview[i].getProDeDes();
            supDeID[i] = productdetailReview[i].getSupDeID();
            cusID[i] = productdetailReview[i].getCusID();
            proDeDate[i] = productdetailReview[i].getProDeDate();

        }
        Log.d("ReviewItemActivity","before listview");
        ListView listView = (ListView)findViewById(R.id.lvReview);
        Log.d("ReviewItemActivity","after listview");
//--------
        CustomAdapterListviewReview adapter = new CustomAdapterListviewReview
                (this.getApplicationContext(), proDePrice, proDeDes, supDeID,cusID,proDeDate);//แก้ตรงนี้

        listView.setAdapter(adapter);
        Log.d("ReviewItemActivity","setAdapter");
//--------

/*
        listView.setAdapter(new ArrayAdapter(this
                , android.R.layout.simple_list_item_1, proName));
*/

/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1
                    , int arg2, long arg3) {

                Log.d("onItemClick", "1");

                Intent intent = new Intent(this, ReviewItemActivity.class);

                //intent data ReviewItemActivity
                intent.putExtra("proID", productReview[arg2].getProID());
                intent.putExtra("proName", productReview[arg2].getProName());
                intent.putExtra("proDes", productReview[arg2].getProDes());
                intent.putExtra("proImage", "http://" + url + "/" + productReview[arg2].getProDisplay());

                //intent data to ReviewItemActivity (Review below)


                startActivity(intent);

            }
        });
*/
    }
}
