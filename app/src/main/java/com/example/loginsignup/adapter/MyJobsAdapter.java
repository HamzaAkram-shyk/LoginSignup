package com.example.loginsignup.adapter;

import android.content.Context;
import android.icu.text.CaseMap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.R;
import com.example.loginsignup.model._Task;
import com.example.loginsignup.model._UserJobs;

import java.util.List;

public class MyJobsAdapter<T> extends RecyclerView.Adapter<MyJobsAdapter.ViewHolder>{
    private Context context;
    private List<T> taskList;

    public MyJobsAdapter(Context context,List<T> taskList){
        this.context=context;
        this.taskList=taskList;
    }

    @NonNull
    @Override

    public MyJobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myjobs_item,parent,false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyJobsAdapter.ViewHolder holder, final  int position) {
        if(taskList.get(position) instanceof _Task){
            _Task task=(_Task) taskList.get(position);
            holder.Title.setText("Title: "+task.getTaskTitle());
            holder.price.setText("Price: "+task.getTasKPrice()+" PKR");
            holder.category.setText("Category: "+task.getTaskCategory());
            holder.desc.setText("Details: "+task.getTaskDetails());

            holder.scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });



        } else if(taskList.get(position) instanceof _UserJobs){

            holder.category.setVisibility(View.GONE);
            _UserJobs job=(_UserJobs) taskList.get(position);
            holder.Title.setText(job.getTitle());
            holder.price.setText(""+job.getPrice()+" PKR");
            holder.desc.setText(job.getDetails());
            holder.imageView.setImageResource(R.drawable.current_job );
        }



    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Title,price,desc,category;
        public ScrollView scrollView;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.details);
            category=itemView.findViewById(R.id.category);
            scrollView=itemView.findViewById(R.id.scroll);
            imageView=itemView.findViewById(R.id.icon);
        }
    }
}
