package com.panachai.priceshared;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ReviewItemActivity extends AppCompatActivity {
    private int proID;
    private String proName;
    private String proDes;
    private String image;

    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_item);

        proID = getIntent().getIntExtra("proID", 0);
        proName = getIntent().getStringExtra("proName");
        proDes = getIntent().getStringExtra("proDes");
        image = getIntent().getStringExtra("proImage");
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

    }
}
