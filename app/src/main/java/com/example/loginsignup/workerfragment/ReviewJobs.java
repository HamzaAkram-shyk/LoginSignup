package com.example.loginsignup.workerfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginsignup.Preference;
import com.example.loginsignup.R;
import com.example.loginsignup.adapter.MyJobsAdapter;
import com.example.loginsignup.adapter.ReviewAdapter;

import com.example.loginsignup.adapter.onLongClick;
import com.example.loginsignup.model._CurrentJob;
import com.example.loginsignup.model._Task;
import com.example.loginsignup.model._UserJobs;
import com.example.loginsignup.workerfragment.Setup.APIService;
import com.example.loginsignup.workerfragment.Setup.Client;
import com.example.loginsignup.workerfragment.Setup.Data;
import com.example.loginsignup.workerfragment.Setup.MyResponse;
import com.example.loginsignup.workerfragment.Setup.NotificationSender;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewJobs extends Fragment implements onLongClick, onGenerator {

    private List<_UserJobs> reviewList;
    private View mainView;
    private ProgressBar progressBar;
    private RecyclerView List;
    private ReviewAdapter adapter;
    private APIService apiService;
    public static String FirebasePush_NotificationUrl = "https://fcm.googleapis.com/";
    private EditText searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_review_jobs, container, false);
        progressBar = mainView.findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);
        List = mainView.findViewById(R.id.reviewList);
        List.setHasFixedSize(true);
        searchView = mainView.findViewById(R.id.search_bar);
        //searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        List.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setClient();
        fetchTask(this);
        return mainView;
    }

    private void fetchTask(final onGenerator listnere) {
        reviewList = new ArrayList<>();
        reviewList.clear();
        FirebaseFirestore tasDb = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tasDb.collection("UserJob").whereEqualTo("clientId", user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    reviewList.add(documentSnapshot.toObject(_UserJobs.class));
                }
                progressBar.setVisibility(View.GONE);
                if (reviewList.size() > 0) {
                    adapter = new ReviewAdapter(getContext(), reviewList);
                    //adapter.setLongClick();
                    listnere.taskId(1, "succes");
                    List.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "No Review Jobs", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    @Override
    public void onClick(String id, final _UserJobs job, final int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("User").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String Token = document.getString("tokenId");
                        if (!Token.equals("null")) {
                            String Title = "Congratulation " + job.getWorkerName();
                            String subBody = "Hy " + job.getWorkerName() + " you Selected for task by " + "\n" + new Preference(getContext()).getUserName();
                            sendNotification(Token, Title, subBody);
                            reviewList.remove(position);
                            adapter.notifyDataSetChanged();
                            addCurrentJob(job);
                        } else {
                            Toast.makeText(getContext(), "This Worker Switch Off", Toast.LENGTH_LONG).show();
                        }
                    } else {

                    }
                } else {

                }
            }
        });
    }

    @Override
    public void taskId(int id, String error) {
        adapter.setLongClick(this);
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


    private void setClient() {
        apiService = Client.getClient(FirebasePush_NotificationUrl).create(APIService.class);
    }

    private void sendNotification(String workerToken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender Sender = new NotificationSender(workerToken, data);
        apiService.sendNotification(Sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Your Offer Sent to worker Successfully ....", Toast.LENGTH_LONG).show();
                    if (response.body().success != 1) {
                        Toast.makeText(getContext(), "Try Again please", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Toast.makeText(getContext(),"Bad Internet Worker side",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }


    private void addCurrentJob(final _UserJobs _task) {
        progressBar.setVisibility(View.VISIBLE);
        final _CurrentJob job = new _CurrentJob();
        job.setClientName(new Preference(getContext()).getUserName());
        job.setClientId(_task.getClientId());
        job.setWorkerName(_task.getWorkerName());
        job.setDetails(_task.getDetails());
        job.setPrice(_task.getPrice());
        job.setWorkerMail(_task.getWorkerMail());
        job.setTitle(_task.getTitle());
        job.setWorkerId(_task.getWorkerId());
        job.setTaskId(_task.getTaskId());
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("CurrentJob")
                .add(job).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull final Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    removeTask(job.getTaskId(), new onRemove() {
                        @Override
                        public void onDelete(int success) {
                            if (success == 1) {
                                FirebaseFirestore dbRef=FirebaseFirestore.getInstance();
                                dbRef.collection("User").document(_task.getWorkerId())
                                        .update("latestTitle",_task.getTitle()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), "Successfully done", Toast.LENGTH_SHORT).show();
                                        }else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), "Done with nothing", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Fail....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Error: " + task.getException().toString(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void removeTask(final int id, final onRemove removeListener) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserJob").whereEqualTo("taskId", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    for (DocumentSnapshot documentSnapshot : snapshot) {
                        db.collection("UserJob").document(documentSnapshot.getId())
                                .delete();
                    }

                    db.collection("Taskdb").whereEqualTo("taskId", id)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                QuerySnapshot snapshot = task.getResult();
                                DocumentSnapshot document = snapshot.getDocuments().get(0);
                                db.collection("Taskdb").document(document.getId())
                                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            removeListener.onDelete(1);
                                        } else {
                                            removeListener.onDelete(0);
                                        }
                                    }
                                });
                            }
                        }
                    });


                } else {

                }
            }
        });
    }

    private void searching(String nexText) {
        List<_UserJobs> list = new ArrayList<>();

            String query = nexText.toLowerCase().trim();
            for (_UserJobs jobs : reviewList) {
                if (jobs.getTitle().toLowerCase().contains(query)) {
                    list.add(jobs);
                }

            }
           // adapter= new ReviewAdapter(getContext(),list);
            adapter.setFilterList(list);





    }

}
