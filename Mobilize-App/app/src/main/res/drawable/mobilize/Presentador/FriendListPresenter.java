package com.app.mobilize.Presentador;

import android.content.Context;

import com.app.mobilize.Model.FriendsListModel;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.Interface.FriendsListInterface;

import java.util.ArrayList;

public class FriendListPresenter implements FriendsListInterface.Presenter, FriendsListInterface.TaskListener {

    private Usuari currentUser;
    private FriendsListInterface.View view;
    private FriendsListInterface.Model model;
    private AdapterUsuarios adapterReq;
    private AdapterUsuarios adapterFriends;
    private ArrayList<Usuari> listaFriends;
    private ArrayList<Usuari> listaReq;
    private Context mContext;

    public FriendListPresenter(Context context, FriendsListInterface.View view, Usuari currentUser) {
        this.mContext = context;
        this.view = view;
        this.currentUser = currentUser;
        model = new FriendsListModel(this);
        listaReq = new ArrayList<>();
        listaFriends = new ArrayList<>();
        adapterReq = new AdapterUsuarios(mContext, this.currentUser, listaFriends, "req");
        adapterFriends = new AdapterUsuarios(mContext, this.currentUser, listaFriends, "users");
    }

    @Override
    public void toGetFriendReq(String user) {
        view.setAdapterReqList(adapterReq);
        model.doGetFriendReq(user);
    }

    @Override
    public void toGetFriendList(String user) {
        view.setAdapterFriendList(adapterFriends);
        model.doGetFriendList(user);
    }

    @Override
    public void addListaReq(Usuari u) {
        listaReq.add(u);
        AdapterUsuarios ad = new AdapterUsuarios(mContext, this.currentUser, listaReq, "req");
        view.setAdapterReqList(ad);
    }

    @Override
    public void addListaFriends(Usuari u) {
        listaFriends.add(u);
        AdapterUsuarios ad = new AdapterUsuarios(mContext, this.currentUser, listaFriends, "users");
        view.setAdapterFriendList(ad);
    }

    @Override
    public void onSuccess(String type) {
        if (type.equals("req")){
            AdapterUsuarios ad = new AdapterUsuarios(mContext, this.currentUser, listaReq, "req");
            view.setAdapterReqList(ad);
        }
        else{
            AdapterUsuarios ad = new AdapterUsuarios(mContext, this.currentUser, listaFriends, "users");
            view.setAdapterFriendList(ad);
        }
    }

    @Override
    public void onError(String error) {
        if(view!=null){
            view.onError(error);
        }
    }
}
