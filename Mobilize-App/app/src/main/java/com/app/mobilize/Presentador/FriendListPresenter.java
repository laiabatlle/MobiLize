package com.app.mobilize.Presentador;

import android.util.Log;

import com.app.mobilize.Model.FriendsListModel;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.Interface.FriendsListInterface;

import java.util.ArrayList;

public class FriendListPresenter implements FriendsListInterface.Presenter, FriendsListInterface.TaskListener {

    private Usuari currentUser;
    private FriendsListInterface.View view;
    private FriendsListInterface.Model model;
    private AdapterUsuarios adapterUsuarios;
    private ArrayList<Usuari> listaUsuarios;

    public FriendListPresenter(FriendsListInterface.View view, Usuari currentUser) {
        this.view = view;
        this.currentUser = currentUser;
        model = new FriendsListModel(this);
        listaUsuarios = new ArrayList<>();
        adapterUsuarios = new AdapterUsuarios(this.currentUser, listaUsuarios);
    }

    @Override
    public void toGetFriendList(String username) {
        view.setAdapterList(adapterUsuarios);
        model.doGetFriendList(username);
    }

    @Override
    public void addLista(Usuari u) {
        listaUsuarios.add(u);
        for (Usuari us : listaUsuarios){
            Log.d("PresenterAddLlista", us.getUsername());
        }
        AdapterUsuarios ad = new AdapterUsuarios(this.currentUser, listaUsuarios);
        view.setAdapterList(ad);
    }

    @Override
    public void onSuccess() {
        if (listaUsuarios.isEmpty()) Log.d("PresenterSuccess", "emptyyyy");
        AdapterUsuarios ad = new AdapterUsuarios(this.currentUser, listaUsuarios);
        view.setAdapterList(ad);
    }

    @Override
    public void onError(String error) {
        if(view!=null){
            view.onError(error);
        }
    }
}
