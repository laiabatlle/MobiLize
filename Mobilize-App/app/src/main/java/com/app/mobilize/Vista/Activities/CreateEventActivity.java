package com.app.mobilize.Vista.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.app.mobilize.Presentador.CreateEventPresenter;
import com.app.mobilize.Presentador.Interface.CreateEventInterface;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity implements CreateEventInterface.View, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int GALLERY_INTENT = 1;

    private String current_user;
    private EditText title, description, max_part;
    private TextView dateEvent, hourEvent;
    private Spinner sportEvent;
    private static final String [] sports = {"","Running", "Cycling", "Swimminig", "Basketball", "Football", "Voleyball", "Otro"};
    private String sport;
    private ImageView eventImage;
    private Uri imageUri;
    private Button createEvent;
    private ImageButton pick_hour, pick_date;

    private CreateEventInterface.Presenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        this.current_user = this.getIntent().getStringExtra("user");
        setViews();
    }

    private void setViews() {
        presenter = new CreateEventPresenter(this);

        eventImage = findViewById(R.id.EventoIV);
        eventImage.setOnClickListener(this);

        title = findViewById(R.id.titleEventET);
        description = findViewById(R.id.descriptionEventET);

        dateEvent = findViewById(R.id.dateEvent);
        pick_date = findViewById(R.id.dateEventCalendar);
        pick_date.setOnClickListener(this);

        hourEvent = findViewById(R.id.hourEvent);
        pick_hour = findViewById(R.id.hourEventClock);
        pick_hour.setOnClickListener(this);

        sportEvent = (Spinner) findViewById(R.id.sportSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sports);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportEvent.setAdapter(adapter);
        sportEvent.setSelection(getPosition(sport));
        sportEvent.setOnItemSelectedListener(this);

        max_part = findViewById(R.id.max_partEvent);

        createEvent = findViewById(R.id.actionEvento);
        createEvent.setText(getResources().getString(R.string.CrearEventoButton));
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // +1 perque Gener = 0;
                final String selectedDate = twoDigits(dayOfMonth) + "/" + twoDigits(monthOfYear+1) + "/" + twoDigits(year);
                dateEvent.setText(selectedDate);
            }
        }, years, month, day);
        datePickerDialog.show();
    }

    private void showHourPickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf("0" + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                hourEvent.setText(horaFormateada + ":" + minutoFormateado + " " + AM_PM);
            }
        }, hour, min, false);
        timePickerDialog.show();
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
        existingTitle();
        if(!isValidTitle()){
            title.setError(getResources().getString(R.string.incorrectTitleEvent));
        }
        else if(!isValidDescription()){
            description.setError(getResources().getString(R.string.incorrectDescriptionEvent));
        }
        else if(!isValidDate()){
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
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
        else if(!isValidParticipantRestriccions1()){
            if (TextUtils.isEmpty(max_part.getText().toString())){
                max_part.setError(getResources().getString(R.string.incorrectMax_partEvent));
            }
        }
        else if(imageUri == null){
            Log.d("deporte:", sport);
            if(sport.equals("Running")) imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mobilize-app-123.appspot.com/o/eventsImages%2Frunning_event.jpg?alt=media&token=8fbee4d3-837f-45a9-bdd3-049dcafb2642");
            else if (sport.equals("Cycling")) imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mobilize-app-123.appspot.com/o/eventsImages%2Fcycling_event.jpg?alt=media&token=d309a557-82c7-4f4b-b08e-d408916518a4");
            else if (sport.equals("Swimminig")) imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mobilize-app-123.appspot.com/o/eventsImages%2Fswim_event.png?alt=media&token=efe869f8-b4f7-47af-9e54-160dc90114b3");
            else if (sport.equals("Basketball")) imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mobilize-app-123.appspot.com/o/eventsImages%2Fbasketball_event.jpg?alt=media&token=af87e7ed-f52a-49b8-9ead-4d03ce1302ed");
            else if (sport.equals("Football")) imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mobilize-app-123.appspot.com/o/eventsImages%2Ffootball_event.jpg?alt=media&token=5d3fd4b2-e688-4eed-b7fd-d13569a55735");
            else if (sport.equals("Voleyball")) imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/mobilize-app-123.appspot.com/o/eventsImages%2Fvolley_event.png?alt=media&token=5f946553-8749-43b4-8ded-212e470c1d82");
            else if (sport.equals("Otro")) {
                Log.d("deporte:", "whataaaa!!! "+sport);
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.ImageEventError)).setTitle("Error").setCancelable(true);

                AlertDialog alert = builder.create();

                alert.setTitle("Error");
                alert.show();
            }
            if(imageUri != null){
                presenter.toCreateEvent(imageUri, title.getText().toString(), description.getText().toString(), dateEvent.getText().toString(), hourEvent.getText().toString(), sport, max_part.getText().toString(), current_user, new ArrayList<String>(), 0);
                this.finish();
            }
        }
        else {
            presenter.toCreateEvent(imageUri, title.getText().toString(), description.getText().toString(), dateEvent.getText().toString(), hourEvent.getText().toString(), sport, max_part.getText().toString(), current_user, new ArrayList<String>(), 0);
            this.finish();
        }
    }

    private void existingTitle() {
        CollectionReference event_ref = FirebaseFirestore.getInstance().collection("Events");
        event_ref.whereEqualTo("title", title.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        title.setError(getResources().getString(R.string.existingTitleEvent));
                    }
                }
            }
        });
    }

    private boolean isValidTitle() {
        return !TextUtils.isEmpty(title.getText().toString());
    }

    private boolean isValidDescription() {
        return !TextUtils.isEmpty(description.getText().toString());
    }

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

    private boolean isValidParticipantRestriccions1() {
        return !TextUtils.isEmpty(max_part.getText().toString());
    }

    @Override
    public void handleImage(Uri image) {
        presenter.toImageChange(image);
    }

    @Override
    public void onSuccesImageChange(Uri uriImage) {
        imageUri = uriImage;
        Glide.with(this).load(imageUri).into(eventImage);
    }

    @Override
    public void onSuccess(String message) {
        if (message.equals("SuccesEventCreate")){
            String mess = getResources().getString(R.string.SuccesEventCreate);
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.EventoIV:
                if (checkPermissionREAD_EXTERNAL_STORAGE(this)) openGallery();
                break;

            case R.id.dateEventCalendar:
                showDatePickerDialog();
                break;

            case R.id.hourEventClock:
                showHourPickerDialog();
                break;

            case R.id.actionEvento:
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
