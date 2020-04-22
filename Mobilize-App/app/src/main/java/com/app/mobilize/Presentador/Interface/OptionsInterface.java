package com.app.mobilize.Presentador.Interface;

public interface OptionsInterface {
    interface View {
        void enableInputs();
        void disableInputs();
        void handlePrivacity(String privacity);
    }

    interface Presenter{
        void toLogout();
        void toDelete(String username);
        void toPrivacity(String email, String privacity);
    }

    interface Model{
        void doLogout();
        void doDelete(String username);
        void doPrivacity(String email, String privacity);
    }

    interface TaskListener{
        void onSuccess(String message);
        void onError(String error);
    }
}
