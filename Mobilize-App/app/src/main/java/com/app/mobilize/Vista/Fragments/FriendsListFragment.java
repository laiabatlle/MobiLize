package com.app.mobilize.Vista.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.FriendListPresenter;
import com.app.mobilize.Presentador.Interface.FriendsListInterface;
import com.app.mobilize.R;

public class FriendsListFragment extends Fragment implements FriendsListInterface.View{

    private Usuari user;
    private RecyclerView lista;
    private LinearLayoutManager lm;
    private FriendsListInterface.Presenter presenter;

    public FriendsListFragment(Usuari user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.friendslist_fragment, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presenter = new FriendListPresenter(this, user);

        lista = (RecyclerView) view.findViewById(R.id.rv1);
        lm = new LinearLayoutManager(getContext());
        lista.setLayoutManager(lm);

        handleChargeFriendList();
    }

    @Override
    public void handleChargeFriendList() {
        presenter.toGetFriendList(user.getUsername());
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
