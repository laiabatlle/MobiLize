package com.app.mobilize.Presentador.Adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.viewholderusuarios> {

    private List<Usuari> userList;
    private Usuari currentUser;
    private CollectionReference user_ref;
    private CollectionReference req_ref;

    public AdapterUsuarios(Usuari currentUser, List<Usuari> userList){
        this.currentUser = currentUser;
        this.userList = userList;
        user_ref = FirebaseFirestore.getInstance().collection("users");
        req_ref = FirebaseFirestore.getInstance().collection("FriendRequests");
    }

    @NonNull
    @Override
    public viewholderusuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios, parent,false);
        return new viewholderusuarios(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholderusuarios holder, int position) {
        final Usuari us = userList.get(position);
        holder.username.setText(us.getUsername());
        Glide.with(holder.itemView).load(Uri.parse(us.getImage())).into(holder.avatar);

        MaintanceofButtons(holder, currentUser.getUsername(), us.getUsername());

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.action.setEnabled(false);

                if(holder.CURRENT_STATE.equals("not_friends")){
                    SendFriendRequest(v, holder, currentUser.getUsername(), us.getUsername());
                }

                if(holder.CURRENT_STATE.equals("request_sent")){
                    CancelFriendRequest(v, holder, currentUser.getUsername(), us.getUsername());
                }

                if(holder.CURRENT_STATE.equals("request_received")) {
                    AcceptFriendRequest(v, holder, currentUser.getUsername(), us.getUsername());
                    user_ref.document(currentUser.getUsername()).update("friendsList", FieldValue.arrayUnion(us.getUsername()));
                    user_ref.document(us.getUsername()).update("friendsList", FieldValue.arrayUnion(currentUser.getUsername()));
                }

                if(holder.CURRENT_STATE.equals("friends")) {
                    DeleteFriendRequest(v, holder, currentUser.getUsername(), us.getUsername());
                    user_ref.document(currentUser.getUsername()).update("friendsList", FieldValue.arrayRemove(us.getUsername()));
                    user_ref.document(us.getUsername()).update("friendsList", FieldValue.arrayRemove(currentUser.getUsername()));
                }
            }
        });
    }

    private void MaintanceofButtons(final viewholderusuarios holder, String senderUser, final String receiverUser) {
        req_ref.document(senderUser).collection("request").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getData().get("user").toString().equals(receiverUser)) {
                            String request_type = document.getData().get("request_type").toString();

                            if (request_type.equals("sent")) {
                                holder.action.setEnabled(false);
                                holder.CURRENT_STATE = "request_sent";
                                holder.action.setImageResource(R.drawable.ic_cancel_req);
                                holder.action.setVisibility(View.VISIBLE);
                                holder.action.setEnabled(true);
                            }

                            else if (request_type.equals("received")) {
                                holder.action.setEnabled(false);
                                holder.CURRENT_STATE = "request_received";
                                holder.action.setImageResource(R.drawable.ic_accept);
                                holder.action.setVisibility(View.VISIBLE);
                                holder.action.setEnabled(true);
                            }
                        }
                    }
                }
            }
        });

        user_ref.whereEqualTo("username", currentUser.getUsername()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> friendList = (List<String>) task.getResult().getDocuments().get(0).getData().get("friendsList");
                    if (friendList.contains(receiverUser)) {
                        holder.action.setEnabled(false);
                        holder.CURRENT_STATE = "friends";
                        holder.action.setImageResource(R.drawable.ic_unfriend);
                        holder.action.setVisibility(View.VISIBLE);
                        holder.action.setEnabled(true);
                        Log.d("title", friendList.get(0));
                    }
                }
            }
        });
        holder.action.setVisibility(View.VISIBLE);
    }

    private void SendFriendRequest(final View v, @NonNull final viewholderusuarios holder, final String senderUser, final String receiverUser) {
        Map<String, Object> request = new HashMap<>();
        request.put("user", receiverUser);
        request.put("request_type", "sent");
        req_ref.document(senderUser).collection("request").document(receiverUser).set(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Map<String, Object> request = new HashMap<>();
                    request.put("user", senderUser);
                    request.put("request_type", "received");
                    req_ref.document(receiverUser).collection("request").document(senderUser).set(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                holder.action.setEnabled(false);
                                holder.CURRENT_STATE = "request_sent";
                                holder.action.setImageResource(R.drawable.ic_cancel_req);
                                holder.action.setEnabled(true);
                            }
                            Toast.makeText(v.getContext(), "Se ha enviado una solicitud de amistad.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void CancelFriendRequest(final View v, final viewholderusuarios holder, final String senderUser, final String receiverUser) {
        req_ref.document(senderUser).collection("request").document(receiverUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    req_ref.document(receiverUser).collection("request").document(senderUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                holder.action.setEnabled(false);
                                holder.CURRENT_STATE = "not_friends";
                                holder.action.setImageResource(R.drawable.ic_add_friend);
                                holder.action.setEnabled(true);
                            }
                            Toast.makeText(v.getContext(), "Se ha cancelado su solicitud.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void AcceptFriendRequest(final View v, final viewholderusuarios holder, final String senderUser, final String receiverUser) {
        req_ref.document(senderUser).collection("request").document(receiverUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    req_ref.document(receiverUser).collection("request").document(senderUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                holder.action.setEnabled(false);
                                holder.CURRENT_STATE = "friends";
                                holder.action.setImageResource(R.drawable.ic_unfriend);
                                holder.action.setEnabled(true);
                            }
                            Toast.makeText(v.getContext(), senderUser + " y tu ahora soys amigos.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void DeleteFriendRequest(View v, viewholderusuarios holder, String senderUser, String receiverUser) {
        holder.action.setEnabled(false);
        holder.CURRENT_STATE = "not_friends";
        holder.action.setImageResource(R.drawable.ic_add_friend);
        holder.action.setEnabled(true);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class viewholderusuarios extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView username;
        ImageButton action;

        String CURRENT_STATE;

        viewholderusuarios(@NonNull View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.busquedaUserTV);
            avatar = (ImageView) itemView.findViewById(R.id.AvatarIV_Cercador);
            action = (ImageButton) itemView.findViewById(R.id.actionRequestButton);

            action.setVisibility(View.INVISIBLE);

            CURRENT_STATE = "not_friends";

        }
    }
}
