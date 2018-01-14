package com.example.android.android_me.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.android_me.R;

/**
 * Created by ryanrogers on 14/01/2018.
 */

public class StepsFragment extends android.support.v4.app.Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Need to pass steps to this fragment from SecondActivity


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);


        return rootView;
    }
}
