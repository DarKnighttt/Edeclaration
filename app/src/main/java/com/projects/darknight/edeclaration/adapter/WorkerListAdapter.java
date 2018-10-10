package com.projects.darknight.edeclaration.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.darknight.edeclaration.R;
import com.projects.darknight.edeclaration.database.DatabaseHelper;
import com.projects.darknight.edeclaration.pojo.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerListViewHolder> {

    private List<Worker> workersList = new ArrayList<>();
    private Context mContext;
    private String whichActivity;

    public WorkerListAdapter(Context context, String activity){
        mContext = context;
        whichActivity = activity;
    }

    public void setWorkersList(List<Worker> workers){
        workersList.addAll(workers);
        notifyDataSetChanged();
    }

    public void clearWorkers(){
        workersList.clear();
        notifyDataSetChanged();
    }

    public void clearWorkers(DatabaseHelper databaseHelper){
        databaseHelper.deleteAll();
        workersList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public WorkerListAdapter.WorkerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new WorkerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkerListAdapter.WorkerListViewHolder holder, int position) {
        final Worker worker = workersList.get(position);

        holder.firtName.setText(worker.getFirstName());
        holder.lastName.setText(worker.getLastName());
        holder.workPlace.setText(worker.getWorkPlace());
        holder.position.setText(worker.getPosition());
        holder.addToFavorite.setImageResource(R.drawable.ic_star_off);
        holder.openPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, worker.getPdfLink(), Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(worker.getPdfLink()));
                mContext.startActivity(browserIntent);
            }
        });
        if(whichActivity.equals("favorites")) {
            holder.addToFavorite.setVisibility(View.GONE);
        }
        holder.addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addToFavorite.setImageResource(R.drawable.ic_star_on);
                DatabaseHelper dbHelper = new DatabaseHelper(mContext);
                dbHelper.addWorker(worker);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public class WorkerListViewHolder extends RecyclerView.ViewHolder {

        private TextView firtName;
        private TextView lastName;
        private TextView workPlace;
        private TextView position;
        private ImageButton addToFavorite;
        private ImageButton openPdf;

        public WorkerListViewHolder(View itemview) {
            super(itemview);
            firtName = itemview.findViewById(R.id.itemFirstName);
            lastName = itemview.findViewById(R.id.itemLastName);
            workPlace = itemview.findViewById(R.id.itemWorkPlace);
            position = itemview.findViewById(R.id.itemPosition);
            addToFavorite = itemview.findViewById(R.id.itemBtnFavorite);
            openPdf = itemview.findViewById(R.id.itemBtnPdf);
        }
    }


}
