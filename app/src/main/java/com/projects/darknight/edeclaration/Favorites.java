package com.projects.darknight.edeclaration;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.projects.darknight.edeclaration.adapter.WorkerListAdapter;
import com.projects.darknight.edeclaration.database.DatabaseHelper;
import com.projects.darknight.edeclaration.pojo.Worker;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity {

    private List<Worker> workersList = new ArrayList<>();
    WorkerListAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.menu_favorites);
        dbHelper = new DatabaseHelper(this);
        adapter = new WorkerListAdapter(this, "favorites");
        adapter.setWorkersList(dbHelper.getAllWorkers());
        RecyclerView recyclerView = findViewById(R.id.favoritesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuBtnDelete:
                adapter.clearWorkers(dbHelper);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
