package com.app.mobilize.Presentador.Interface;

public interface OptionsInterface {
    interface View {
        void enableInputs();
        void disableInputs();
    }

    interface Presenter{
        void toLogout();
        void toDelete(String username);
    }

    interface Model{
        void doLogout();
        void doDelete(String username);
    }

    interface TaskListener{
        void onSuccess(String message);
        void onError(String error);
    }
}
