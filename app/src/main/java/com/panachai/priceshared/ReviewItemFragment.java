package com.panachai.priceshared;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReviewItemFragment extends Fragment {

    public static ReviewItemFragment newInstance() {
        ReviewItemFragment fragment = new ReviewItemFragment();
        return fragment;
    }

    public ReviewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review_item, container, false);

        return rootView;
    }

}
