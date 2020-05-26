package com.app.mobilize.Presentador.Interface;

public interface UserInterface {
    interface View {
        void handleActionReq(String userperfil, String CURRENT_STATE);
        void handleDeclineReq(String userperfil, String CURRENT_STATE);
        void MaintanceofButtons(String currentUser, String userperfil, String current_state);

        void onSuccess(String username, String message);

        void setActionButton(String buttonText);

        void setCurrentState(String current_state);

        void setDeclinenButton(boolean b);
    }

    interface Presenter{
        void toActionReq(String currentUser, String userperfil, String CURRENT_STATE);
        void toDeclineReq(String currentUser, String userperfil, String CURRENT_STATE);

        void MaintanceofButtons(String currentUser, String userperfil, String current_state);
    }

    interface Model{
        void doAcceptReq(String currentUser, String userperfil, String CURRENT_STATE);
        void doSendReq(String currentUser, String userperfil, String CURRENT_STATE);
        void doCancelReq(String currentUser, String userperfil, String CURRENT_STATE);
        void doUnfriend(String currentUser, String userperfil, String CURRENT_STATE);

        void MaintanceofButtons(String currentUser, String userperfil, String current_state);
    }

    interface TaskListener{
        void onSuccess(String current_state, String username, String message);
        void onError(String error);

        void MaintanceofButtons(String current_state);
    }
}
