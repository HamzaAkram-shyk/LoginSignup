package com.example.loginsignup.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.loginsignup.Preference;
import com.example.loginsignup.R;
import com.example.loginsignup.clientfragment.CurrentJob;
import com.example.loginsignup.login;
import com.example.loginsignup.workerfragment.MyJobs;
import com.example.loginsignup.workerfragment.PostTask;
import com.example.loginsignup.workerfragment.ReviewJobs;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class ClientActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Client Online");
        getSupportActionBar().setTitle(new Preference(this).getUserName()+" Online");
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.reviewjobs);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_fragment,new ReviewJobs())
                .commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.reviewjobs){
                    Toast.makeText(getApplicationContext(),"Hello ",Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment,new ReviewJobs())
                            .commit();
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);

                } else if(item.getItemId()==R.id.postjobs){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment,new PostTask())
                            .commit();
                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                } else if(item.getItemId()==R.id.myjobs){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment,new MyJobs())
                            .commit();
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                } else if(item.getItemId()==R.id.hire){
                    Fragment fragment=new CurrentJob();
                    Bundle arg=new Bundle();
                    arg.putBoolean(CurrentJob.JOB_TYPE,false);
                    fragment.setArguments(arg);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment,fragment)
                            .commit();
                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
                }else if(item.getItemId()==R.id.client_profile){
                    Fragment fragment=ProfileFragment.newInstance("r","");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_fragment,fragment)
                            .commit();
                    bottomNavigationView.getMenu().getItem(4).setChecked(true);
                }

                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                new Preference(getApplicationContext()).setUserType(-1);
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}