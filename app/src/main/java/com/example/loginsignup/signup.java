package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginsignup.activity.ClientActivity;
import com.example.loginsignup.activity.WorkerActivity;
import com.example.loginsignup.model._User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class signup extends AppCompatActivity {

    private EditText etemail, etpassword, etconfirmpassword, Name;
    private String email, password, confirmpassword;
    private Chip userChip, WorkerChip;
    private boolean isWorker = true;
    private boolean check = true;
    private FirebaseFirestore userdb;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        etconfirmpassword = findViewById(R.id.etconfirmpassword);
        Name = findViewById(R.id.nameField);
        userChip = findViewById(R.id.userChip);
        WorkerChip = findViewById(R.id.worker);
        userChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {
                    check = true;
                    isWorker = false;
                    Toast.makeText(getApplicationContext(),"Check: "+check,Toast.LENGTH_SHORT).show();
                } else if (isChecked==false){
                    check = false;
                    Toast.makeText(getApplicationContext(),"Check: "+check,Toast.LENGTH_SHORT).show();
                }
            }
        });

        WorkerChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {
                    check = true;
                    isWorker = true;
                    Toast.makeText(getApplicationContext(),"Check: "+check,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void btnsignupClick(View view) {
        email = etemail.getText().toString().trim();
        password = etpassword.getText().toString().trim();
        confirmpassword = etconfirmpassword.getText().toString().trim();

        if (email.equals("")) {
            etemail.setError("Enter Email");
        } else if (password.equals("")) {
            etpassword.setError("Enter Password");
        } else if (confirmpassword.equals("")) {
            etconfirmpassword.setError("confirm password");
        } else if (Name.getText().toString().trim().equals("")) {
            Name.setError("Enter Name");
        } else if (!confirmpassword.equals(password)) {
            etconfirmpassword.setError(" password not correct");
        } else {
            signUp();

        }
    }


    private void signUp() {
        dialog=new ProgressDialog(this);

            dialog.setTitle("Account Creating.......");
            dialog.setMessage("Please Wait your Account is Creating");
            dialog.show();
            createAccount(isWorker);


    }


    private void createAccount(final boolean sigUpType) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userdb =FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user user = new user(email, password, confirmpassword);
                            final _User _user=new _User(Name.getText().toString(),email,password,"",isWorker);
//                            user.setName(Name.getText().toString());
//                            user.setWorker(sigUpType);
                           final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                            userdb.collection("User")
                                    .document(firebaseUser.getUid())
                                    .set(_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   // dialog.dismiss();
                                    new Preference(getApplicationContext()).setUserName(_user.getName());
                                    if(isWorker){
                                        generateToken(new GenerateToken() {
                                            @Override
                                            public void onToken(String token) {
                                                if(!token.equals("null")){
                                                    Task<Void> Ref = userdb.collection("User").document(firebaseUser.getUid()).
                                                            update("tokenId",token).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                dialog.cancel();
                                                                startActivity(new Intent(getApplicationContext(), WorkerActivity.class));
                                                                finish();
                                                            }else{
                                                                dialog.cancel();
                                                                Toast.makeText(getApplicationContext(),"Fail in Token",Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    dialog.cancel();
                                                    Toast.makeText(getApplicationContext(),"Fail in Token",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        new Preference(getApplicationContext()).setUserType(0);
                                        startActivity(new Intent(signup.this, WorkerActivity.class));

                                    }else{
                                        new Preference(getApplicationContext()).setUserType(1);
                                        startActivity(new Intent(signup.this, ClientActivity.class));

                                    }
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(signup.this, "Failed to create user: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });


//                            FirebaseDatabase.getInstance().getReference("user")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(signup.this, "User created succssfully", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(signup.this, MainActivity.class));
//                                        finish();
//                                    } else {
//                                        Toast.makeText(signup.this, "Failed to create user: " + task.getException(), Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                }
//                            });

                        } else{
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Something Wrong"+task.getException().toString(),Toast.LENGTH_LONG).show();
                        }
                    }




                });


    }


    private void token(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                           // Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void generateToken(final GenerateToken token){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            token.onToken("null");
                            return;
                        }

                        // Get new FCM registration token
                        String tokenId = task.getResult();
                        token.onToken(tokenId);


                    }
                });

    }



}
