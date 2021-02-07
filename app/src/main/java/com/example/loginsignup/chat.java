package com.example.loginsignup;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cenkgun.chatbar.ChatBarView;
import com.example.loginsignup.R;

public class chat extends AppCompatActivity {

    TextView textView;
    ChatBarView chatBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textView = (TextView)findViewById(R.id.textchat);
        chatBarView=(ChatBarView)findViewById(R.id.chatbar);

        chatBarView.setMessageBoxHint("Enter your message");
        chatBarView.setSendClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(chatBarView.getMessageText());
            }
        });
    }
}