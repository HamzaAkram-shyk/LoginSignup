package com.example.loginsignup.clientfragment;

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
import com.example.loginsignup.adapter.CurrentJobAdapter;
import com.example.loginsignup.adapter.MyJobsAdapter;
import com.example.loginsignup.model._CurrentJob;
import com.example.loginsignup.model._UserJobs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CurrentJob extends Fragment {
    private RecyclerView listView;
    private View mainView;
    private CurrentJobAdapter adapter;
    private ProgressBar loading;
    private List<_CurrentJob> jobList;
    public static final String JOB_TYPE="jobtype";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView=inflater.inflate(R.layout.fragment_current_job, container, false);
        listView = mainView.findViewById(R.id.list);
        listView.setHasFixedSize(true);
        loading = mainView.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        listView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        fetchTask();
        return mainView;
    }

    private void fetchTask() {
        jobList = new ArrayList<>();
        jobList.clear();
       boolean isWorker = getArguments().getBoolean(JOB_TYPE);
        FirebaseFirestore tasDb = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(isWorker){
            tasDb.collection("CurrentJob").whereEqualTo("workerId", user.getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        jobList.add(documentSnapshot.toObject(_CurrentJob.class));
                    }
                    loading.setVisibility(View.GONE);
                    adapter = new CurrentJobAdapter(getContext(),jobList);
                    listView.setAdapter(adapter);
                }
            });
        }else{

            tasDb.collection("CurrentJob").whereEqualTo("clientId", user.getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        jobList.add(documentSnapshot.toObject(_CurrentJob.class));
                    }
                    loading.setVisibility(View.GONE);
                    adapter = new CurrentJobAdapter(getContext(),jobList);
                    listView.setAdapter(adapter);
                }
            });





        }


    }



}