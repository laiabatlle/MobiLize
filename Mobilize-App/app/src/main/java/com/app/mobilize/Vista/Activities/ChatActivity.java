package com.app.mobilize.Vista.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private ImageButton sendMessageButton;
    private CircleImageView avatarUser;
    private TextView userName;
    private EditText userMessageInput;
    private RecyclerView userMessageList;

    private String user, username, avatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = getIntent().getStringExtra("user");
        username = getIntent().getStringExtra("username");
        avatar = getIntent().getStringExtra("avatar");

        setViews();
    }

    private void setViews() {

        userName =  (TextView) findViewById(R.id.custom_profile_name);
        avatarUser =  (CircleImageView) findViewById(R.id.custom_profile_avatar);

        userMessageList = (RecyclerView) findViewById(R.id.rv_chat);
        sendMessageButton = (ImageButton) findViewById(R.id.sendMessage);
        userMessageInput =  (EditText) findViewById(R.id.writeMessage);
    }

}
