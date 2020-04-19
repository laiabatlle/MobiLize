package com.app.mobilize.Presentador;

import com.app.mobilize.Model.Events;
import com.app.mobilize.Model.EventsModel;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterEventos;
import com.app.mobilize.Presentador.Interface.EventsInterface;

import java.util.ArrayList;

public class EventsPresenter implements EventsInterface.Presenter, EventsInterface.TaskListener{

    private Usuari currentUser;
    private EventsInterface.View view;
    private EventsInterface.Model model;
    private AdapterEventos adapterEventos;
    private ArrayList<Events> eventsList;

    public EventsPresenter(EventsInterface.View view, Usuari user) {
        this.view = view;
        this.currentUser = user;
        model = new EventsModel(this);
        eventsList = new ArrayList<>();
        adapterEventos = new AdapterEventos(this.currentUser, eventsList);
    }

    @Override
    public void toGetEvents() {
        view.setAdapterEventsList(adapterEventos);
        model.doGetEvents();
    }

    @Override
    public void addEventsList(Events e) {
        eventsList.add(e);
        AdapterEventos ad = new AdapterEventos(this.currentUser, eventsList);
        view.setAdapterEventsList(ad);
    }

    @Override
    public void onSuccess() {
        AdapterEventos ad = new AdapterEventos(this.currentUser, eventsList);
        view.setAdapterEventsList(ad);
    }

    @Override
    public void onError(String error) {
        if(view!=null){
            view.onError(error);
        }
    }
}
