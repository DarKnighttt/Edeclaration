package com.projects.darknight.edeclaration.api;

import com.projects.darknight.edeclaration.pojo.Worker;
import com.projects.darknight.edeclaration.pojo.WorkerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetDataRetrofit {

    @GET
    Call<WorkerResponse> getWorkers(@Url String request);
}
