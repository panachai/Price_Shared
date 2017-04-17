package com.panachai.priceshared;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;


public class NewsfeedFragment extends Fragment {

    public static NewsfeedFragment newInstance() {
        NewsfeedFragment fragment = new NewsfeedFragment();
        return fragment;
    }

    public NewsfeedFragment() {

    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        String[] str = { "Row 0", "Row 1", "Row 2"
                , "Row 3", "Row 4", "Row 5" };

        String type = "selectitem";
        DBHelper dbhelper = new DBHelper(getActivity());
        dbhelper.execute(type);






        ListView listView1 = (ListView)rootView.findViewById(R.id.listView1);
        listView1.setAdapter(new ArrayAdapter(getActivity()
                , android.R.layout.simple_list_item_1, str));

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1
                    , int arg2, long arg3) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature
                        (dialog.getWindow().FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_data);
                TextView textData =
                        (TextView)dialog.findViewById(R.id.textData);
                textData.setText("Select row " + arg2);

                Button buttonOK =
                        (Button)dialog.findViewById(R.id.buttonOK);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        return rootView;
    }

    @Subscribe
    public void onStringSubscribed(DB_ProductResponse[] data) {
        Log.d("TEST EventBus : ",data[0].getProName());
    }

    @Subscribe
    public void onStringSubscribed(String data) {
        Log.d("TEST EventBus : ",data);
    }



}
