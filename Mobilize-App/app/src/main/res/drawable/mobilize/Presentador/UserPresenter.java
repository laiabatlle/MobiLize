package com.app.mobilize.Presentador;


import com.app.mobilize.Model.UserModel;
import com.app.mobilize.Presentador.Interface.UserInterface;

public class UserPresenter implements UserInterface.Presenter, UserInterface.TaskListener {

    private UserInterface.View view;
    private UserInterface.Model model;

    public UserPresenter(UserInterface.View view) {
        this.view = view;
        model = new UserModel(this);
    }

    @Override
    public void toActionReq(String currentUser, String userperfil, String CURRENT_STATE) {
        if(CURRENT_STATE.equals("not_friends")){
            model.doSendReq(currentUser, userperfil, CURRENT_STATE);
        }

        if(CURRENT_STATE.equals("request_sent")){
            model.doCancelReq(currentUser, userperfil, CURRENT_STATE);
        }

        if(CURRENT_STATE.equals("request_received")) {
            model.doAcceptReq(currentUser, userperfil, CURRENT_STATE);
        }

        if(CURRENT_STATE.equals("friends")) {
            model.doUnfriend(currentUser, userperfil, CURRENT_STATE);
        }
    }

    @Override
    public void toDeclineReq(String currentUser, String userperfil, String CURRENT_STATE) {
        model.doCancelReq(currentUser, userperfil, CURRENT_STATE);
        view.setDeclinenButton(false);
    }

    @Override
    public void MaintanceofButtons(String currentUser, String userperfil, String current_state) {
        model.MaintanceofButtons(currentUser, userperfil, current_state);
    }

    @Override
    public void onSuccess(String CURRENT_STATE, String username, String message) {
        if(view!=null){
            if (CURRENT_STATE.equals("request_sent")){
                view.setActionButton("CancelFriendReq");
            }
            if (CURRENT_STATE.equals("not_friends")){
                view.setActionButton("SendFriendReq");
            }
            if (CURRENT_STATE.equals("friends")){
                view.setActionButton("Unfriend");
            }
            view.setCurrentState(CURRENT_STATE);
            view.onSuccess(username, message);
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void MaintanceofButtons(String CURRENT_STATE) {
        if(view!=null){
            if (CURRENT_STATE.equals("request_sent")){
                view.setActionButton("CancelFriendReq");
                view.setDeclinenButton(false);
            }
            if (CURRENT_STATE.equals("not_friends")){
                view.setActionButton("SendFriendReq");
                view.setDeclinenButton(false);
            }
            if (CURRENT_STATE.equals("friends")){
                view.setActionButton("Unfriend");
                view.setDeclinenButton(false);
            }
            if (CURRENT_STATE.equals("request_received")){
                view.setActionButton("AcceptFriendReq");
                view.setDeclinenButton(true);
            }
            view.setCurrentState(CURRENT_STATE);
        }
    }
}
