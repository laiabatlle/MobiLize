package com.app.mobilize.Presentador.Adapter;

import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Messages;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Messages> userMessagesList;
    private String currentUser, user, username, profileUserImage;
    private CollectionReference chat_ref;

    public MessagesAdapter(List<Messages> userMessagesList, String currentUser, String user, String username, String avatar){
        this.userMessagesList = userMessagesList;
        this.currentUser = currentUser;
        this.chat_ref = FirebaseFirestore.getInstance().collection("Chat");
        this.user = user;
        this.username = username;
        this.profileUserImage = avatar;

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout_of_user, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Messages message = userMessagesList.get(position);
        String fromMessageSenderId = message.getFrom();
        Glide.with(holder.itemView).load(Uri.parse(profileUserImage)).into(holder.receiverProfileImage);

        holder.clreceiver.setVisibility(View.INVISIBLE);
        holder.receiverMessageText.setVisibility(View.INVISIBLE);
        holder.receiverProfileImage.setVisibility(View.INVISIBLE);
        holder.receiverMessageHour.setVisibility(View.INVISIBLE);

        if(fromMessageSenderId.equals(currentUser)){
            holder.senderMessageText.setBackgroundResource(R.drawable.sender_message_text_background);
            holder.senderMessageText.setTextColor(Color.BLACK);
            holder.senderMessageText.setGravity(Gravity.LEFT);
            holder.senderMessageText.setText(message.getMessage());
            holder.senderMessageHour.setText(message.getTime());
        }else{
            holder.cLsender.setVisibility(View.INVISIBLE);
            holder.senderMessageText.setVisibility(View.INVISIBLE);
            holder.senderMessageHour.setVisibility(View.INVISIBLE);
            holder.clreceiver.setVisibility(View.VISIBLE);
            holder.receiverMessageText.setVisibility(View.VISIBLE);
            holder.receiverProfileImage.setVisibility(View.VISIBLE);
            holder.receiverMessageHour.setVisibility(View.VISIBLE);

            holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_message_text_background);
            holder.receiverMessageText.setTextColor(Color.BLACK);
            holder.receiverMessageText.setGravity(Gravity.LEFT);
            holder.receiverMessageText.setText(message.getMessage());
            holder.receiverMessageHour.setText(message.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout cLsender, clreceiver;
        public TextView senderMessageText, receiverMessageText, senderMessageHour, receiverMessageHour;
        public CircleImageView receiverProfileImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            cLsender = (ConstraintLayout) itemView.findViewById(R.id.sender_message);
            clreceiver = (ConstraintLayout) itemView.findViewById(R.id.receiver_message);
            senderMessageText = (TextView) itemView.findViewById(R.id.sender_message_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            senderMessageHour = (TextView) itemView.findViewById(R.id.sender_message_Hour);
            receiverMessageHour = (TextView) itemView.findViewById(R.id.receiver_message_Hour);
            receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);

        }
    }
}
