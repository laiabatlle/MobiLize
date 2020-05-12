package com.app.mobilize.Vista.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.mobilize.Presentador.Interface.UserInterface;
import com.app.mobilize.Presentador.UserPresenter;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserActivity extends AppCompatActivity implements UserInterface.View, View.OnClickListener  {

    private String currentUser;
    private String imageUri, userperfil, email, privacity, CURRENT_STATE;
    private Button messageButton, actionRequest, declineRequest;

    private UserInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.currentUser = SaveSharedPreference.getEmail(this);
        this.userperfil = this.getIntent().getStringExtra("userperfil");
        this.imageUri = this.getIntent().getStringExtra("imageUri");
        this.email = this.getIntent().getStringExtra("email");
        this.privacity = this.getIntent().getStringExtra("privacity");
        this.CURRENT_STATE = this.getIntent().getStringExtra("CURRENT_STATE");;
        setViews();
    }

    private void setViews() {
        presenter = new UserPresenter(this);

        //Imatge de l'avatar de l'usuari:
        ImageView avatar = (ImageView) findViewById(R.id.AvatarIV);
        Glide.with(this).load(Uri.parse(imageUri)).error(R.drawable.ic_user).into(avatar);

        TextView username = (TextView) findViewById(R.id.usernameTV);
        username.setText(userperfil);

        TextView privateText = (TextView) findViewById(R.id.privateText);
        if(this.privacity.equals("private") && !this.CURRENT_STATE.equals("friends")){
            privateText.setText(getResources().getString(R.string.PrivateAccount) + this.userperfil + ".");
            privateText.setVisibility(View.VISIBLE);
        }
        messageButton = (Button) findViewById(R.id.messageButtom);
        actionRequest = (Button) findViewById(R.id.actionRequestButton);
        declineRequest = (Button) findViewById(R.id.declineRequestButton);
        MaintanceofButtons();
        declineRequest.setText(getResources().getString(R.string.DeclineFriendReq));
        MaintanceofButtons(currentUser, email, CURRENT_STATE);
        messageButton.setOnClickListener(this);
        actionRequest.setOnClickListener(this);
        declineRequest.setOnClickListener(this);
    }

    private void MaintanceofButtons() {
        switch (this.CURRENT_STATE) {
            case "not_friends":
                actionRequest.setText(getResources().getString(R.string.SendFriendReq));
                declineRequest.setVisibility(View.INVISIBLE);
                break;
            case "request_sent":
                actionRequest.setText(getResources().getString(R.string.CancelFriendReq));
                declineRequest.setVisibility(View.INVISIBLE);
                break;
            case "request_received":
                actionRequest.setText(getResources().getString(R.string.DeclineFriendReq));
                declineRequest.setVisibility(View.VISIBLE);
                break;
            default:
                actionRequest.setText(getResources().getString(R.string.Unfriend));
                declineRequest.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messageButtom:
                handleGoToChat();
                break;

            case R.id.actionRequestButton:
                handleActionReq(email, CURRENT_STATE);
                break;

            case R.id.declineRequestButton:
                handleDeclineReq(email, CURRENT_STATE);
                break;

            default:
                break;
        }
    }

    private void handleGoToChat() {
        Intent intent = new Intent( this, ChatActivity.class);
        intent.putExtra("user", email);
        intent.putExtra("username", userperfil);
        intent.putExtra("avatar", imageUri);

        startActivity(intent);
    }

    @Override
    public void handleActionReq(String user, String CURRENT_STATE) {
        presenter.toActionReq(this.currentUser, user, this.CURRENT_STATE);
    }

    @Override
    public void handleDeclineReq(String user, String CURRENT_STATE) {
        presenter.toDeclineReq(this.currentUser, user, this.CURRENT_STATE);
    }

    @Override
    public void MaintanceofButtons(String currentUser, String user, String current_state) {
        presenter.MaintanceofButtons(currentUser, user, current_state);
    }

    @Override
    public void onSuccess(String username, String mess) {
        if (mess.equals("newFriendsMes")){
            Toast.makeText(this, username + getResources().getString(R.string.NewFriendMes), Toast.LENGTH_SHORT).show();
        }
        else if (mess.equals("unfriendsMes")){
            Toast.makeText(this, username + getResources().getString(R.string.UnfriendMes), Toast.LENGTH_SHORT).show();
        }
        else if (mess.equals("SentFriendReqMes")){
            Toast.makeText(this, getResources().getString(R.string.SendFriendReqMes), Toast.LENGTH_SHORT).show();
        }
        else if (mess.equals("CancelFriendReqMes")){
            Toast.makeText(this, username + getResources().getString(R.string.CancelFriendReqMes), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setActionButton(String buttonText) {
        switch (buttonText) {
            case "CancelFriendReq":
                actionRequest.setText(getResources().getText(R.string.CancelFriendReq));
                break;
            case "SendFriendReq":
                actionRequest.setText(getResources().getText(R.string.SendFriendReq));
                break;
            case "Unfriend":
                actionRequest.setText(getResources().getText(R.string.Unfriend));
                break;
            case "AcceptFriendReq":
                actionRequest.setText(getResources().getText(R.string.AcceptFriendReq));
                break;
        }
    }

    @Override
    public void setCurrentState(String current_state) {
        this.CURRENT_STATE = current_state;
    }

    @Override
    public void setDeclinenButton(boolean b) {
        if (b) declineRequest.setVisibility(View.VISIBLE);
        else declineRequest.setVisibility(View.INVISIBLE);
    }
}
