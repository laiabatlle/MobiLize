package com.app.mobilize.Vista.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.CreateEventPresenter;
import com.app.mobilize.Presentador.Interface.CreateEventInterface;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;

public class CreateEventFragment extends Fragment implements CreateEventInterface.View, View.OnClickListener {

    private static final int GALLERY_INTENT = 1;

    private Usuari user;
    private EditText description, dateEvent, max_part, min_part;
    private ImageView eventImage;
    private Uri imageUri;
    private Button createEvent;

    private CreateEventInterface.Presenter presenter;

    public CreateEventFragment(Usuari user) {
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        presenter = new CreateEventPresenter(this, user);

        eventImage = (ImageView)view.findViewById(R.id.EventoIV);
        eventImage.setOnClickListener(this);

        description = (EditText) view.findViewById(R.id.descriptionEvent);

        //EditText de la dataNaixement de l'usuari:
        dateEvent = (EditText)view.findViewById(R.id.dateEvent);
        dateEvent.setOnClickListener(this);

        //EditText del pes de l'usuari:
        max_part = (EditText) view.findViewById(R.id.max_partEvent);

        //EditText del altura de l'usuari:
        min_part = (EditText) view.findViewById(R.id.min_partEvent);

        createEvent = (Button) view.findViewById(R.id.crearEvento);
        createEvent.setOnClickListener(this);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int years = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // +1 perque Gener = 0;
                final String selectedDate = twoDigits(dayOfMonth) + "/" + twoDigits(monthOfYear+1) + "/" + twoDigits(year);
                dateEvent.setText(selectedDate);
            }
        }, years, month, day);
        datePickerDialog.show();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        gallery.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(gallery, GALLERY_INTENT);
    }

    @Override
    //Funcio que assigna la imatge seleccionada com a nova imatge d'acatar de l'usuari:
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            handleImage(uri);
        }
    }

    //Funcio per passar la data en format de dos digits
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    //Codi per demanar permis a l'usuari
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private boolean checkPermissionREAD_EXTERNAL_STORAGE(Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("Permiso necesario", context, Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(msg);
        alertBuilder.setMessage("Se requiere su permiso para acceder a la galería de imágenes.");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private void setInputs(boolean enable){
        eventImage.setEnabled(enable);
        dateEvent.setEnabled(enable);
        description.setEnabled(enable);
        max_part.setEnabled(enable);
        min_part.setEnabled(enable);
        createEvent.setEnabled(enable);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void handleCreateEvent() {
        // Create the user with the email and password introduced
        if(!isValidDescription()){
            description.setError(getResources().getString(R.string.incorrectUsername));
        }
        if(!isValidDate()){
            dateEvent.setError(getResources().getString(R.string.incorrectEmail));
        }
        if(!isValidParticipantRestriccions1()){
            if (TextUtils.isEmpty(max_part.getText().toString())){
                max_part.setError(getResources().getString(R.string.incorrectPassword));
            }
            if (TextUtils.isEmpty(min_part.getText().toString())){
                min_part.setError(getResources().getString(R.string.incorrectPassword));
            }
        }
        else if(!isValidParticipantRestriccions2()){
            max_part.setError(getResources().getString(R.string.incorrectPassword));
        }
        else {
            presenter.toCreateEvent(imageUri, description.getText().toString(), dateEvent.getText().toString(), max_part.getText().toString(), min_part.getText().toString());
        }
    }

    private boolean isValidParticipantRestriccions1() {
        return !TextUtils.isEmpty(max_part.getText().toString()) && !TextUtils.isEmpty(min_part.getText().toString());
    }

    private boolean isValidParticipantRestriccions2() {
        int max = Integer.parseInt(max_part.getText().toString());
        int min = Integer.parseInt(min_part.getText().toString());
        return max >= min;
    }

    //TODO control that dateEvent is posterior than the current time.
    private boolean isValidDate() {
        return  true;
    }

    private boolean isValidDescription() {
        return !TextUtils.isEmpty(description.getText().toString());
    }

    @Override
    public void handleImage(Uri image) {
        presenter.toImageChange(image);
    }

    @Override
    public void onSuccesImageChange(Uri uriImage) {
        imageUri = uriImage;
        Glide.with(getActivity()).load(imageUri).into(eventImage);
    }

    @Override
    public void onSuccess(String message) {
        if (message.equals("Evento creado correctamente")){
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.EventoIV:
                if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) openGallery();
                break;

            case R.id.dateEvent:
                showDatePickerDialog();
                break;

            case R.id.crearEvento:
                handleCreateEvent();
                break;

            default:
                break;
        }

    }
}
