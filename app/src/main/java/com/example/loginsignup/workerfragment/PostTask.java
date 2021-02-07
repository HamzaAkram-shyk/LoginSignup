package com.example.loginsignup.workerfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.Preference;
import com.example.loginsignup.R;
import com.example.loginsignup.model._Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class PostTask extends Fragment implements onGenerator, View.OnClickListener {
    private View mainView;
    private Button postBtn;
    private List<String> categoryList;
    private Spinner categorySpinner,workTypeSpinner;
    private String category="null",isOnline="";
    TextView title,workType;
    private EditText price,titleField,detail;
    private ProgressBar progressBar;
    private EditText location;
    List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_post_task, container, false);
        postBtn = mainView.findViewById(R.id.postBtn);
        postBtn.setOnClickListener(this);
        categoryList = new ArrayList<>();
        categorySpinner=mainView.findViewById(R.id.spinnerId);
        title=mainView.findViewById(R.id.heading);
        price=mainView.findViewById(R.id.price);
        list=new ArrayList<>();
        titleField=mainView.findViewById(R.id.title);
        detail=mainView.findViewById(R.id.details);
        workTypeSpinner=mainView.findViewById(R.id.workTypeSpinner);
        location=mainView.findViewById(R.id.location);
        workType=mainView.findViewById(R.id.workType);
        progressBar=mainView.findViewById(R.id.loading);
        setCategoryList();
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryList);
        ArrayAdapter<String> workTypeAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
        workTypeSpinner.setAdapter(workTypeAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=categoryList.get(position);
                title.setText(category+" Selected");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        workTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isOnline=list.get(position);
                workType.setText(isOnline+" Selected");
                if(position>0){
                    location.setVisibility(View.VISIBLE);
                }else{
                    location.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return mainView;
    }

    private void setCategoryList() {
        categoryList.clear();
        category="Mechanic";
        title.setText(category+" Selected");
        categoryList.add("Mechanic");
        categoryList.add("Electrician");
        categoryList.add("Maid");
        categoryList.add("Tutor");
        categoryList.add("Beautician");
        categoryList.add("Cook");
        categoryList.add("Tailor");
        categoryList.add("Painter");
        categoryList.add("Developer");
        categoryList.add("Physical Trainer");
        categoryList.add("Virtual Assistant");

        list.clear();
        list.add("Online");
        list.add("Physical");
    }

    private void generateTaskId(final onGenerator generator) {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("IDGenerator").document("Generator").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String taskId = document.getString("taskId");
                            final int TaskId = Integer.parseInt(taskId);
                            int newId = Integer.parseInt(taskId);
                            newId++;
                            db.collection("IDGenerator").document("Generator")
                                    .update("taskId", String.valueOf(newId))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                generator.taskId(TaskId, "Success");
                                            } else {
                                                progressBar.setVisibility(View.GONE);
                                                generator.taskId(-1, task.getException().getMessage());
                                            }


                                        }
                                    });


                        } else {
                            progressBar.setVisibility(View.GONE);
                            generator.taskId(-1, task.getException().toString());
                        }
                    }
                });

    }

    @Override
    public void taskId(int id, String error) {
        if (id > -1) {
            //Toast.makeText(getContext(), "Task Id = " + id, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
            postTask(id, new onGenerator() {
                @Override
                public void taskId(int id, String error) {
                    if(id==1){
                        progressBar.setVisibility(View.GONE);
                        price.setText("");
                        detail.setText("");
                        titleField.setText("");
                        Toast.makeText(getContext(),"Task Posted Successfully..",Toast.LENGTH_SHORT).show();
                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Error"+error,Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Fail: " + error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.postBtn) {
            progressBar.setVisibility(View.VISIBLE);
            generateTaskId(this);
        }
    }

    private void postTask(int id,final onGenerator listener){
        _Task task=new _Task();

        task.setTaskCategory(category);
        if(isOnline.equals("Online")){
            task.setWorkLocation("Online");
            task.setOnline(true);
        }else{
            task.setWorkLocation(location.getText().toString());
            task.setOnline(false);
        }

        task.setTasKPrice(Integer.parseInt(price.getText().toString()));
        task.setTaskDetails(detail.getText().toString());
        task.setTaskTitle(titleField.getText().toString());
        task.setTaskId(id);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            task.setClientId(firebaseUser.getUid());
            task.setClientName(new Preference(getContext()).getUserName());
        }else{
            task.setClientId("NullId");
        }

      FirebaseFirestore taskDb=FirebaseFirestore.getInstance();
      taskDb.collection("Taskdb")
              .add(task).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
          @Override
          public void onComplete(@NonNull Task<DocumentReference> task) {
              if(task.isSuccessful()){
                  listener.taskId(1,"Success");
              }else{
                  listener.taskId(-1,task.getException().toString());
              }

          }
      });
    }


}

interface onGenerator {
    void taskId(int id, String error);
}