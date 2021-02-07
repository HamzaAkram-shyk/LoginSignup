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
import com.example.loginsignup.clientfragment.Alljobs;

import com.example.loginsignup.model._Task;

import java.util.List;

public class AllJobsAdapter extends RecyclerView.Adapter<AllJobsAdapter.ViewHolder> {

    private Context context;
    private List<_Task> taskList;
    private onApply apply;

    public void setApply(onApply apply) {
        this.apply = apply;
    }

    public AllJobsAdapter(Context context, List<_Task> taskList) {
        this.context = context;
        this.taskList = taskList;

    }

    @Override
    public AllJobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllJobsAdapter.ViewHolder holder, final int position) {
        final _Task task = taskList.get(position);
        holder.Title.setText("Title: " + task.getTaskTitle());
        holder.price.setText("Price: " + task.getTasKPrice() + " PKR");
        holder.desc.setText("Details: " + task.getTaskDetails());
        holder.clientName.setText("Posted by: "+task.getClientName());
        if(task.getOnline()){
            holder.workType.setText("WorkType: Online");
        }else{
            holder.workType.setText("Work Location: "+task.getWorkLocation());
        }
        holder.applyIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                apply.jobApply(task, position);

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setFilterList(List<_Task> list) {
        taskList=list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Title, price, desc, clientName,workType;
        private ImageButton applyIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.jobTitle);
            price = itemView.findViewById(R.id.jobPrice);
            desc = itemView.findViewById(R.id.jobDetails);
            clientName = itemView.findViewById(R.id.taskerName);
            applyIcon = itemView.findViewById(R.id.applyIcon);
            workType=itemView.findViewById(R.id.workLock);
        }
    }

    public interface onApply {
        public void jobApply(_Task task, int Position);
    }

}


