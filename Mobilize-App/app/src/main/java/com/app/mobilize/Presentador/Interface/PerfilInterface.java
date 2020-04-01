package com.app.mobilize.Presentador.Interface;

public interface PerfilInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void handleOptions();
        void handleGuardarCambios();
    }

    interface Presenter{
        void toGuardarCambios(String username, String dateNaixement, String gendre, String weight, String height, String image);
    }

    interface Model{
        void doLogin(String email, String password);
    }

    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
