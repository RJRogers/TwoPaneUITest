/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.api.RecipeService;
import com.example.android.android_me.data.AndroidImageAssets;
import com.example.android.android_me.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity{

    public static final String LOG_TAG = "myLogs";
    ArrayList<Recipe> recipeList = new ArrayList<>();
    SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        getRecipes();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(getTitle());


    }



    public void getRecipes(){

        String ROOT_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";


        Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = RETROFIT.create(RecipeService.class);

        Call<List<Recipe>> call = service.getMyJson();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(LOG_TAG, "Got here");
                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "No Success");
                }

                Log.d(LOG_TAG, "Got here");
                String string = response.body().toString();

                Log.d(LOG_TAG, string);


                recipeList = (ArrayList) response.body();

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.item_list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);

                simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(recipeList);
                recyclerView.setAdapter(simpleItemRecyclerViewAdapter);


//                if (findViewById(R.id.item_detail_container) != null) {
//                    mTwoPane = true;
//                }


                Log.e(LOG_TAG, "LOGS " + recipeList.size());




                for (int i = 0; i < recipeList.size(); i++) {
                    String newString = recipeList.get(i).getName();

                    Log.d(LOG_TAG, newString + " ****************");



                }


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("getRecipes throwable: ", t.getMessage());
                t.printStackTrace();

            }
        });


    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Recipe> mValues;

        public SimpleItemRecyclerViewAdapter(List<Recipe> items) {

            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {


            holder.mItem = mValues.get(position);


            holder.mContentView.setText(mValues.get(position).getName());


            Recipe recipe = simpleItemRecyclerViewAdapter.mValues.get(position);
            recipe.addItem(recipe);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Context context = v.getContext();


                    Intent intent = new Intent(context, SecondActivity.class);
                    intent.putParcelableArrayListExtra("list", recipeList);
                    intent.putExtra("intent", holder.mItem.getId());
                    context.startActivity(intent);




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
            public Recipe mItem;


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
