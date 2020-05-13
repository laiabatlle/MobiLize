package com.app.mobilize.Presentador;

import android.content.Context;

import com.app.mobilize.Model.ChatModel;
import com.app.mobilize.Model.Messages;
import com.app.mobilize.Presentador.Adapter.MessagesAdapter;
import com.app.mobilize.Presentador.Interface.ChatInterface;

import java.util.ArrayList;

public class ChatPresenter implements ChatInterface.Presenter, ChatInterface.TaskListener {

    private Context mContext;
    private ChatInterface.View view;
    private ChatInterface.Model model;
    private String currentUser, user, username, avatar;
    private ArrayList<Messages> messagesList;


    public ChatPresenter(Context context, ChatInterface.View view, String currentUser, String user, String username, String image) {
        this.mContext = context;
        this.view = view;
        this.currentUser = currentUser;
        this.user = user;
        this.username = username;
        this.avatar = image;
        model = new ChatModel(this);
        messagesList = new ArrayList<>();
    }

    @Override
    public void toGetMessageList() {
        messagesList = new ArrayList<>();
        model.doGetMessageList(currentUser, user);
    }

    @Override
    public void toSendMessage(String date, String time, String from, String message_text) {
        model.doSendMessage(currentUser, user, date, time, message_text);
    }

    @Override
    public void addLista(Messages m) {
        messagesList.add(m);
        MessagesAdapter ad = new MessagesAdapter(messagesList, currentUser, user, username, avatar);
        view.setAdapterList(ad);
    }

    @Override
    public void onSuccess() {
        MessagesAdapter ad = new MessagesAdapter(messagesList, currentUser, user, username, avatar);
        view.setAdapterList(ad);
    }

    @Override
    public void onError(String error) {
        if(view!=null){
            view.onError(error);
        }
    }
}
