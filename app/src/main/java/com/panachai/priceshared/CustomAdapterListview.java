package com.panachai.priceshared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class CustomAdapterListview extends BaseAdapter  {//จำนวน Array ที่ใช้กำหนดเป็นข้อมูล
    Context mContext;
    String[] strName;
    String[] resId;
    String[] proDes;

    public CustomAdapterListview(Context context, String[] strName,String[] proDes, String[] resId) {
        this.mContext= context;
        this.strName = strName;
        this.resId = resId;
        this.proDes = proDes;
    }

    public int getCount() {
        return strName.length;
    }

    public Object getItem(int position) {//ไว้ดึง Object  ใดๆแล้วแต่กำหนด เช่น listview
        return null;
    }

    public long getItemId(int position) {//get ผ่าน id
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_row_custom, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textView1);
        textView.setText(strName[position]);

        TextView des = (TextView)view.findViewById(R.id.des);
        des.setText(proDes[position]);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
/*        //old
        imageView.setBackgroundResource(resId[position]);
*/
        Glide.with(mContext)
                .load(resId[position])
                .diskCacheStrategy(DiskCacheStrategy.ALL)   //บังคับให้ Glide เซฟรูปทุกขนาด ลง Cache
                .into(imageView);




        return view;
    }

}
