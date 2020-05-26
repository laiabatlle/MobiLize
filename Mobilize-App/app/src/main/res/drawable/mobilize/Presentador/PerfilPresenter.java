package com.app.mobilize.Presentador;

import android.net.Uri;

import com.app.mobilize.Model.PerfilModel;
import com.app.mobilize.Presentador.Interface.PerfilInterface;

public class PerfilPresenter implements PerfilInterface.Presenter, PerfilInterface.TaskListener {

    private PerfilInterface.View view;
    private PerfilInterface.Model model;

    public PerfilPresenter(PerfilInterface.View view) {
        this.view = view;
        this.model = new PerfilModel(this);
    }

    @Override
    public void toGuardarCambios(String username, String email, String dateNaixement, String gendre, String weight, String height, String image/*, String privacity*/) {
        if(view!=null){
            view.disableInputs();
        }
        model.doGuardarCambios(username, email, dateNaixement, gendre, weight, height, image/*, privacity*/);
    }

    @Override
    public void toImageChange(Uri uri) {
        if(view!=null){
            view.disableInputs();
        }
        model.doImageChange(uri);
    }

    @Override
    public void haveAnyFriendReq(String username) {
        model.haveAnyFriendReq(username);
    }

    @Override
    public void onSuccess(String message) {
        if(view!=null){
            view.enableInputs();
            view.onSuccess(message);
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void OnSuccesImageChange(Uri uriImage) {
        if(view!=null){
            view.enableInputs();
            view.onSuccesImageChange(uriImage);
        }
    }

    @Override
    public void setReq(boolean b) {
        view.setReq(b);
    }
}
