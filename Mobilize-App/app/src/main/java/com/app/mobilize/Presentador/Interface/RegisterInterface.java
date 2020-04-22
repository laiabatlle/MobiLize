package com.app.mobilize.Presentador.Interface;

public interface RegisterInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void showPrgress();
        void hideProgress();

        void handleRegister();
        boolean isValidUsername();
        boolean isValidEmail();
        boolean isValidPassword();

        void onError(String error);

        void goConfirmation();
    }

    interface Presenter{
        void toRegister(String username, String email, String password, boolean g);
    }

    interface Model{
        void doRegister(String username, String email, String password);

        void ConfirmationEmail();
    }

    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
