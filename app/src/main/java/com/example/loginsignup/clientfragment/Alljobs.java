package com.example.loginsignup.clientfragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.Preference;
import com.example.loginsignup.R;
import com.example.loginsignup.adapter.AllJobsAdapter;
import com.example.loginsignup.adapter.MyJobsAdapter;

import com.example.loginsignup.model._Task;
import com.example.loginsignup.model._UserJobs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Alljobs extends Fragment implements AllJobsAdapter.onApply, onTrigger {
    private List<_Task> taskList;
    private RecyclerView jobList;
    private View mainView;
    private AllJobsAdapter adapter;
    private ProgressBar loading;
    private EditText searchView;
    public static final String _KEY="key";
    private boolean isRecommend=false;
    private TextView titleLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_alljobs, container, false);
        jobList = mainView.findViewById(R.id.jobList);
        jobList.setHasFixedSize(true);
        loading = mainView.findViewById(R.id.loading);
        titleLabel=mainView.findViewById(R.id.label);
        loading.setVisibility(View.VISIBLE);
        searchView = mainView.findViewById(R.id.search_bar);
        taskList = new ArrayList<>();
        jobList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        jobList.setNestedScrollingEnabled(true);
        isRecommend = getArguments().getBoolean(_KEY);
        if(isRecommend){
            titleLabel.setText("Recommend List");
            fetchRecommendTask(this);
        }else{
            titleLabel.setText("All Task");
            fetchTask(this);
        }
        // adapter.setApply(this);

        return mainView;
    }


    private void fetchTask(final onTrigger trigger) {
        taskList = new ArrayList<>();
        taskList.clear();
        FirebaseFirestore tasDb = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tasDb.collection("Taskdb")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    taskList.add(documentSnapshot.toObject(_Task.class));
                }
                loading.setVisibility(View.GONE);
                adapter = new AllJobsAdapter(getContext(), taskList);
                trigger.onVisible();
                jobList.setAdapter(adapter);

            }
        });

    }

    private void fetchRecommendTask(final onTrigger trigger) {
        taskList = new ArrayList<>();
        taskList.clear();
        final FirebaseFirestore tasDb = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tasDb.collection("Taskdb")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    taskList.add(documentSnapshot.toObject(_Task.class));
                }
                tasDb.collection("User").document(user.getUid()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.contains("latestTitle")) {
                                        String latestTitle = document.getString("latestTitle");
                                        taskList = getrecommendList(taskList, latestTitle);

                                    } else {

                                        taskList.clear();
                                    }
                                    loading.setVisibility(View.GONE);
                                    adapter = new AllJobsAdapter(getContext(), taskList);
                                    trigger.onVisible();
                                    jobList.setAdapter(adapter);
                                } else {
                                    loading.setVisibility(View.GONE);
                                    taskList.clear();
                                    adapter = new AllJobsAdapter(getContext(), taskList);
                                    trigger.onVisible();
                                    jobList.setAdapter(adapter);

                                }


                            }
                        });


            }
        });

    }


    private List<_Task> getrecommendList(List<_Task> list, String KeyWord) {
        List<_Task> recommendList = new ArrayList<>();
        String mainKey = KeyWord.toLowerCase().trim();
        for (_Task task : list) {
            int counter = 0;
            for (int i = 0; i < mainKey.length(); i++) {
                String key = mainKey.charAt(i) + "";
                if (task.getTaskTitle().toLowerCase().contains(key)) {
                    counter++;
                }
            }
            int percentage = (counter / task.getTaskTitle().length()) * 100;
            if (percentage >= 50) {
                recommendList.add(task);
            }
            Log.d("Tag",percentage+"");
           // Toast.makeText(getContext(),"percent: "+percentage,Toast.LENGTH_SHORT).show();
        }
        return recommendList;
    }


    @Override
    public void jobApply(_Task task, int Position) {
        taskList.remove(Position);
        adapter.notifyDataSetChanged();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setTitle("Applying.....");
            dialog.show();
            _UserJobs job = new _UserJobs();
            job.setWorkerId(user.getUid());
            job.setClientId(task.getClientId());
            job.setTaskId(task.getTaskId());
            job.setTitle(task.getTaskTitle());
            job.setDetails(task.getTaskDetails());
            job.setPrice(task.getTasKPrice());
            job.setWorkerMail(user.getEmail());
            job.setWorkerName(new Preference(getContext()).getUserName());
            FirebaseFirestore jobDb = FirebaseFirestore.getInstance();
            jobDb.collection("UserJob")
                    .add(job)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                dialog.cancel();
                                Toast.makeText(getContext(), "Successfully Apply", Toast.LENGTH_SHORT).show();
                            } else {

                                dialog.cancel();
                                Toast.makeText(getContext(), "Error " + task.getException().toString(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
        }
    }

    @Override
    public void onVisible() {
        adapter.setApply(this);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searching(s.toString());

            }
        });


    }

    private void searching(String nexText) {
        List<_Task> list = new ArrayList<>();

        String query = nexText.toLowerCase().trim();
        for (_Task task : taskList) {
            if (task.getTaskTitle().toLowerCase().contains(query)) {
                list.add(task);
            }

        }
        // adapter= new ReviewAdapter(getContext(),list);
        adapter.setFilterList(list);


    }
}

interface onTrigger {
    void onVisible();
}