package com.example.android.android_me.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.android.android_me.data.Recipe;
import com.example.android.android_me.data.Steps;

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

    int result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Need to pass steps to this fragment from SecondActivity
        //Need to pass the number clicked here too


        myList = getArguments().getParcelableArrayList("myList");
        result = getArguments().getInt("result") - 1;
        Log.d(LOG_TAG, "This is the result " + result);
        Log.d(LOG_TAG, "TTTTTTTTTTTTTT " + myList.toString());

        myStepsList = myList.get(result).getSteps();
        ingredients = myList.get(result).getIngredients();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        recyclerView = (RecyclerView) rootView
                .findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepItemRecyclerViewAdapter = new StepItemRecyclerViewAdapter(myStepsList);
        recyclerView.setAdapter(stepItemRecyclerViewAdapter);

        TextView textView = (TextView) rootView.findViewById(R.id.text_view);
        textView.setText(ingredients.toString());


        return rootView;
    }


    public class StepItemRecyclerViewAdapter
            extends RecyclerView.Adapter<StepItemRecyclerViewAdapter.ViewHolder> {

//        private final List<Recipe> mValues;
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


            Steps step = stepItemRecyclerViewAdapter.mValues.get(position);


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Context context = v.getContext();
//
//
//                    Intent intent = new Intent(context, SecondActivity.class);
//                    intent.putParcelableArrayListExtra("list", recipeList);
//                    intent.putExtra("intent", holder.mItem.getId());
//                    context.startActivity(intent);




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




