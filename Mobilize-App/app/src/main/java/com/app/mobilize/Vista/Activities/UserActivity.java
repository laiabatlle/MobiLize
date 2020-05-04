package com.app.mobilize.Vista.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Interface.UserInterface;
import com.app.mobilize.Presentador.UserPresenter;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserActivity extends AppCompatActivity implements UserInterface.View, View.OnClickListener  {

    private String currentUser;
    private String imageUri, userperfil, CURRENT_STATE;
    private Button actionRequest, declineRequest;

    private UserInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.currentUser = SaveSharedPreference.getEmail(this);
        this.userperfil = this.getIntent().getStringExtra("userperfil");
        this.imageUri = this.getIntent().getStringExtra("imageUri");;
        this.CURRENT_STATE = this.getIntent().getStringExtra("CURRENT_STATE");;
        setViews();
    }

    private void setViews() {
        presenter = new UserPresenter(this);

        //Imatge de l'avatar de l'usuari:
        ImageView avatar = (ImageView) findViewById(R.id.AvatarIV);
        Glide.with(this).load(Uri.parse(imageUri)).error(R.drawable.ic_user).into(avatar);

        //TextView de l'username:
        TextView username = (TextView) findViewById(R.id.usernameTV);
        username.setText(userperfil);

        //Boto de guardar canvis. S'actualitza la Base de Dades amb els parametres seleccionats als diferents widgets:
        actionRequest = (Button) findViewById(R.id.actionRequestButton);
        declineRequest = (Button) findViewById(R.id.declineRequestButton);
        actionRequest.setText(getResources().getString(R.string.SendFriendReq));
        declineRequest.setText(getResources().getString(R.string.DeclineFriendReq));
        declineRequest.setVisibility(View.INVISIBLE);
        MaintanceofButtons(currentUser, userperfil, CURRENT_STATE);
        actionRequest.setOnClickListener(this);
        declineRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionRequestButton:
                handleActionReq(userperfil, CURRENT_STATE);
                break;

            case R.id.declineRequestButton:
                handleDeclineReq(userperfil, CURRENT_STATE);
                break;

            default:
                break;
        }
    }

    @Override
    public void handleActionReq(String userperfil, String CURRENT_STATE) {
        presenter.toActionReq(this.currentUser, this.userperfil, this.CURRENT_STATE);
    }

    @Override
    public void handleDeclineReq(String userperfil, String CURRENT_STATE) {
        presenter.toDeclineReq(this.currentUser, this.userperfil, this.CURRENT_STATE);
    }

    @Override
    public void MaintanceofButtons(String currentUser, String userperfil, String current_state) {
        presenter.MaintanceofButtons(currentUser, userperfil, current_state);
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
