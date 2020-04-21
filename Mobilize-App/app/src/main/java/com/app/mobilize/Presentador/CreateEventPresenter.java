package com.app.mobilize.Presentador;

import android.net.Uri;

import com.app.mobilize.Model.CreateEventModel;
import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Interface.CreateEventInterface;

public class CreateEventPresenter implements CreateEventInterface.Presenter, CreateEventInterface.TaskListener {

    private Usuari user;
    private CreateEventInterface.View view;
    private CreateEventInterface.Model model;

    public CreateEventPresenter(CreateEventInterface.View view, Usuari user) {
        this.view = view;
        this.model = new CreateEventModel(this);
        this.user = user;
    }

    @Override
    public void toCreateEvent(Uri imageUri, String desciption, String dateEvent, String hourEvent, String sportEvent, String max_part, String min_part) {
        model.doCreateEvent(imageUri, desciption, dateEvent, hourEvent, sportEvent, max_part, min_part);
    }

    @Override
    public void toImageChange(Uri image) {
        model.doImageChange(image);
    }

    @Override
    public void onSuccess(String message) {
        if(view!=null){
            view.enableInputs();
            view.onSuccess(message);
        }
    }

    @Override
    public void OnSuccesImageChange(Uri downloadUri) {
        if(view!=null){
            view.enableInputs();
            view.onSuccesImageChange(downloadUri);
        }
    }
}
