package com.app.mobilize.Presentador;

import com.app.mobilize.Model.LoginModel;
import com.app.mobilize.Presentador.Interface.LoginInterface;

public class LoginPresenter implements LoginInterface.Presenter, LoginInterface.TaskListener {

    private LoginInterface.View view;
    private LoginInterface.Model model;

    public LoginPresenter(LoginInterface.View view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void toLogin(String email, String password) {
        if(view!=null){
            view.disableInputs();
            view.showPrgress();
        }
        model.doLogin(email, password);
    }

    @Override
    public void onSuccess() {
        if(view!=null){
            view.enableInputs();
            view.hideProgress();
            view.goMainMenu();
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
