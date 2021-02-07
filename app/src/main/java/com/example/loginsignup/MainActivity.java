package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.activity.ClientActivity;
import com.example.loginsignup.activity.WorkerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Preference preference = new Preference(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                int type = new Preference(getApplicationContext()).getUserType();
                if (type == 1) {
                    startActivity(new Intent(getApplicationContext(), ClientActivity.class));
                    finish();
                } else if(type==0){
                    startActivity(new Intent(getApplicationContext(), WorkerActivity.class));
                    finish();
                } else if(type==-1){
                    startActivity(new Intent(getApplicationContext(), screen2.class));
                    finish();
                }


            }
        }, 1 * 1000);


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//
//            startActivity(new Intent(getApplicationContext(), screen2.class));
//            finish();
//            return;
//
//        }






    }
}

