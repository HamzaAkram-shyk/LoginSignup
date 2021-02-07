package com.example.loginsignup.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.R;
import com.example.loginsignup.model._Task;
import com.example.loginsignup.model._UserJobs;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<_UserJobs> jobList;
    private List<_UserJobs> filterList;
    private onLongClick longClick;


    public void setLongClick(onLongClick longClick) {
        this.longClick = longClick;
    }

    public ReviewAdapter(Context context, List<_UserJobs> jobList) {
        this.context = context;
        this.jobList = jobList;
        filterList = new ArrayList<>(jobList);
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final _UserJobs task = jobList.get(position);
        holder.Mail.setText(task.getWorkerMail());
        holder.Title.setText(task.getTitle());
        holder.Name.setText(task.getWorkerName());
        holder.SelectBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClick.onClick(task.getWorkerId(), task, position);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public void setFilterList(List<_UserJobs> filterList) {
       jobList=filterList;
       notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Title, Name, Mail;
        private ImageButton SelectBtn;
        public ScrollView scrollView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.workerjobtitle);
            Name = itemView.findViewById(R.id.workerjobname);
            Mail = itemView.findViewById(R.id.workerjobmail);
            SelectBtn = itemView.findViewById(R.id.workerjobIcon);

        }
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<_UserJobs> list = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                list.addAll(filterList);
            } else {
                String query = constraint.toString().toLowerCase().trim();
                for (_UserJobs jobs : filterList) {
                    if (jobs.getTitle().toLowerCase().contains(query)) {
                        list.add(jobs);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = list;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //jobList.clear();
            filterList.clear();
            filterList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}

