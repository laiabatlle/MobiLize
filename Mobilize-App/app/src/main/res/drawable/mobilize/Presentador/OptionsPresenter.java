package com.app.mobilize.Presentador;

import com.app.mobilize.Model.OptionsModel;
import com.app.mobilize.Presentador.Interface.OptionsInterface;

public class OptionsPresenter implements OptionsInterface.Presenter, OptionsInterface.TaskListener {

    private OptionsInterface.View view;
    private OptionsInterface.Model model;

    public OptionsPresenter(OptionsInterface.View view) {
        this.view = view;
        model = new OptionsModel(this);
    }

    @Override
    public void toLogout() {
        if(view!=null){
            view.disableInputs();
        }
        model.doLogout();
    }

    @Override
    public void toDelete(String user) {
        if(view!=null){
            view.disableInputs();
        }
        model.doDelete(user);
    }

    @Override
    public void toPrivacity(String user, String privacity) {
        model.doPrivacity(user, privacity);
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String error) {

    }
}
