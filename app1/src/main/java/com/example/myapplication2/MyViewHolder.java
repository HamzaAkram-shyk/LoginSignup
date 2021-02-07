package com.example.myapplication2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView job_title, job_description, budget, work_type, category, location;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);


        job_title = itemView.findViewById(R.id.job_title);
        job_description = itemView.findViewById(R.id.job_description);

        budget = itemView.findViewById(R.id.budget);
        work_type = itemView.findViewById(R.id.work_type);

        category = itemView.findViewById(R.id.category);
        location = itemView.findViewById(R.id.location);


    }
}