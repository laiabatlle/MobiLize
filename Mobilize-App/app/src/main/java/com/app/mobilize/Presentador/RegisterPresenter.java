package com.app.mobilize.Presentador;

import com.app.mobilize.Model.RegisterModel;
import com.app.mobilize.Presentador.Interface.RegisterInterface;
import com.app.mobilize.Vista.Activities.RegisterActivity;

public class RegisterPresenter implements RegisterInterface.Presenter, RegisterInterface.TaskListener{

    private RegisterInterface.View view;
    private RegisterInterface.Model model;

    public RegisterPresenter(RegisterActivity view) {
        this.view = view;
        model = new RegisterModel(this);
    }

    @Override
    public void toRegister(String username, String email, String password) {
        if(view!=null){
            view.disableInputs();
            view.showPrgress();
        }
        model.doRegister(username, email, password);
    }

    @Override
    public void onSuccess() {
        if(view!=null){
            view.enableInputs();
            view.hideProgress();
            view.goConfirmation();
        }
    }

    @Override
    public void onError(String error) {
        if(view!=null){
            view.enableInputs();
            view.hideProgress();
            view.onError(error);
        }
    }
}
