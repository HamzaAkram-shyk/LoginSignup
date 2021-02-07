package com.example.loginsignup.workerfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.loginsignup.R;
import com.example.loginsignup.adapter.MyJobsAdapter;
import com.example.loginsignup.model._Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyJobs extends Fragment {

    private List<_Task> taskList;
    private RecyclerView listView;
    private View mainView;
    private MyJobsAdapter adapter;
    private ProgressBar loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView=inflater.inflate(R.layout.fragment_my_jobs, container, false);
        listView=mainView.findViewById(R.id.list);
        listView.setHasFixedSize(true);
        loading=mainView.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        listView.setLayoutManager(new GridLayoutManager(getContext(),2));
        fetchTask();
        return mainView;
    }

    private void fetchTask(){
        taskList=new ArrayList<>();
        taskList.clear();
        FirebaseFirestore tasDb=FirebaseFirestore.getInstance();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        tasDb.collection("Taskdb").whereEqualTo("clientId",user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                    taskList.add(documentSnapshot.toObject(_Task.class));
                }
                loading.setVisibility(View.GONE);
                adapter=new MyJobsAdapter(getContext(),taskList);
                listView.setAdapter(adapter);
            }
        });

    }

}