package com.panachai.priceshared;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class NewsfeedFragment extends Fragment {
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private FragmentActivity myContext; //เดี๋ยวไม่น่าจะได้ใช้ เพราะจะไปใช้ CustomView แทน
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static NewsfeedFragment newInstance() {
        NewsfeedFragment fragment = new NewsfeedFragment();
        return fragment;
    }

    public NewsfeedFragment() {

    }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //ใช้ดึงค่า myContext ของ Fragment  //เดี๋ยวไม่น่าจะได้ใช้ เพราะจะไปใช้ CustomView แทน
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //เดี๋ยวไม่น่าจะได้ใช้ เพราะจะไปใช้ CustomView แทน
        Button btn_one = (Button) rootView.findViewById(R.id.btn_test);

        btn_one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                ReviewItemFragment reviewItemFragment = new ReviewItemFragment();
                FragmentTransaction transaction = myContext.getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, reviewItemFragment);
                transaction.addToBackStack(null); //เพื่อให้กด Back แล้วปิด fragment ก่อน
                transaction.commit();

            }
        });

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        return rootView;
    }

}
