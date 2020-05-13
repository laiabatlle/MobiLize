package com.app.mobilize.Presentador.Interface;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;

public interface FriendsListInterface {
    interface View {

        void handleChargeFriendReq();
        void handleChargeFriendList();

        void onError(String message);

        void setAdapterReqList(AdapterUsuarios adapterUsuarios);
        void setAdapterFriendList(AdapterUsuarios adapterUsuarios);
    }

    interface Presenter{
        void toGetFriendReq(String user);
        void toGetFriendList(String user);
    }

    interface Model{
        void doGetFriendReq(String user);
        void doGetFriendList(String username);
    }

    interface TaskListener{
        void addListaReq(Usuari u);
        void addListaFriends(Usuari u);
        void onSuccess(String type);
        void onError(String error);


    }
}
