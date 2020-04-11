package com.app.mobilize.Vista.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.BuscadorUserPresenter;
import com.app.mobilize.Presentador.Interface.BuscadorUserInterface;
import com.app.mobilize.R;

public class ListaUsersFragment extends Fragment implements BuscadorUserInterface.View {

    private Usuari user;
    private String username;
    private RecyclerView lista;
    private SearchView buscador;
    private LinearLayoutManager lm;
    private BuscadorUserInterface.Presenter presenter;

    public ListaUsersFragment( Usuari user, String busqueda) {
        this.user = user;
        this.username = busqueda;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listausers_fragment, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presenter = new BuscadorUserPresenter(this, user);

        lista = (RecyclerView) view.findViewById(R.id.rv);
        buscador = (SearchView) view.findViewById(R.id.searchView);
        lm = new LinearLayoutManager(getContext());
        lista.setLayoutManager(lm);

        handleChargeUserList();

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                handleSearchUser(s);
                return true;
            }
        });
    }

    private void setInputs(boolean enable){
        buscador.setEnabled(enable);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void handleChargeUserList() {
        presenter.toGetUserList();
    }

    @Override
    public void handleSearchUser(String s) {
        presenter.toSearchUser(s);
    }

    @Override
    public void setBuscador() {
        buscador.setQuery(username,true);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAdapterList(AdapterUsuarios adapterUsuarios) {
        lista.setAdapter(adapterUsuarios);
    }
}
