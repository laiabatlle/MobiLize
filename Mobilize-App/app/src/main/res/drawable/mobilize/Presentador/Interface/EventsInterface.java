package com.app.mobilize.Presentador.Interface;

import com.app.mobilize.Model.Events;
import com.app.mobilize.Presentador.Adapter.AdapterEventos;

public interface EventsInterface {
    interface View {
        void handleChargeEvents();
        void handleSearchEvents(String search);

        void onError(String message);

        void setAdapterEventsList(AdapterEventos adapterEventos);
    }

    interface Presenter{
        void toGetEvents();
        void toSearchEvents(String search);
    }

    interface Model{
        void doGetEvents();
    }

    interface TaskListener{
        void addEventsList(Events e);

        void onSuccess();
        void onError(String error);
    }
}
