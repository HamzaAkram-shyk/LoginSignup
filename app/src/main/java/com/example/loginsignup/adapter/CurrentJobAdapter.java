package com.example.loginsignup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.R;
import com.example.loginsignup.model._CurrentJob;
import com.example.loginsignup.model._Task;

import java.util.List;

public class CurrentJobAdapter extends RecyclerView.Adapter<CurrentJobAdapter.ViewHolder> {

    private Context context;
    private List<_CurrentJob> jobList;




    public CurrentJobAdapter(Context context, List<_CurrentJob> taskList) {
        this.context = context;
        this.jobList = taskList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_job_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final _CurrentJob job = jobList.get(position);
        holder.workerName.setText("Worker Name: "+job.getWorkerName());
        holder.workermail.setText("Worker Mail: "+job.getWorkerMail());
        holder.taskTitle.setText("Task: "+job.getTitle());
        holder.price.setText("Price: "+job.getPrice()+" PKR");
        holder.desc.setText("Deatils: "+job.getDetails());
        holder.clientName.setText("Posted by: "+job.getClientName());

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView workerName, price, desc, clientName,clientmail,workermail
                ,taskTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workerName=itemView.findViewById(R.id.wname);
            workermail=itemView.findViewById(R.id.wmail);
            price = itemView.findViewById(R.id.taskPrice);
            desc = itemView.findViewById(R.id.taskdetail);
            taskTitle=itemView.findViewById(R.id.taskname);
            clientName = itemView.findViewById(R.id.cname);
            clientmail=itemView.findViewById(R.id.cmail);

        }
    }
}