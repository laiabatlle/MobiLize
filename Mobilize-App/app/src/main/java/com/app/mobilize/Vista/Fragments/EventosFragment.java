package com.app.mobilize.Vista.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterEventos;
import com.app.mobilize.Presentador.EventsPresenter;
import com.app.mobilize.Presentador.Interface.EventsInterface;
import com.app.mobilize.R;
import com.app.mobilize.Vista.Activities.CreateEventActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment implements EventsInterface.View, View.OnClickListener {

    private Usuari user;
    private RecyclerView events;
    private ImageButton createEvent;
    private SearchView buscadorEventos;

    private EventsInterface.Presenter presenter;

    public EventosFragment() {
    }

    public EventosFragment(Usuari user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presenter = new EventsPresenter(this, user);

        events = (RecyclerView) view.findViewById(R.id.rv_events);
        events.addItemDecoration(new DividerItemDecoration(events.getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        events.setLayoutManager(lm);
        handleChargeEvents();

        buscadorEventos = view.findViewById(R.id.cearchEventsSV);
        buscadorEventos.onActionViewExpanded();
        buscadorEventos.setIconifiedByDefault(false);
        buscadorEventos.setQueryHint(getResources().getString(R.string.searchEvent));
        if(!buscadorEventos.isFocused()) {
            buscadorEventos.clearFocus();
        }
        buscadorEventos.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {
                handleSearchEvents(search);
                return true;
            }
        });

        createEvent = (ImageButton) view.findViewById(R.id.createEvent);
        createEvent.setOnClickListener(this);
    }

    @Override
    public void handleChargeEvents() {
        presenter.toGetEvents();
    }

    @Override
    public void handleSearchEvents(String search) {
        presenter.toSearchEvents(search);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void setAdapterEventsList(AdapterEventos adapterEventos) {
        events.setAdapter(adapterEventos);
    }

    private void gotoCreateEvent() {
        Intent intent = new Intent(getActivity(), CreateEventActivity.class);
        intent.putExtra("username", user.getUsername());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createEvent:
                gotoCreateEvent();
                break;

            default:
                break;
        }
    }
}
