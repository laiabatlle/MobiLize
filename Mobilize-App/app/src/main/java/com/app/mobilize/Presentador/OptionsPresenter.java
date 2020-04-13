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
    public void toDelete(String username) {
        if(view!=null){
            view.disableInputs();
        }
        model.doDelete(username);
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String error) {

    }
}
