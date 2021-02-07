package com.example.loginsignup.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.R;
import com.example.loginsignup.model._User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {

    private static final String isWorker = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mainView;
    private TextView details;
    private Button editBtn;
    private ImageView profileIcon;
    private ProgressBar loading;
    private _User worker = new _User();
    private String userType;
    private String mParam2;
    private CardView workerCardview;
    private EditText skill, education, city, country, number;
    private String isLatestTitle = "-1";

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(isWorker, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userType = getArguments().getString(isWorker);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        editBtn = mainView.findViewById(R.id.addBtn);
        profileIcon = mainView.findViewById(R.id.profile_img);
        details = mainView.findViewById(R.id.text);
        loading = mainView.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        workerCardview = mainView.findViewById(R.id.workerEditCard);
        worker.setSkill("Android ,Web, Ios");
        worker.setEducation("Engineering");
        setProfile();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUser(worker);
            }
        });
        return mainView;
    }

    private void setProfile() {
        if (userType.equals("worker")) {
            profileIcon.setImageResource(R.drawable.current_job_icon);
            workerCardview.setVisibility(View.VISIBLE);
            city = mainView.findViewById(R.id.cityField);
            country = mainView.findViewById(R.id.countryField);
            skill = mainView.findViewById(R.id.skillField);
            education = mainView.findViewById(R.id.eduField);
            number = mainView.findViewById(R.id.mobField);
            getWorker();
        } else {
            editBtn.setVisibility(View.GONE);
            getClient();
        }
    }

    private void getClient() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            db.collection("User").document(user.getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                _User user = documentSnapshot.toObject(_User.class);
                                loading.setVisibility(View.GONE);
                                details.setText("Name: " + user.getName() + "\n\nEmail: " + user.getEmail());
                            } else {
                                loading.setVisibility(View.GONE);
                                details.setText("Opps Check Your Internet");
                            }
                        }
                    });

        } else {
            loading.setVisibility(View.GONE);
        }

    }

    private void getWorker() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            db.collection("User").document(user.getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.contains("latestTitle")) {
                                    isLatestTitle = documentSnapshot.getString("latestTitle");
                                }
                                _User user = documentSnapshot.toObject(_User.class);
                                loading.setVisibility(View.GONE);
                                worker.setName(user.getName());
                                worker.setEmail(user.getEmail());
                                worker.setPassword(user.getPassword());
                                worker.setTokenId(user.getTokenId());
                                worker.setWorker(true);

                                details.setText("Name: " + user.getName() + "\n\nEmail: " + user.getEmail());
                                if (user.getSkill() != null) {
                                    String Details = "\n\nSkill: " + getValue(user.getSkill()) + "\n\nEducation: " + getValue(user.getEducation()) +
                                            "\n\nCountry: " + getValue(user.getCountry()) + "\n\nPhoneNo: " + getValue(user.getNumber()) + "\n\nCity: " + getValue(user.getCity());

                                    details.setText(details.getText() + Details);
                                }
                            } else {
                                loading.setVisibility(View.GONE);
                                details.setText("Opps Check Your Internet");
                            }
                        }
                    });

        } else {
            loading.setVisibility(View.GONE);
        }


    }

    private void setUser(_User user) {
        loading.setVisibility(View.VISIBLE);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        worker.setEducation(education.getText().toString());
        worker.setSkill(skill.getText().toString());
        worker.setCountry(country.getText().toString());
        worker.setCity(city.getText().toString());
        worker.setNumber(number.getText().toString());
        if (currentUser != null) {
            db.collection("User").document(currentUser.getUid())
                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        if (!isLatestTitle.equals("-1")) {
                            db.collection("User").document(currentUser.getUid())
                                    .update("latestTitle", isLatestTitle)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Fragment fragment = ProfileFragment.newInstance("worker", "");
                                                getActivity().getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.nav_fragment, fragment)
                                                        .commit();
                                            } else {

                                            }

                                        }
                                    });

                        } else {
                            Fragment fragment = ProfileFragment.newInstance("worker", "");
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.nav_fragment, fragment)
                                    .commit();
                        }


                    } else {

                    }
                    loading.setVisibility(View.GONE);
                }
            });
        } else {

            loading.setVisibility(View.GONE);
        }
    }

    private String getValue(String value) {
        if (value == null || value == "") {
            return "Not Given";
        } else {
            return value;
        }
    }
}