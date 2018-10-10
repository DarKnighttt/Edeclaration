package com.projects.darknight.edeclaration.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projects.darknight.edeclaration.pojo.Worker;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://public-api.nazk.gov.ua/v1/declaration/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
