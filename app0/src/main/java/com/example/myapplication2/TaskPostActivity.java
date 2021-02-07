package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication2.Model.Data;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.myapplication2.R.id.add;
import static com.example.myapplication2.R.id.fill;
import static com.example.myapplication2.R.id.icon;

import static com.example.myapplication2.R.id.location;
import static com.example.myapplication2.R.id.post_task_toolbar;


public class TaskPostActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Toolbar supportActionBar;

    private EditText task_title;
    private EditText task_description;
    private EditText task_budget;
    private CheckBox online;
    private CheckBox Physical;
    private EditText task_category;
    private EditText location;
    private Button task_posting_button;

    private DatabaseReference mTaskPost;
    private FirebaseDatabase mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_post);
        mTaskPost = FirebaseDatabase.getInstance().getReference().child("Task Post");
        TaskPost();

    }

    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }

    private void TaskPost(){

        task_title=findViewById(R.id.task_title);
        task_description=findViewById(R.id.task_description);
        task_budget=findViewById(R.id.task_budget);
        task_category=findViewById(R.id.task_category);
        online=findViewById(R.id.online);
        Physical=findViewById(R.id.Physical);
        location=findViewById(R.id.location);


        task_posting_button=findViewById(R.id.task_post_btn);

        task_posting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = task_title.getText().toString().trim();
                String description = task_description.getText().toString().trim();
                String budget = task_budget.getText().toString().trim();
                String category = task_category.getText().toString().trim();
                String locality = location.getText().toString().trim();
                String online1 = online.getText().toString().trim();
                String physical1 = Physical.getText().toString().trim();

                if (TextUtils.isEmpty(title)){
                    task_title.setError("Required Field");
                    return;

                }
                if(TextUtils.isEmpty(online1)){
                    online.setError("Required Field");
                }
                if(TextUtils.isEmpty(physical1)){
                    Physical.setError("Required Field");
                }
                if (TextUtils.isEmpty(description)){
                    task_description.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty((CharSequence) locality)){
                    location.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(budget)){
                    task_budget.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(category)){
                    task_category.setError("Required Field");
                    return;
                }

                Data data = new Data(title,description,budget,category,locality);
                mTaskPost.push().setValue(data);
                Toast.makeText(getApplicationContext(),"Successfully Posted",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Post.class));
                }
        });
    }
}