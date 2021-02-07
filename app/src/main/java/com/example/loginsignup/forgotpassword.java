package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button ResetpasswordButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailEditText = (EditText)findViewById(R.id.etemail);
        ResetpasswordButton = (Button) findViewById(R.id.btnreset);

        auth = FirebaseAuth.getInstance();

        ResetpasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
                
            }
        });

    }

    private void resetpassword() {
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("email is required");
            emailEditText.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(forgotpassword.this, "check your email to reset your password", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(forgotpassword.this, "Try again ", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}