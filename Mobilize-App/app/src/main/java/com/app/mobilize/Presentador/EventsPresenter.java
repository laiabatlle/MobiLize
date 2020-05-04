package com.app.mobilize.Presentador;

import android.content.Context;

import com.app.mobilize.Model.Events;
import com.app.mobilize.Model.EventsModel;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterEventos;
import com.app.mobilize.Presentador.Interface.EventsInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class EventsPresenter implements EventsInterface.Presenter, EventsInterface.TaskListener{

    private Usuari currentUser;
    private EventsInterface.View view;
    private EventsInterface.Model model;
    private AdapterEventos adapterEventos;
    private ArrayList<Events> eventsList;
    private Context mContext;

    public EventsPresenter(Context context, EventsInterface.View view, Usuari user) {
        this.mContext = context;
        this.view = view;
        this.currentUser = user;
        model = new EventsModel(this);
        eventsList = new ArrayList<>();
        adapterEventos = new AdapterEventos(mContext, this.currentUser, eventsList);
    }

    private void sortList() {
        Collections.sort(eventsList, new Comparator<Events>() {
            @Override
            public int compare(Events e1, Events e2) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date_e1 = formatter.parse(e1.getDateEvent());
                    Date date_e2 = formatter.parse(e2.getDateEvent());
                    return date_e1.compareTo(date_e2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }

    @Override
    public void toGetEvents() {
        view.setAdapterEventsList(adapterEventos);
        model.doGetEvents();
    }

    @Override
    public void toSearchEvents(String search) {
        ArrayList<Events> miLista = new ArrayList<>();
        for (Events e : eventsList) {
            if (e.getTitle().toLowerCase().contains(search) || e.getSportEvent().toLowerCase().contains(search)) {
                miLista.add(e);
            }
        }
        if (miLista.isEmpty()){
            view.onError("No se ha encontrado ningún evento con ese nombre.");
        }
        AdapterEventos ad = new AdapterEventos(mContext, this.currentUser, miLista);
        view.setAdapterEventsList(ad);
    }

    @Override
    public void addEventsList(Events e) {
        eventsList.add(e);
        sortList();
        AdapterEventos ad = new AdapterEventos(mContext, this.currentUser, eventsList);
        view.setAdapterEventsList(ad);
    }

    @Override
    public void onSuccess() {
        sortList();
        AdapterEventos ad = new AdapterEventos(mContext, this.currentUser, eventsList);
        view.setAdapterEventsList(ad);
    }

    @Override
    public void onError(String error) {
        if(view!=null){
            view.onError(error);
        }
    }
}
