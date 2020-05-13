package com.app.mobilize.Presentador.Interface;

import com.app.mobilize.Model.Messages;
import com.app.mobilize.Presentador.Adapter.MessagesAdapter;

public interface ChatInterface {
    interface View {
        void handleChargeMessages();
        void handleSendMessage();

        void onError(String message);

        void setAdapterList(MessagesAdapter adapterMessages);
    }

    interface Presenter{
        void toGetMessageList();
        void toSendMessage(String date, String time, String from, String message_text);
    }

    interface Model{
        void doGetMessageList(String currentUser, String user);
        void doSendMessage(String currentUser, String user, String date, String time, String message_text);
    }

    interface TaskListener{
        void addLista(Messages m);
        void onSuccess();
        void onError(String error);
    }
}
