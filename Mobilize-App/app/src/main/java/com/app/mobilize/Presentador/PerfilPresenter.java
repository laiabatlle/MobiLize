package com.app.mobilize.Presentador;

import com.app.mobilize.Model.PerfilModel;
import com.app.mobilize.Presentador.Interface.PerfilInterface;

public class PerfilPresenter implements PerfilInterface.Presenter, PerfilInterface.TaskListener {

    private PerfilInterface.View view;
    private PerfilInterface.Model model;


    public PerfilPresenter(PerfilInterface.View view) {
        this.view = view;
        //model = new PerfilModel(this);
    }

    @Override
    public void toGuardarCambios(String username, String dateNaixement, String gendre, String weight, String height, String image) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String error) {

    }
}
