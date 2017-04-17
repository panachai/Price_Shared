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
    public static int[] proID;
    private View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        //String[] str = {"Row 0", "Row 1", "Row 2", "Row 3", "Row 4", "Row 5"};

        String type = "selectItem";

        DBHelper dbhelper = new DBHelper(getActivity());
        dbhelper.execute(type);


        return rootView;
    }


    @Subscribe
    public void onProductResponse(DB_ProductResponse[] data) {

        Log.d("TEST EventBus db ", data[0].getProName());
        proID = new int[data.length];
        String[] proName = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            Log.d("test getProName ", "" + data[i].getProName());
            proName[i] = data[i].getProName();
            proID[i] = data[i].getProID();

            Log.d("test proName ", "" + proName[i]);
        }

        String[] resId ={"http://www.florasage.com/wp-content/uploads/2012/03/Apps-Paypal-B-icon-512x480.png"
                ,"http://www.icon2s.com/wp-content/uploads/2014/03/Mobile2-Icon.png"};

        ListView listView1 = (ListView) rootView.findViewById(R.id.listView1);
//--------
        CustomAdapterListview adapter = new CustomAdapterListview(getActivity(), proName, resId);


        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
//--------


        /*
        listView1.setAdapter(new ArrayAdapter(getActivity()
                , android.R.layout.simple_list_item_1, proName));
*/


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1
                    , int arg2, long arg3) {


                ReviewItemFragment reviewItemFragment = new ReviewItemFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, reviewItemFragment);
                transaction.addToBackStack(null); //เพื่อให้กด Back แล้วปิด fragment ก่อน
                transaction.commit();


                /* //old
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
                */


            }
        });


    }


}
