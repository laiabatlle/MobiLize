package com.app.mobilize.Presentador;

import com.app.mobilize.Model.BuscadorUserModel;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.Interface.BuscadorUserInterface;

import java.util.ArrayList;

public class BuscadorUserPresenter implements BuscadorUserInterface.Presenter, BuscadorUserInterface.TaskListener{

    private Usuari currentUser;
    private BuscadorUserInterface.View view;
    private BuscadorUserInterface.Model model;
    private AdapterUsuarios adapterUsuarios;
    private ArrayList<Usuari> listaUsuarios;

    public BuscadorUserPresenter(BuscadorUserInterface.View view, Usuari currentUser) {
        this.view = view;
        this.currentUser = currentUser;
        model = new BuscadorUserModel(this);
        listaUsuarios = new ArrayList<>();
        adapterUsuarios = new AdapterUsuarios(this.currentUser, listaUsuarios);
    }

    @Override
    public void toGetUserList() {
        view.setAdapterList(adapterUsuarios);
        model.doGetUserList(this.currentUser);
    }

    @Override
    public void toSearchUser(String username) {
        ArrayList<Usuari> miLista = new ArrayList<>();
        for (Usuari u : listaUsuarios) {
            if (u.getUsername().toLowerCase().contains(username.toLowerCase())) {
                miLista.add(u);
            }
        }
        if (miLista.isEmpty()){
            view.onError("No se ha encontrado ningún usuario con ese nombre.");
        }
        AdapterUsuarios ad = new AdapterUsuarios(this.currentUser, miLista);
        view.setAdapterList(ad);
    }

    @Override
    public void addLista(Usuari u) {
        listaUsuarios.add(u);
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
