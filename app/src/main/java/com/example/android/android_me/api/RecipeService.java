package com.example.android.android_me.api;



import com.example.android.android_me.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RecipeService {
    @GET("baking.json")
    Call<List<Recipe>> getMyJson();
}
