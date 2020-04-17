package com.app.mobilize.Presentador.Interface;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;

public interface BuscadorUserInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void handleChargeUserList();
        void handleSearchUser(String username);

        void setBuscador();

        void onError(String message);

        void setAdapterList(AdapterUsuarios adapterUsuarios);
    }

    interface Presenter{
        void toGetUserList();
        void toSearchUser(String username);
    }

    interface Model{
        void doGetUserList(Usuari currentUser);
    }

    interface TaskListener{
        void addLista(Usuari u);
        void onSuccess();
        void onError(String error);
    }
}
