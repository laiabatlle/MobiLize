package com.app.mobilize.Vista.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private RecyclerView reqFriends, listaFriends;
    private LinearLayout req, friends;
    private LinearLayoutManager lm1, lm2;
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

        req = (LinearLayout) view.findViewById(R.id.ReqListLL);
        friends = (LinearLayout) view.findViewById(R.id.friendsListLL);

        reqFriends = (RecyclerView) view.findViewById(R.id.rv1);
        listaFriends = (RecyclerView) view.findViewById(R.id.rv2);
        lm1 = new LinearLayoutManager(getContext());
        lm2 = new LinearLayoutManager(getContext());
        reqFriends.setLayoutManager(lm1);
        listaFriends.setLayoutManager(lm2);

        handleChargeFriendReq();
        handleChargeFriendList();
    }

    @Override
    public void handleChargeFriendReq() {
        presenter.toGetFriendReq(user.getUsername());
    }

    @Override
    public void handleChargeFriendList() {
        presenter.toGetFriendList(user.getUsername());
    }

    //TODO arreglar això perque quan no hi hagi req, no aparegui el layout ni la recycleList
    @Override
    public void onError(String message) {
        if (message.equals("¡Encuentra nuevos amigos!")){
            friends.setVisibility(View.INVISIBLE);
            listaFriends.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
        if (message.equals("No tienes solicitudes")){
            req.setVisibility(View.INVISIBLE);
            reqFriends.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setAdapterReqList(AdapterUsuarios adapterUsuarios) {
        reqFriends.setAdapter(adapterUsuarios);
    }

    @Override
    public void setAdapterFriendList(AdapterUsuarios adapterUsuarios) {
        listaFriends.setAdapter(adapterUsuarios);
    }
}
