package com.app.mobilize.Vista.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Messages;
import com.app.mobilize.Presentador.Adapter.MessagesAdapter;
import com.app.mobilize.Presentador.ChatPresenter;
import com.app.mobilize.Presentador.Interface.ChatInterface;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, ChatInterface.View {

    private ImageButton sendMessageButton;
    private CircleImageView avatarUser;
    private TextView userName;
    private EditText userMessageInput;
    private RecyclerView userMessageList;

    private String currentUser, user, username, avatar, saveCurrentDate, saveCurrentTime;
    private List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagesAdapter messagesAdapter;

    private ChatInterface.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        currentUser = SaveSharedPreference.getEmail(this);
        user = getIntent().getStringExtra("user");
        username = getIntent().getStringExtra("username");
        avatar = getIntent().getStringExtra("avatar");

        presenter = new ChatPresenter(this, this, currentUser, user, username, avatar);

        handleChargeMessages();
        setViews();
    }

    private void setViews() {
        userName =  (TextView) findViewById(R.id.custom_profile_name);
        userName.setText(username);
        avatarUser =  (CircleImageView) findViewById(R.id.custom_profile_avatar);
        Glide.with(this).load(Uri.parse(avatar)).into(avatarUser);

        sendMessageButton = (ImageButton) findViewById(R.id.sendMessage);
        userMessageInput =  (EditText) findViewById(R.id.writeMessage);

        sendMessageButton.setOnClickListener(this);

        userMessageList = (RecyclerView) findViewById(R.id.rv_chat);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessageList.setHasFixedSize(true);
        userMessageList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessage:
                handleSendMessage();
            default:
                break;
        }
    }

    @Override
     public void handleChargeMessages() {
        this.presenter.toGetMessageList();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void setAdapterList(MessagesAdapter adapterMessages) {
        userMessageList.setAdapter(adapterMessages);
        int pos = userMessageList.getAdapter().getItemCount();
        if(pos != 0) {
            userMessageList.smoothScrollToPosition(pos - 1);
        }
    }

    @Override
    public void handleSendMessage() {
        final String message_text = userMessageInput.getText().toString();
        if (TextUtils.isEmpty(message_text)){
            Toast.makeText(this, "Please type a message first", Toast.LENGTH_SHORT).show();
        }
        else{
            Calendar calFordDate = Calendar.getInstance();
            SimpleDateFormat curretDate = new SimpleDateFormat("dd/MM/yyyy");
            saveCurrentDate = curretDate.format(calFordDate.getTime());
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            saveCurrentTime = currentTime.format(calFordDate.getTime());

            presenter.toSendMessage(saveCurrentDate, saveCurrentTime, currentUser, message_text);
            userMessageInput.setText("");
        }
    }
}
