package com.app.mobilize.Vista.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.FriendListPresenter;
import com.app.mobilize.Presentador.Interface.FriendsListInterface;
import com.app.mobilize.R;

public class FriendsListFragment extends Fragment implements FriendsListInterface.View{

    private Usuari user;
    private TextView noFriends;
    private RecyclerView reqFriends, listaFriends;
    private ImageView imageView;
    private String iconoFriends;

    private FriendsListInterface.Presenter presenter;

    public FriendsListFragment(Usuari user, String iconoFriends) {
        this.user = user;
        this.iconoFriends = iconoFriends;
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
        presenter = new FriendListPresenter(getContext(), this, user);

        imageView = (ImageView) view.findViewById(R.id.iconoFriends);
        if (iconoFriends.equals("req")) imageView.setImageResource(R.mipmap.ic_reqfriend);
        else imageView.setImageResource(R.mipmap.ic_friendslist);

        noFriends = (TextView) view.findViewById(R.id.NoFriendText);

        reqFriends = (RecyclerView) view.findViewById(R.id.rv_req);
        listaFriends = (RecyclerView) view.findViewById(R.id.rv_friends);
        listaFriends.addItemDecoration(new DividerItemDecoration(listaFriends.getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager lm1 = new LinearLayoutManager(getContext());
        LinearLayoutManager lm2 = new LinearLayoutManager(getContext());
        reqFriends.setLayoutManager(lm1);
        listaFriends.setLayoutManager(lm2);

        handleChargeFriendReq();
        handleChargeFriendList();
    }

    @Override
    public void handleChargeFriendReq() {
        presenter.toGetFriendReq(user.getEmail());
    }

    @Override
    public void handleChargeFriendList() {
        presenter.toGetFriendList(user.getEmail());
    }

    @Override
    public void onError(String message) {
        if (message.equals(message)){
            listaFriends.setVisibility(View.INVISIBLE);
            noFriends.setVisibility(View.VISIBLE);

        }
        if (message.equals("No tienes solicitudes")){
            reqFriends.setVisibility(View.INVISIBLE);
            noFriends.setVisibility(View.VISIBLE);
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
