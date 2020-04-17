package com.app.mobilize.Presentador;

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

    public FriendListPresenter(FriendsListInterface.View view, Usuari currentUser) {
        this.view = view;
        this.currentUser = currentUser;
        model = new FriendsListModel(this);
        listaReq = new ArrayList<>();
        listaFriends = new ArrayList<>();
        adapterReq = new AdapterUsuarios(this.currentUser, listaFriends);
        adapterFriends = new AdapterUsuarios(this.currentUser, listaFriends);
    }

    @Override
    public void toGetFriendReq(String username) {
        view.setAdapterReqList(adapterReq);
        model.doGetFriendReq(username);
    }

    @Override
    public void toGetFriendList(String username) {
        view.setAdapterFriendList(adapterFriends);
        model.doGetFriendList(username);
    }

    @Override
    public void addListaReq(Usuari u) {
        listaReq.add(u);
        AdapterUsuarios ad = new AdapterUsuarios(this.currentUser, listaReq);
        view.setAdapterReqList(ad);
    }

    @Override
    public void addListaFriends(Usuari u) {
        listaFriends.add(u);
        AdapterUsuarios ad = new AdapterUsuarios(this.currentUser, listaFriends);
        view.setAdapterFriendList(ad);
    }

    @Override
    public void onSuccess(String type) {
        if (type.equals("req")){
            AdapterUsuarios ad = new AdapterUsuarios(this.currentUser, listaReq);
            view.setAdapterReqList(ad);
        }
        else{
            AdapterUsuarios ad = new AdapterUsuarios(this.currentUser, listaFriends);
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
