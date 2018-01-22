package com.example.android.android_me.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.Ingredients;
import com.example.android.android_me.data.Message;
import com.example.android.android_me.data.Recipe;
import com.example.android.android_me.data.Steps;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.android_me.ui.MainActivity.LOG_TAG;

/**
 * Created by ryanrogers on 14/01/2018.
 */

public class StepsFragment extends android.support.v4.app.Fragment {



    ArrayList<Recipe> myList;
    List<Steps> myStepsList;
    List <Ingredients> ingredients;

    private RecyclerView recyclerView;
    StepItemRecyclerViewAdapter stepItemRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int result;
    List<String> ingredientsString;
    String string;
    Boolean mTwoPane;
    Parcelable listState;
    public final static String LIST_STATE_KEY = "recycler_list_state";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("myArrayListTwo",(ArrayList) myStepsList);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState == null){
            myStepsList = new ArrayList<>();
        }

        else{
            myStepsList = savedInstanceState.getParcelableArrayList("myArrayListTwo");
        }


        mTwoPane = getArguments().getBoolean("twoPane");
        myList = getArguments().getParcelableArrayList("myList");
        result = getArguments().getInt("result") - 1;
        myStepsList = myList.get(result).getSteps();
        ingredients = myList.get(result).getIngredients();


        //Create a String array for all ingredients strings
        ingredientsString = new ArrayList<>();
        ingredientsString.add("Ingredients" + "\n");

        for(int x = 0; x < ingredients.size(); x++){

            ingredientsString.add(ingredients.get(x).getIngredient() + " " + ingredients.get(x).getQuantity() + " " + ingredients.get(x).getMeasure());
            Log.d(LOG_TAG, ingredientsString.get(x).toString());
        }



        StringBuilder stringBuilder = new StringBuilder();
        for(String s : ingredientsString){
            stringBuilder.append(s + "\n");
        }

        string = stringBuilder.toString();



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

            recyclerView = (RecyclerView) rootView
                    .findViewById(R.id.my_recycler_view);

            mLayoutManager = new LinearLayoutManager(getActivity());

            recyclerView.setLayoutManager(mLayoutManager);
            stepItemRecyclerViewAdapter = new StepItemRecyclerViewAdapter(myStepsList);
            recyclerView.setAdapter(stepItemRecyclerViewAdapter);


            TextView textView = (TextView) rootView.findViewById(R.id.text_view);
            textView.setText(string);


        return rootView;
    }


    public class StepItemRecyclerViewAdapter
            extends RecyclerView.Adapter<StepItemRecyclerViewAdapter.ViewHolder> {

            private final List<Steps> mValues;



        public StepItemRecyclerViewAdapter(List<Steps> items) {

            mValues = items;
        }

        @Override
        public StepItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new StepItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final StepItemRecyclerViewAdapter.ViewHolder holder, int position) {


            holder.mItem = mValues.get(position);


            holder.mContentView.setText(mValues.get(position).getShortDescription());




            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(mTwoPane){
                        Bundle args = new Bundle();

                        args.putParcelableArrayList("myList", myList);
                        args.putInt("result", result);
                        args.putString("int", holder.mItem.getId());
                        EventBus.getDefault().postSticky(new Message(2, args));
                        Log.d(LOG_TAG, "You clicked a button" + holder.mItem.toString());



                    } else if( mTwoPane == false){

                        Bundle args = new Bundle();

                        args.putParcelableArrayList("myList", myList);
                        args.putInt("result", result);
                        args.putString("int", holder.mItem.getId());
                        EventBus.getDefault().postSticky(new Message(3, args));
                        Log.d(LOG_TAG, "You clicked a button" + holder.mItem.toString());

                    }


                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public  View mView;
            public TextView mContentView;
            public Steps mItem;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }













}




