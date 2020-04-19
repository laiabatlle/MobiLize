package com.app.mobilize.Presentador.Adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Events;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.viewholdereventos> {

    private List<Events> eventsList;
    private Usuari currentUser;
    private CollectionReference user_ref;
    private CollectionReference event_ref;

    public AdapterEventos(Usuari currentUser, List<Events> eventsList){
        this.currentUser = currentUser;
        this.eventsList = eventsList;
        user_ref = FirebaseFirestore.getInstance().collection("users");
        event_ref = FirebaseFirestore.getInstance().collection("Events");
    }

    @NonNull
    @Override
    public viewholdereventos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_eventos, parent,false);
        return new viewholdereventos(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholdereventos holder, int position) {
        final Events e = eventsList.get(position);
        Log.d("evento", e.getImage());
        Glide.with(holder.itemView).load(Uri.parse(e.getImage())).into(holder.event);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    static class viewholdereventos extends RecyclerView.ViewHolder {

        ImageView event;

        viewholdereventos(@NonNull View itemView) {
            super(itemView);

            this.event = itemView.findViewById(R.id.eventImage);
        }
    }
}

