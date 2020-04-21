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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.CreateEventPresenter;
import com.app.mobilize.Presentador.Interface.CreateEventInterface;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEventFragment extends Fragment implements CreateEventInterface.View, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int GALLERY_INTENT = 1;

    private Usuari user;
    private EditText description, dateEvent, hourEvent, max_part, min_part;
    private Spinner sportEvent;
    private static final String [] sports = {"","Running", "Cycling", "Swimminig", "Basketball", "Football", "Voleyball"};
    private String sport;
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

        eventImage = view.findViewById(R.id.EventoIV);
        eventImage.setOnClickListener(this);

        description = view.findViewById(R.id.descriptionEvent);
        dateEvent = view.findViewById(R.id.dateEvent);
        dateEvent.setOnClickListener(this);

        hourEvent = view.findViewById(R.id.hourEvent);
        hourEvent.setOnClickListener(this);

        sportEvent = (Spinner)view.findViewById(R.id.sportSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sports);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportEvent.setAdapter(adapter);
        sportEvent.setSelection(getPosition(sport));
        sportEvent.setOnItemSelectedListener(this);

        max_part = view.findViewById(R.id.max_partEvent);

        min_part = view.findViewById(R.id.min_partEvent);

        createEvent = view.findViewById(R.id.crearEvento);
        createEvent.setOnClickListener(this);
    }

    private int getPosition(String gendre) {
        int posicion = 0;
        for (int i = 0; i < sportEvent.getCount(); i++) {
            if (sportEvent.getItemAtPosition(i).toString().equalsIgnoreCase(gendre)) {
                posicion = i;
            }
        }
        return posicion;
    }

    //Funcio que retorna l'item seleccionat de l'spinner per seleccionar el genere de l'usuari:
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.sportSpin) {
            sport = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    public void handleCreateEvent() throws ParseException {
        // Create the user with the email and password introduced
        if(imageUri == null){
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getResources().getString(R.string.ImageEventError)).setTitle("Error").setCancelable(true);

            AlertDialog alert = builder.create();

            alert.setTitle("Error");
            alert.show();
        }

        if(!isValidDescription()){
            description.setError(getResources().getString(R.string.incorrectDescriptionEvent));
        }
        if(!isValidDate()){
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(getActivity());
            if(dateEvent.getText().toString().equals(getResources().getString(R.string.Fecha_NacimientoHint))){
                builder.setMessage(getResources().getString(R.string.emptyDateEvent)).setTitle("Error").setCancelable(true);
            }
            else{
                builder.setMessage(getResources().getString(R.string.incorrectDateEvent)).setTitle("Error").setCancelable(true);
            }
            AlertDialog alert = builder.create();

            alert.setTitle("Error");
            alert.show();
            dateEvent.setError(getResources().getString(R.string.incorrectDateEvent));
        }
        if(!isValidParticipantRestriccions1()){
            if (TextUtils.isEmpty(max_part.getText().toString())){
                max_part.setError(getResources().getString(R.string.incorrectMax_partEvent));
            }
            if (TextUtils.isEmpty(min_part.getText().toString())){
                min_part.setError(getResources().getString(R.string.incorrectMin_partEvent));
            }
        }
        else if(!isValidParticipantRestriccions2()){
            max_part.setError(getResources().getString(R.string.incorrectRest_Part_Event));
        }
        else {
            presenter.toCreateEvent(imageUri, description.getText().toString(), dateEvent.getText().toString(), hourEvent.getText().toString(), sport, max_part.getText().toString(), min_part.getText().toString());
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
    private boolean isValidDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(dateEvent.getText().toString());
            if (new Date().after(date)) {
                return false;
            }
            else return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
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
        if (message.equals("SuccesEventCreate")){
            String mess = getResources().getString(R.string.SuccesEventCreate);
            Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
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
                try {
                    handleCreateEvent();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

    }
}
