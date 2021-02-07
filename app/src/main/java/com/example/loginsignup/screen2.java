package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public  class screen2 extends AppCompatActivity {

    private Button createBtn,loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        loginBtn=findViewById(R.id.btnlogin1);
        createBtn=findViewById(R.id.btnlogin0);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openlogin();
//            }
//        });


    }


//    private void openlogin() {
//        Intent intent = new Intent(this, login.class);
//        startActivity(intent);
//
//
//        btn1 =findViewById(R.id.btnlogin2);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                openotp();
//            }
//
//            private void openotp() {
//                Intent intent = new Intent(screen2.this, otp.class);
//                startActivity(intent);
//
//
//                btn2=findViewById(R.id.btnlogin0);
//                btn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        opensignup();
//
//                    }
//
//                    private void opensignup() {
//                        Intent intent = new Intent(screen2.this, signup.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//
//        });
//    }

}

