package com.projects.darknight.edeclaration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.projects.darknight.edeclaration.adapter.WorkerListAdapter;
import com.projects.darknight.edeclaration.api.GetDataRetrofit;
import com.projects.darknight.edeclaration.api.RetrofitClient;
import com.projects.darknight.edeclaration.pojo.WorkerResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    WorkerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new WorkerListAdapter(this, "main");


        RecyclerView recyclerView = findViewById(R.id.requestList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favoritesMenuItem:
                startActivity(new Intent(this, Favorites.class));
                return true;
            case R.id.searchMenuItem:
                if (checkInternetConnection())
                    getRequest();
                else
                    simpleDialogShow("Please check your internet connection");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input your key word or leave empty to get all declarations");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().length() == 0)
                    input.setError("Input required!");
                Toast.makeText(MainActivity.this, input.getText().toString(), Toast.LENGTH_SHORT).show();
                getData(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    void getData(String request) {
        GetDataRetrofit service = RetrofitClient.getRetrofitInstance().create(GetDataRetrofit.class);

        Call<WorkerResponse> call = service.getWorkers("?q=" + request);
        call.enqueue(new Callback<WorkerResponse>() {
            @Override
            public void onResponse(@NonNull Call<WorkerResponse> call, @NonNull Response<WorkerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getWorkers() != null) {
                        adapter.clearWorkers();
                        adapter.setWorkersList(response.body().getWorkers());
                    } else
                        simpleDialogShow("Your request return nothing");
                } else
                    simpleDialogShow("Some problems with server.\nPlese try again later!");

            }

            @Override
            public void onFailure(@NonNull Call<WorkerResponse> call, @NonNull Throwable t) {
                simpleDialogShow("Some problems with server.\nPlese try again later!");
            }
        });
    }

    public void simpleDialogShow(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    public boolean checkInternetConnection() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
