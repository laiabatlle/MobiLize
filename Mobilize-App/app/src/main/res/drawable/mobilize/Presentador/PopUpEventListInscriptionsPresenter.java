package com.app.mobilize.Presentador;

import android.content.Context;

import com.app.mobilize.Model.PopUpEventListInscriptionsModel;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.Interface.BuscadorUserInterface;

import java.util.ArrayList;

public class PopUpEventListInscriptionsPresenter implements BuscadorUserInterface.Presenter, BuscadorUserInterface.TaskListener {

    private String event;
    Usuari currentUser;
    private AdapterUsuarios adapterUsuarios;
    private ArrayList<Usuari> listaUsuarios;
    private BuscadorUserInterface.View view;
    private BuscadorUserInterface.Model model;
    private Context mContext;

    public PopUpEventListInscriptionsPresenter(Context context, BuscadorUserInterface.View view, Usuari current_user, String event) {
        this.mContext = context;
        this.view = view;
        this.currentUser = current_user;
        this.event = event;
        model = new PopUpEventListInscriptionsModel(this, this.event);
        listaUsuarios = new ArrayList<>();
        adapterUsuarios = new AdapterUsuarios(mContext, currentUser, listaUsuarios, "users");
    }

    @Override
    public void toGetUserList() {
        view.setAdapterList(adapterUsuarios);
        model.doGetUserList(this.currentUser);
    }

    @Override
    public void toSearchUser(String username) {
    }

    @Override
    public void addLista(Usuari u) {
        listaUsuarios.add(u);
        adapterUsuarios.notifyDataSetChanged();
        view.setAdapterList(adapterUsuarios);
    }

    @Override
    public void onSuccess() {
        if(view!=null) {
            view.enableInputs();
            adapterUsuarios.notifyDataSetChanged();
            view.setBuscador();
        }
    }

    @Override
    public void onError(String error) {
        if(view!=null){
            view.enableInputs();
            view.onError(error);
        }
    }
}

