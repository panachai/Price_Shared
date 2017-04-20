package com.panachai.priceshared;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class CustomAdapterListviewReview extends BaseAdapter {//จำนวน Array ที่ใช้กำหนดเป็นข้อมูล
    Context mContext;
    double[] proDePrice;
    String[] proDeDes;

    String[] proDeDate;

    String[] supDeDes;
    String[] cusName;

    /*รอแก้ class
    proDePrice+
    proDeDes+
    supDeID	(ใช้ดึงร้านค้าออกมา)+
    cusID+
    proDeDate+
     */

    /**
     * proDeID : 00000000001
     * proID : 00000000001
     * proDePrice : 27000       +
     * proDeDes : ลดราคาจาก 32000 เหลือ 27000   +
     * //supDeID : 00000000001            +
     * //cusID : 00000000010          +
     * proDeScore : 5
     * proDeDate : 2017-04-15       +
     * proDeStatus : 0
     */


    public CustomAdapterListviewReview(Context context, double[] proDePrice,
                                       String[] proDeDes, String[] supDeDes, String[] cusName, String[] proDeDate) {
        this.mContext = context;

        this.proDePrice = proDePrice;
        this.proDeDes = proDeDes;
        this.supDeDes = supDeDes;
        this.cusName = cusName;
        this.proDeDate = proDeDate;
    }

    public int getCount() {
        return proDePrice.length;
    }

    public Object getItem(int position) {//ไว้ดึง Object  ใดๆแล้วแต่กำหนด เช่น listview
        return null;
    }

    public long getItemId(int position) {//get ผ่าน id
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if (view == null)
            view = mInflater.inflate(R.layout.listview_row_custom_review, parent, false);

        TextView tvprice = (TextView) view.findViewById(R.id.tvPriceReview);
        tvprice.setText(String.format( "ราคา %.2f", proDePrice[position] )+"฿");
Log.d("CustomAdapterReview","proDePrice");
        TextView tvCR = (TextView) view.findViewById(R.id.tvCusnameReview);
        tvCR.setText(String.valueOf("by "+cusName[position]));
Log.d("CustomAdapterReview","cusName");
        TextView des = (TextView) view.findViewById(R.id.tvDesReview);
        des.setText(proDeDes[position]);
Log.d("CustomAdapterReview","proDeDes");
        TextView supname = (TextView) view.findViewById(R.id.tvSupName);
        supname.setText(String.valueOf(supDeDes[position]));
Log.d("CustomAdapterReview","supDeDes");
        TextView datereview = (TextView) view.findViewById(R.id.tvDate);
        datereview.setText(String.valueOf(proDeDate[position]));
Log.d("CustomAdapterReview","proDeDate");
        return view;
    }

}
