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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.mobilize.Presentador.CreateEventPresenter;
import com.app.mobilize.Presentador.Interface.CreateEventInterface;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ModifyEventActivity extends AppCompatActivity implements CreateEventInterface.View, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int GALLERY_INTENT = 1;

    private ArrayList<String> inscriptions;
    private String currentUser, title, descrption, image, sport, date, hour, max_part;
    private EditText titleEvent, descriptionEvent, max_partEvent;
    private TextView dateEvent, hourEvent;
    private Spinner sportEvent;
    private static final String [] sports = {"","Running", "Cycling", "Swimminig", "Basketball", "Football", "Voleyball", "Otro"};
    private ImageView eventImage;
    private Uri imageUri;
    private Button modifyEvent;
    private ImageButton pick_hour, pick_date;

    private CreateEventInterface.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        this.currentUser = this.getIntent().getStringExtra("current_user");
        this.title = this.getIntent().getStringExtra("title");
        this.descrption = this.getIntent().getStringExtra("descrption");
        this.image = this.getIntent().getStringExtra("image");
        this.sport = this.getIntent().getStringExtra("sport");
        this.date = this.getIntent().getStringExtra("date");
        this.hour = this.getIntent().getStringExtra("hour");
        this.max_part = this.getIntent().getStringExtra("max_part");
        this.inscriptions = this.getIntent().getStringArrayListExtra("inscriptions");
        setViews();
    }

    private void setViews() {
        presenter = new CreateEventPresenter(this);

        eventImage = findViewById(R.id.EventoIV);
        Glide.with(this).load(Uri.parse(image)).into(eventImage);
        imageUri = Uri.parse(image);
        eventImage.setOnClickListener(this);

        titleEvent = findViewById(R.id.titleEventET);
        titleEvent.setText(title);
        titleEvent.setEnabled(false);
        descriptionEvent = findViewById(R.id.descriptionEventET);
        descriptionEvent.setText(descrption);

        dateEvent = findViewById(R.id.dateEvent);
        dateEvent.setText(date);
        pick_date = findViewById(R.id.dateEventCalendar);
        pick_date.setOnClickListener(this);

        hourEvent = findViewById(R.id.hourEvent);
        hourEvent.setText(hour);
        pick_hour = findViewById(R.id.hourEventClock);
        pick_hour.setOnClickListener(this);

        sportEvent = (Spinner) findViewById(R.id.sportSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sports);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportEvent.setAdapter(adapter);
        sportEvent.setSelection(getPosition(sport));
        sportEvent.setOnItemSelectedListener(this);

        max_partEvent = findViewById(R.id.max_partEvent);
        max_partEvent.setText(max_part);

        modifyEvent = findViewById(R.id.actionEvento);
        modifyEvent.setText(getResources().getString(R.string.ModificarEsdeveniment));
        modifyEvent.setOnClickListener(this);
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
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
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
        descriptionEvent.setEnabled(enable);
        max_partEvent.setEnabled(enable);
        modifyEvent.setEnabled(enable);
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
        if(!isValidDescription()){
            descriptionEvent.setError(getResources().getString(R.string.incorrectDescriptionEvent));
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
            if (TextUtils.isEmpty(max_partEvent.getText().toString())){
                max_partEvent.setError(getResources().getString(R.string.incorrectMax_partEvent));
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
                if(dateEvent.getText().toString().equals(date) && hourEvent.getText().toString().equals(hour)) {
                    presenter.toCreateEvent(imageUri, titleEvent.getText().toString(), descriptionEvent.getText().toString(), dateEvent.getText().toString(), hourEvent.getText().toString(), sport, max_partEvent.getText().toString(), currentUser, inscriptions, 1);
                    this.finish();
                }else{
                    presenter.toCreateEvent(imageUri, titleEvent.getText().toString(), descriptionEvent.getText().toString(), dateEvent.getText().toString(), hourEvent.getText().toString(), sport, max_partEvent.getText().toString(), currentUser, new ArrayList<String>(), 1);
                    this.finish();
                }
            }
        }
        else {
            if(dateEvent.getText().toString().equals(date) && hourEvent.getText().toString().equals(hour)) {
                presenter.toCreateEvent(imageUri, titleEvent.getText().toString(), descriptionEvent.getText().toString(), dateEvent.getText().toString(), hourEvent.getText().toString(), sport, max_partEvent.getText().toString(), currentUser, inscriptions, 1);
                this.finish();
            }else{
                presenter.toCreateEvent(imageUri, titleEvent.getText().toString(), descriptionEvent.getText().toString(), dateEvent.getText().toString(), hourEvent.getText().toString(), sport, max_partEvent.getText().toString(), currentUser, new ArrayList<String>(), 1);
                this.finish();
            }
        }
    }

    private boolean isValidDescription() {
        return !TextUtils.isEmpty(descriptionEvent.getText().toString());
    }

    private boolean isValidDate() {
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
        return !TextUtils.isEmpty(max_partEvent.getText().toString());
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
