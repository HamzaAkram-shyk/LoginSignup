package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.activity.ClientActivity;
import com.example.loginsignup.activity.WorkerActivity;
import com.example.loginsignup.model._User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;

public class login extends AppCompatActivity implements View.OnClickListener {
    private TextView register, forgotpasssword ;
    private EditText etemail, etpassword;
    private Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        login = (Button) findViewById(R.id.btnlogin);
        login.setOnClickListener(this);

        etemail = (EditText)findViewById(R.id.etemail);
        etpassword = (EditText)findViewById(R.id.etpassword);

        mAuth = FirebaseAuth.getInstance();
        forgotpasssword = (TextView)findViewById(R.id.tvforgotpassord);
        forgotpasssword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnlogin:
                userlogin();
                break;


            case R.id.tvforgotpassord:
                startActivity(new Intent(this, forgotpassword.class ));
                break;
        }

    }

    private void userlogin() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Authenticating.....");
        if(email.isEmpty()){
            etemail.setError("Email is required");
        }
        else if(password.isEmpty()){
            etpassword.setError("Enter Password");
        }
        if(email.equals("")){
            etemail.setError("Enter Email");
        }
        else if(password.length()< 6){
            etpassword.setError("Min Password length is 6 character");
        }
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(),"Hello there ...",Toast.LENGTH_LONG).show();
                if(task.isSuccessful()) {
                  final   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user!=null) {
                         final FirebaseFirestore db=FirebaseFirestore.getInstance();

                        DocumentReference docRef = db.collection("User").document(user.getUid());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        final _User _user=  document.toObject(_User.class);
                                        new Preference(getApplicationContext()).setUserName(_user.getName());
                                       if(_user.isWorker()){
                                           new Preference(getApplicationContext()).setUserType(0);
                                           generateToken(new GenerateToken() {
                                               @Override
                                               public void onToken(String token) {
                                                   if(!token.equals("null")){
                                                       Task<Void> Ref = db.collection("User").document(user.getUid()).
                                                               update("tokenId",token).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               if(task.isSuccessful()){
                                                                   dialog.cancel();
                                                                   startActivity(new Intent(getApplicationContext(), WorkerActivity.class));
                                                                   finish();
                                                               }else{
                                                                   dialog.cancel();
                                                                   Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                                                               }
                                                           }
                                                       });
                                                   }
                                               }
                                           });

                                       }else{
                                           new Preference(getApplicationContext()).setUserType(1);
                                           startActivity(new Intent(getApplicationContext(), ClientActivity.class));
                                           finish();
                                       }
                                    } else {
                                        //Log.d(TAG, "No such document");
                                        dialog.cancel();
                                    }
                                } else {
                                    //Log.d(TAG, "get failed with ", task.getException());
                                    dialog.cancel();
                                }
                            }
                        });
                        //redirect to user profile


                    } else {
                        //user.sendEmailVerification();
                        dialog.cancel();
                        Toast.makeText(login.this, "check your email to verify your account !", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                    Toast.makeText(login.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                }
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

