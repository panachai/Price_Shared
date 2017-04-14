package com.panachai.priceshared;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WaitforUseFragment extends Fragment {

    public static WaitforUseFragment newInstance() {
        WaitforUseFragment fragment = new WaitforUseFragment();
        return fragment;
    }


    public WaitforUseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_waitfor_use, container, false);

        return rootView;
    }

}
