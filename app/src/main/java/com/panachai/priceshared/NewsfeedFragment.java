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
    private ListView listView1;
    private String[] proName;

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

        //String[] str = {"Row 0", "Row 1", "Row 2", "Row 3", "Row 4", "Row 5"};

        String type = "selectItem";

        DBHelper dbhelper = new DBHelper(getActivity());
        dbhelper.execute(type);

        listView1 = (ListView) rootView.findViewById(R.id.listView1);

        return rootView;
    }


    @Subscribe
    public void onProductResponse(DB_ProductResponse[] data) {
        Log.d("TEST EventBus db ", data[0].getProName());
        proName = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            Log.d("test getProName ",""+data[i].getProName());
            proName[i] = data[i].getProName();
            Log.d("test proName ",""+proName[i]);
        }

        listView1.setAdapter(new ArrayAdapter(getActivity()
                , android.R.layout.simple_list_item_1, proName));

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1
                    , int arg2, long arg3) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature
                        (dialog.getWindow().FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_data);
                TextView textData =
                        (TextView) dialog.findViewById(R.id.textData);
                textData.setText("Select row " + arg2);

                Button buttonOK =
                        (Button) dialog.findViewById(R.id.buttonOK);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });


    }


}
