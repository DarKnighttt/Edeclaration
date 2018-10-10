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
                getRequest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Request");
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

    void getData(String request){
        GetDataRetrofit service = RetrofitClient.getRetrofitInstance().create(GetDataRetrofit.class);

        Call<WorkerResponse> call = service.getWorkers("?q=" + request);
        Log.d("URL", call.request().url() + "");
        call.enqueue(new Callback<WorkerResponse>() {
            @Override
            public void onResponse(@NonNull Call<WorkerResponse> call, @NonNull Response<WorkerResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("URL", "Successful response: " + String.valueOf(response.code()));
                    if (response.body() != null) {
                        adapter.setWorkersList(response.body().getWorkers());
                    } else
                        Toast.makeText(MainActivity.this, "Response null", Toast.LENGTH_SHORT).show();
                } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Sorry, nothing found with your request.")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }
            }

            @Override
            public void onFailure(@NonNull Call<WorkerResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.d("URL", "Error " + t.getMessage());
            }
        });
    }
}
