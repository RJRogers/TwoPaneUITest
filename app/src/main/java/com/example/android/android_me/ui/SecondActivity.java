package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by ryanrogers on 11/01/2018.
 */

public class SecondActivity extends AppCompatActivity {

    // Variables to store the values for the list index of the selected images
    // The default value will be index = 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        if(findViewById(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

//            // Change the GridView to space out the images more on tablet
//            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
//            gridView.setNumColumns(2);



            if(savedInstanceState == null) {
                // In two-pane mode, add initial BodyPartFragments to the screen
                // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
                // Use setListindex(int index) to set the list index for all BodyPartFragments

                // Create a new head BodyPartFragment
                BodyPartFragment headFragment = new BodyPartFragment();

                // Set the list of image id's for the head fragment and set the position to the second image in the list
                headFragment.setImageIds(AndroidImageAssets.getHeads());

                // Get the correct index to access in the array of head images from the intent
                // Set the default value to 0
                int headIndex = getIntent().getIntExtra("headIndex", 0);
                headFragment.setListIndex(headIndex);

                // Add the fragment to its container using a FragmentManager and a Transaction
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                // Create and display the body and leg BodyPartFragments

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
                bodyFragment.setListIndex(bodyIndex);

                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                int legIndex = getIntent().getIntExtra("legIndex", 0);
                legFragment.setListIndex(legIndex);

                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }


    }



}
