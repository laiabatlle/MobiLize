package com.app.mobilize.Presentador.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Events;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.R;
import com.app.mobilize.Vista.Activities.PopUpEventListInscriptions;
import com.app.mobilize.Vista.Activities.QuestionDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.viewholdereventos> {

    private List<Events> eventsList;
    private Usuari currentUser;
    private CollectionReference user_ref;
    private CollectionReference event_ref;
    private Context mContext;

    public AdapterEventos(@NonNull Context context, Usuari currentUser, List<Events> eventsList){
        this.mContext = context;
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
        holder.title1.setText(e.getTitle());
        Glide.with(holder.itemView).load(Uri.parse(e.getImage())).into(holder.event);
        MaintanceofInformationEvent(holder, currentUser.getEmail(), e);
        holder.title2.setText(e.getTitle());
        holder.description.setText(e.getDescription());
        holder.sport.setText(e.getSportEvent());
        holder.date.setText(e.getDateEvent());
        holder.hour.setText(e.getHourEvent());
        int romandingSeats =  Integer.parseInt(e.getMax_part()) - e.getInscripcionsList().size();
        holder.romanding.setText(Integer.toString(romandingSeats));
        holder.title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShowInfo(holder);
            }
        });
        holder.event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShowInfo(holder);
            }
        });
        holder.titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleHideInfo(holder);
            }
        });
        holder.inscriptionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShowInscriptions(e);
            }
        });
        holder.actionButtomEventModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleModify(e, currentUser.getEmail());
            }
        });
        holder.actionButtomEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.CURRENT_STATE.equals("creator")){
                    handleDelete(e, currentUser.getEmail());
                }
                else if (holder.CURRENT_STATE.equals("subscribed")){
                    handleToUnsubscribe(holder, currentUser.getEmail(), e);
                    Toast.makeText(v.getContext(), "Te has desapuntado del evento  " + e.getTitle(), Toast.LENGTH_SHORT).show();
                }
                else{
                    handleToSubscribe(holder, currentUser.getEmail(), e);
                    Toast.makeText(v.getContext(), "Te has inscrito al evento  " + e.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        handleHideInfo(holder);
    }

    private void MaintanceofInformationEvent(final viewholdereventos holder, final String user, Events e) {
        event_ref.whereEqualTo("title", e.getTitle()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (user.equals(task.getResult().getDocuments().get(0).getData().get("creator").toString())) {
                        holder.actionButtomEvent.setImageResource(R.mipmap.ic_delete);
                        holder.CURRENT_STATE = "creator";
                    }else {
                        List<String> inscriptions = (List<String>) task.getResult().getDocuments().get(0).getData().get("inscripcionsList");
                        if (inscriptions.contains(user)) {
                            holder.actionButtomEvent.setImageResource(R.mipmap.ic_unsubscrive_event);
                            holder.CURRENT_STATE = "subscribed";
                        } else {
                            holder.actionButtomEvent.setImageResource(R.mipmap.ic_subscrive_event);
                            holder.CURRENT_STATE = "not_subscribed";
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    private void handleShowInfo(viewholdereventos holder) {
        holder.title1.setEnabled(false);
        holder.title1.setVisibility(View.INVISIBLE);
        holder.event.setEnabled(false);
        holder.event.setVisibility(View.INVISIBLE);

        holder.titleLayout.setEnabled(true);
        holder.titleLayout.setBackgroundResource(R.drawable.titleevent_bg_rounded_courners);
        holder.title2.setEnabled(true);
        holder.title2.setVisibility(View.VISIBLE);
        if (holder.CURRENT_STATE.equals("creator")){
            holder.actionButtomEventModify.setEnabled(true);
            holder.actionButtomEventModify.setVisibility(View.VISIBLE);
        }
        holder.actionButtomEvent.setEnabled(true);
        holder.actionButtomEvent.setVisibility(View.VISIBLE);
        holder.descriptionTV.setEnabled(true);
        holder.descriptionTV.setVisibility(View.VISIBLE);
        holder.description.setEnabled(true);
        holder.description.setVisibility(View.VISIBLE);
        holder.sportTV.setEnabled(true);
        holder.sportTV.setVisibility(View.VISIBLE);
        holder.sport.setEnabled(true);
        holder.sport.setVisibility(View.VISIBLE);
        holder.dateTV.setEnabled(true);
        holder.dateTV.setVisibility(View.VISIBLE);
        holder.date.setEnabled(true);
        holder.date.setVisibility(View.VISIBLE);
        holder.hourTV.setEnabled(true);
        holder.hourTV.setVisibility(View.VISIBLE);
        holder.hour.setEnabled(true);
        holder.hour.setVisibility(View.VISIBLE);
        holder.romandingTV.setEnabled(true);
        holder.romandingTV.setVisibility(View.VISIBLE);
        holder.romanding.setEnabled(true);
        holder.romanding.setVisibility(View.VISIBLE);
        holder.inscriptionListTV.setEnabled(true);
        holder.inscriptionListTV.setVisibility(View.VISIBLE);
        holder.inscriptionList.setEnabled(true);
        holder.inscriptionList.setVisibility(View.VISIBLE);
    }

    private void handleHideInfo(viewholdereventos holder) {
        holder.titleLayout.setEnabled(false);
        holder.titleLayout.setBackground(null);
        holder.title2.setEnabled(false);
        holder.title2.setVisibility(View.INVISIBLE);
        holder.actionButtomEventModify.setEnabled(false);
        holder.actionButtomEventModify.setVisibility(View.INVISIBLE);
        holder.actionButtomEvent.setEnabled(false);
        holder.actionButtomEvent.setVisibility(View.INVISIBLE);
        holder.descriptionTV.setEnabled(false);
        holder.descriptionTV.setVisibility(View.INVISIBLE);
        holder.description.setEnabled(false);
        holder.description.setVisibility(View.INVISIBLE);
        holder.sportTV.setEnabled(false);
        holder.sportTV.setVisibility(View.INVISIBLE);
        holder.sport.setEnabled(false);
        holder.sport.setVisibility(View.INVISIBLE);
        holder.dateTV.setEnabled(false);
        holder.dateTV.setVisibility(View.INVISIBLE);
        holder.date.setEnabled(false);
        holder.date.setVisibility(View.INVISIBLE);
        holder.hourTV.setEnabled(false);
        holder.hourTV.setVisibility(View.INVISIBLE);
        holder.hour.setEnabled(false);
        holder.hour.setVisibility(View.INVISIBLE);
        holder.romandingTV.setEnabled(false);
        holder.romandingTV.setVisibility(View.INVISIBLE);
        holder.romanding.setEnabled(false);
        holder.romanding.setVisibility(View.INVISIBLE);
        holder.inscriptionListTV.setEnabled(false);
        holder.inscriptionListTV.setVisibility(View.INVISIBLE);
        holder.inscriptionList.setEnabled(false);
        holder.inscriptionList.setVisibility(View.INVISIBLE);

        holder.title1.setEnabled(true);
        holder.title1.setVisibility(View.VISIBLE);
        holder.event.setEnabled(true);
        holder.event.setVisibility(View.VISIBLE);
    }

    private void handleShowInscriptions(Events e) {
        Intent intent = new Intent(mContext, PopUpEventListInscriptions.class);
        intent.putExtra("title", e.getTitle());
        mContext.startActivity(intent);
    }

    private void handleModify(final Events e, final String current_user) {
        Intent intent = new Intent(mContext, QuestionDialog.class);
        intent.putExtra("type", "modify");
        intent.putExtra("current_user", current_user);
        intent.putExtra("title", e.getTitle());
        intent.putExtra("descrption", e.getDescription());
        intent.putExtra("image", e.getImage());
        intent.putExtra("sport", e.getSportEvent());
        intent.putExtra("date", e.getDateEvent());
        intent.putExtra("hour", e.getHourEvent());
        intent.putExtra("max_part", e.getMax_part());
        intent.putStringArrayListExtra("inscriptions", (ArrayList<String>) e.getInscripcionsList());
        mContext.startActivity(intent);
    }

    private void handleDelete(final Events e, final String current_user) {
        Intent intent = new Intent(mContext, QuestionDialog.class);
        intent.putExtra("type", "delete");
        intent.putExtra("current_user", current_user);
        intent.putExtra("title", e.getTitle());
        intent.putExtra("descrption", e.getDescription());
        intent.putExtra("image", e.getImage());
        intent.putExtra("sport", e.getSportEvent());
        intent.putExtra("date", e.getDateEvent());
        intent.putExtra("hour", e.getHourEvent());
        intent.putExtra("max_part", e.getMax_part());
        intent.putStringArrayListExtra("inscriptions", (ArrayList<String>) e.getInscripcionsList());
        mContext.startActivity(intent);

        final AlertDialog.Builder editAD = new AlertDialog.Builder(mContext);
        TextView textView = new TextView(mContext);
        editAD.setIcon(R.mipmap.ic_delete);
        editAD.setTitle(mContext.getResources().getString(R.string.EliminarEsdeveniment));
        editAD.setView(textView);
    }

    private void handleToSubscribe(final viewholdereventos holder, final String user, Events e) {
        holder.actionButtomEvent.setImageResource(R.mipmap.ic_unsubscrive_event);
        event_ref.document(e.getTitle()).update("inscripcionsList", FieldValue.arrayUnion(user));
        holder.CURRENT_STATE = "subscribed";
    }

    private void handleToUnsubscribe(viewholdereventos holder, String user, Events e) {
        holder.actionButtomEvent.setImageResource(R.mipmap.ic_subscrive_event);
        event_ref.document(e.getTitle()).update("inscripcionsList", FieldValue.arrayRemove(user));
        holder.CURRENT_STATE = "not_subscribed";
    }

    static class viewholdereventos extends RecyclerView.ViewHolder {

        ConstraintLayout titleLayout;
        ImageView event;
        TextView title1, title2, descriptionTV, description, sportTV, sport, date, hour, romandingTV, romanding, inscriptionListTV;
        ImageButton dateTV, hourTV, inscriptionList, actionButtomEventModify, actionButtomEvent;

        String CURRENT_STATE;

        viewholdereventos(@NonNull View itemView) {
            super(itemView);

            this.event = (ImageView) itemView.findViewById(R.id.eventImage);
            this.title1 = (TextView) itemView.findViewById(R.id.eventTitle);

            this.titleLayout = itemView.findViewById(R.id.titleInfoEventLayout);
            this.title2 = (TextView) itemView.findViewById(R.id.eventInfoTitle);
            this.descriptionTV = (TextView) itemView.findViewById(R.id.descriptionInfoEventTV);
            this.description = (TextView) itemView.findViewById(R.id.descriptionInfoEvent);
            this.sportTV = (TextView) itemView.findViewById(R.id.sportInfoEventTV);
            this.sport = (TextView) itemView.findViewById(R.id.sportInfoEvent);
            this.dateTV = (ImageButton) itemView.findViewById(R.id.dateInfoEventCalendar);
            this.date = (TextView) itemView.findViewById(R.id.dateInfoEvent);
            this.hourTV = (ImageButton) itemView.findViewById(R.id.hourInfoEventClock);
            this.hour = (TextView) itemView.findViewById(R.id.hourInfoEvent);
            this.romandingTV = (TextView) itemView.findViewById(R.id.plazasLibresInfoEventTV);
            this.romanding = (TextView) itemView.findViewById(R.id.plazasLibresInfoEvent);
            this.inscriptionListTV = (TextView) itemView.findViewById(R.id.inscritosInfoEventTV);
            this.inscriptionList = (ImageButton) itemView.findViewById(R.id.ConsultarInscritosButton);
            this.actionButtomEvent = (ImageButton) itemView.findViewById(R.id.actionButtomEvent);
            this.actionButtomEventModify = (ImageButton) itemView.findViewById(R.id.actionButtomEventModify);

            this.CURRENT_STATE = "not_subscribed";
        }
    }
}

