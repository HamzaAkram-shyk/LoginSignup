package com.example.loginsignup.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.loginsignup.Preference;
import com.example.loginsignup.R;
import com.example.loginsignup.clientfragment.Alljobs;
import com.example.loginsignup.clientfragment.ApplyJob;
import com.example.loginsignup.clientfragment.CurrentJob;
import com.example.loginsignup.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class WorkerActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public AppBarConfiguration mAppBarConfiguration;
    public NavController navController;
    boolean hasJob = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(new Preference(this).getUserName() + " Online");
        bottomNavigationView = findViewById(R.id.navigation);
        //  bottomNavigationView.setSelectedItemId(R.id.alljobs);
        if (getIntent().getExtras() != null) {
            hasJob = getIntent().getExtras().getBoolean("hasJob");
        }
        if (hasJob) {
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            Fragment fragment = new CurrentJob();
            Bundle arg = new Bundle();
            arg.putBoolean(CurrentJob.JOB_TYPE, true);
            fragment.setArguments(arg);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_fragment, fragment)
                    .commit();
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            Fragment fragment = new Alljobs();
            Bundle arg = new Bundle();
            arg.putBoolean(Alljobs._KEY, false);
            fragment.setArguments(arg);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_fragment, fragment)
                    .commit();

        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.currentjob) {
                    Fragment fragment = new CurrentJob();
                    Bundle arg = new Bundle();
                    arg.putBoolean(CurrentJob.JOB_TYPE, true);
                    fragment.setArguments(arg);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment, fragment)
                            .commit();
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);

                } else if (item.getItemId() == R.id.alljobs) {

                    Fragment fragment = new Alljobs();
                    Bundle arg = new Bundle();
                    arg.putBoolean(Alljobs._KEY, false);
                    fragment.setArguments(arg);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment, fragment)
                            .commit();
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                } else if (item.getItemId() == R.id.applyjobs) {
                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment, new ApplyJob())
                            .commit();
                } else if (item.getItemId() == R.id.worker_profile) {
                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
                    Fragment fragment = ProfileFragment.newInstance("worker", "");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment, fragment)
                            .commit();
                } else if(item.getItemId() == R.id.recommend){
                    bottomNavigationView.getMenu().getItem(4).setChecked(true);
                    Fragment fragment = new Alljobs();
                    Bundle arg = new Bundle();
                    arg.putBoolean(Alljobs._KEY, true);
                    fragment.setArguments(arg);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment, fragment)
                            .commit();
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                final ProgressDialog dialog = new ProgressDialog(WorkerActivity.this);
                dialog.setTitle("SignOut....");
                dialog.show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                db.collection("User").document(user.getUid())
                        .update("tokenId", "null").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            new Preference(getApplicationContext()).setUserType(-1);
                            dialog.cancel();
                            startActivity(new Intent(getApplicationContext(), login.class));
                            finish();
                        }
                    }
                });

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}