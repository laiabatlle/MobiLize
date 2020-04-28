package com.app.mobilize.Vista.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Interface.UserInterface;
import com.app.mobilize.Presentador.UserPresenter;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements UserInterface.View, View.OnClickListener  {

    private Usuari currentUser;
    private String imageUri, userperfil, CURRENT_STATE;
    private Button actionRequest, declineRequest;

    private UserInterface.Presenter presenter;

    public UserFragment(Usuari user, String username, String avatar, String current_state) {
        this.currentUser = user;
        this.userperfil = username;
        this.imageUri = avatar;
        this.CURRENT_STATE = current_state;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presenter = new UserPresenter(this);

        //Imatge de l'avatar de l'usuari:
        ImageView avatar = (ImageView) view.findViewById(R.id.AvatarIV);
        Glide.with(this).load(Uri.parse(imageUri)).error(R.drawable.ic_user).into(avatar);

        //TextView de l'username:
        TextView username = (TextView) view.findViewById(R.id.usernameTV);
        username.setText(userperfil);

        //Boto de guardar canvis. S'actualitza la Base de Dades amb els parametres seleccionats als diferents widgets:
        actionRequest = (Button) view.findViewById(R.id.actionRequestButton);
        declineRequest = (Button) view.findViewById(R.id.declineRequestButton);
        actionRequest.setText(getResources().getString(R.string.SendFriendReq));
        declineRequest.setText(getResources().getString(R.string.DeclineFriendReq));
        declineRequest.setVisibility(View.INVISIBLE);
        MaintanceofButtons(currentUser.getUsername(), userperfil, CURRENT_STATE);
        actionRequest.setOnClickListener(this);
        declineRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionRequestButton:
                handleActionReq(currentUser, userperfil, CURRENT_STATE);
                break;

            case R.id.declineRequestButton:
                handleDeclineReq(currentUser, userperfil, CURRENT_STATE);
                break;

            default:
                break;
        }
    }

    @Override
    public void handleActionReq(Usuari currentUser, String userperfil, String CURRENT_STATE) {
        presenter.toActionReq(this.currentUser.getUsername(), this.userperfil, this.CURRENT_STATE);
    }

    @Override
    public void handleDeclineReq(Usuari currentUser, String userperfil, String CURRENT_STATE) {
        presenter.toDeclineReq(this.currentUser.getUsername(), this.userperfil, this.CURRENT_STATE);
    }

    @Override
    public void MaintanceofButtons(String currentUser, String userperfil, String current_state) {
        presenter.MaintanceofButtons(currentUser, userperfil, current_state);
    }

    @Override
    public void onSuccess(String username, String mess) {
        if (mess.equals("newFriendsMes")){
            Toast.makeText(getContext(), username + getResources().getString(R.string.NewFriendMes), Toast.LENGTH_SHORT).show();
        }
        else if (mess.equals("unfriendsMes")){
            Toast.makeText(getContext(), username + getResources().getString(R.string.UnfriendMes), Toast.LENGTH_SHORT).show();
        }
        else if (mess.equals("SentFriendReqMes")){
            Toast.makeText(getContext(), getResources().getString(R.string.SendFriendReqMes), Toast.LENGTH_SHORT).show();
        }
        else if (mess.equals("CancelFriendReqMes")){
            Toast.makeText(getContext(), username + getResources().getString(R.string.CancelFriendReqMes), Toast.LENGTH_SHORT).show();
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
