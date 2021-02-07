package com.example.myapplication2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myapplication2.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private DatabaseReference JobPostDatabse;

    private FirebaseRecyclerOptions<Data> options;
    private FirebaseRecyclerAdapter<Data, MyViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
       // String uId = muser.getUid();
        JobPostDatabse = FirebaseDatabase.getInstance().getReference().child("Task Post");


        recyclerView = findViewById(R.id.recycler_task);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        options = new FirebaseRecyclerOptions.Builder<Data>().setQuery(JobPostDatabse, Data.class).build();
        adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {


            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item,parent,false);

                return new MyViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {
                holder.job_title.setText(""+model.getTitle());
                holder.job_description.setText(model.getDescription());
                holder.budget.setText(model.getBudget());
                holder.category.setText(model.getCategory());
                holder.location.setText(model.getLocality());
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}

