package com.example.android.android_me.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


import com.example.android.android_me.R;
import com.example.android.android_me.data.Message;
import com.example.android.android_me.data.Recipe;
import com.example.android.android_me.data.Steps;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.android_me.ui.MainActivity.LOG_TAG;

/**
 * Created by ryanrogers on 11/01/2018.
 */

public class SecondActivity extends AppCompatActivity {


    private boolean mTwoPane;
    ArrayList<Recipe> myList;
    List<Steps> steps;
    android.support.v7.app.ActionBar actionBar;
    StepsFragment fragment;

    //Eventbus
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);


        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        myList = getIntent().getParcelableArrayListExtra("list");
        String id = getIntent().getStringExtra("intent");
        int result = Integer.parseInt(id);
        steps = (ArrayList) myList.get(result - 1).getSteps();

        String nameString = myList.get(result - 1).getName();

        getSupportActionBar().setTitle(nameString);

        if(savedInstanceState == null){

            if (findViewById(R.id.android_me_linear_layout) != null) {

                mTwoPane = true;



                Bundle arguments = new Bundle();


                arguments.putParcelableArrayList("myList", myList);
                arguments.putInt("result", result);
                arguments.putBoolean("twoPane", mTwoPane);

                StepsFragment fragment = new StepsFragment();

                fragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();



            }
            else {
                // We're in single-pane mode and displaying fragments on a phone in separate activities
                mTwoPane = false;

                Bundle arguments = new Bundle();


                arguments.putParcelableArrayList("myList", myList);
                arguments.putInt("result", result);
                arguments.putBoolean("twoPane", mTwoPane);

                StepsFragment fragment = new StepsFragment();

                fragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();



            }



        }





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mTwoPane) {


            startActivity(new Intent(SecondActivity.this, MainActivity.class));
            finish();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Message event) {

        if (event.getMessage() == 2) {


            Fragment newFragment = new DetailFragment();

            newFragment.setArguments(event.getBundle());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.detail_container, newFragment);


            transaction.addToBackStack(null);

            transaction.commit();

            Log.d(LOG_TAG, "Eventbus worked");


        }

        else if ( event.getMessage() == 3){

            Fragment newFragment = new DetailFragment();

            newFragment.setArguments(event.getBundle());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);

            transaction.addToBackStack(null);

            transaction.commit();



        }


    }





}
