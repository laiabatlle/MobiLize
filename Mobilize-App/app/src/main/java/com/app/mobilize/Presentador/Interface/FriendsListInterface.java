package com.app.mobilize.Presentador.Interface;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;

public interface FriendsListInterface {
    interface View {
        void handleChargeFriendList();

        void onError(String message);

        void setAdapterList(AdapterUsuarios adapterUsuarios);
    }

    interface Presenter{
        void toGetFriendList(String user);
    }

    interface Model{
        void doGetFriendList(String username);
    }

    interface TaskListener{
        void addLista(Usuari u);
        void onSuccess();
        void onError(String error);


    }
}
