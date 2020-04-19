package com.app.mobilize.Vista.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Events;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterEventos;
import com.app.mobilize.Presentador.EventsPresenter;
import com.app.mobilize.Presentador.FriendListPresenter;
import com.app.mobilize.Presentador.Interface.EventsInterface;
import com.app.mobilize.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment implements EventsInterface.View, View.OnClickListener {

    private Usuari user;
    private RecyclerView events;
    private ImageButton createEvent;

    private EventsInterface.Presenter presenter;


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

        createEvent = (ImageButton) view.findViewById(R.id.createEvent);
        createEvent.setOnClickListener(this);
    }

    @Override
    public void handleChargeEvents() {
        presenter.toGetEvents();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void setAdapterEventsList(AdapterEventos adapterEventos) {
        events.setAdapter(adapterEventos);
    }

    private void gotoCreateEvent() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new CreateEventFragment(user))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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
