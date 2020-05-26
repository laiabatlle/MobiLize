package com.app.mobilize.Vista.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.mobilize.Model.ActivitatFinalitzada;
import com.app.mobilize.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TrackActivity extends AppCompatActivity implements OnMapReadyCallback {

    String email, tipus;

    Chronometer chrono;
    Button bStart, bStop, bFinish;
    boolean is_Finish, is_running;
    long timeElapsed;
    double distance = 0;
    Polyline polyline = null;
    Intent service = null;

    ArrayList<ActivitatFinalitzada> activitatsF;
    boolean is_empty;

    boolean firstStop = true;
    boolean firstStart = true;

    int number = 1;

    Location initialLocation;

    private GoogleMap mMap;

    ArrayList<LocationSignal> locations;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("BOOM", "onReceive");
            if (intent.getAction().equals("getLocation")) {
                if (is_running == true) {
                    double Lat = intent.getDoubleExtra("latitude", -1000);
                    double Lng = intent.getDoubleExtra("longitude", -1000);
                    LatLng latLng;
                    if (Lat != -1000 && Lng != -1000) {
                        addLatLng(Lat,Lng);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        tipus = getIntent().getStringExtra("tipus");
        email = getIntent().getStringExtra("email");

        if ( isLocationEnabled() == false ) {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if (checkPermissions() == false) {
            requestPermissions();
            if (checkPermissions() == false) return;
        }

        getLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        is_running = false;
        is_Finish = false;
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, new IntentFilter("getLocation"));

        locations = new ArrayList<>();

        initChrono();

    }

    private String getTimeChrono ( long time ) {
        if ( time < 0 ) time = time*-1;
        long second = (time/1000)%60;
        long minute = (time/(1000*60))%60;
        long hour = (time/(1000*60*60))%24;
        String sSecond, sMinute, sHour;
        if ( second < 10 ) sSecond = "0" + String.valueOf(second);
        else sSecond = String.valueOf(second);
        if ( minute < 10 ) sMinute = "0" + String.valueOf(minute);
        else sMinute = String.valueOf(minute);
        if ( hour < 10 ) sHour = "0" + String.valueOf(hour);
        else sHour = String.valueOf(hour);
        return sHour + "h " + sMinute + "min " + sSecond + "sec";
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        if (initialLocation != null) {
            LatLng latLng = new LatLng(initialLocation.getLatitude(), initialLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Ubicació");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.setMinZoomPreference(15.0f);
        }
    }

    private void addLatLng ( double Lat, double Lng ) {
        LatLng latLng = new LatLng(Lat, Lng);
        if ( locations.isEmpty() == true ) {
            if ( firstStart == true ) {
                locations.add(new LocationSignal(latLng, "START", "00:00:00" ));
                firstStart = false;
            }
            else {
                String time = getTimeChrono(timeElapsed);
                locations.add(new LocationSignal(latLng, "STOP", time ));
            }
        }
        else {
            String time = getTimeChrono(timeElapsed);
            locations.add(new LocationSignal(latLng, "-", time));
        }
    }

    public void getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            initialLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (initialLocation != null) return;
            else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                initialLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
    }

    private void initChrono () {
        timeElapsed = 0;
        is_Finish = false;

        chrono = findViewById(R.id.chrono);

        bStart = findViewById(R.id.bStart);
        bFinish = findViewById(R.id.bFinish);
        bStop = findViewById(R.id.bStop);

        bStop.setVisibility(View.GONE);
        bFinish.setVisibility(View.GONE);

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( is_Finish == false ) {
                    chrono.setBase(SystemClock.elapsedRealtime() + timeElapsed);
                    chrono.start();

                    startService();
                    is_running = true;

                    bStop.setVisibility(View.VISIBLE);
                    bFinish.setVisibility(View.VISIBLE);
                    bStart.setVisibility(View.GONE);
                    bStart.setText("RESUME");

                }
            }
        });

        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( is_running == true && is_Finish == false ) {
                    if ( firstStop == true ) {
                        mMap.clear();
                        firstStop = false;
                    }
                    finishService();
                    timeElapsed = chrono.getBase() - SystemClock.elapsedRealtime();
                    chrono.stop();
                    countMetres();
                    bStop.setVisibility(View.GONE);
                    bFinish.setVisibility(View.GONE);
                    bStart.setVisibility(View.VISIBLE);

                    LocationSignal locationSignal = locations.get(locations.size()-1);
                    locationSignal.setSignal("STOP");
                    locationSignal.setExerciciTime(getTimeChrono(timeElapsed));
                    locations.set(locations.size()-1, locationSignal);
                    drawMap();
                    locations.clear();
                }
            }
        });

        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chrono.stop();
                timeElapsed = chrono.getBase() - SystemClock.elapsedRealtime();
                finishService();

                if ( firstStop == true ) {
                    mMap.clear();
                    firstStop = false;
                }

                if ( locations.size() > 0 ) {
                    LocationSignal locationSignal = locations.get(locations.size() - 1);
                    locationSignal.setSignal("FINISH");
                    locationSignal.setExerciciTime(getTimeChrono(timeElapsed));
                    locations.set(locations.size() - 1, locationSignal);
                }

                countMetres();
                is_Finish = true;
                drawMap();
                locations.clear();


                CalculateKcals calculateKcals = new CalculateKcals();
                double ritme = getRitme();
                double kcal;
                if ( ritme == -1 ) kcal = 0;
                else kcal = calculateKcals.calculateRunningKcal(60,getRitme());

                makeToast("Ritmo -> " + ritme + "  kcal -> kcal");

                Calendar calendar = Calendar.getInstance();
                String data = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH)) + "/" + String.valueOf(calendar.get(Calendar.YEAR));
                final ActivitatFinalitzada activitatFinalitzada = new ActivitatFinalitzada(data, email, -timeElapsed/1000, distance, 0, kcal);
                is_empty = false;
                activitatsF = new ArrayList<>();
                Map<String, ArrayList<ActivitatFinalitzada>> mapAux = new HashMap<>();
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("ActivitatsFinalitzades").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if ( task.isSuccessful() ) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if ( documentSnapshot.getData() == null ) {
                                Log.i("TASKFIREBASE", "NULL");
                                is_empty = true;
                            }
                            else if ( documentSnapshot.getData().isEmpty() ) {
                                Log.i("TASKFIREBASE", "EMPTY");
                                is_empty = true;
                            }
                            else {
                                Log.i("TASKFIREBASE", "SUCCES");
                                activitatsF = (ArrayList<ActivitatFinalitzada>) documentSnapshot.getData().get("activitats");
                                Log.i("TASKFIREBASE", "Size " +  String.valueOf(activitatsF.size()));
                                putMapFirebase(activitatFinalitzada);
                            }
                        }
                    }
                });
                Log.i("TASKFIREBASE", "Size " +  String.valueOf(activitatsF.size()));

                Log.i("TASKFIREBASE", "Size " +  String.valueOf(activitatsF.size()));





                // put ActivitatFinalitzada in Database

                showCustomDialog("Activitat Finalitzada!", getTimeChrono(timeElapsed), String.valueOf(kcal), String.valueOf(distance/1000));

            }
        });
    }

    private void putMapFirebase (ActivitatFinalitzada activitatFinalitzada ) {
        activitatsF.add(activitatFinalitzada);
        Map <String, ArrayList<ActivitatFinalitzada>> mapAux = new HashMap<>();
        mapAux.put("activitats", activitatsF);


        FirebaseFirestore.getInstance().collection("ActivitatsFinalitzades").document(email).set(mapAux).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if ( task.isSuccessful() ) Log.i("TASKFIREBASE", "Succesful");
                else Log.i("TASKFIREBASE", "Not Succesful");
            }
        });
    }

    private static double round ( double value, int places ) {
        if ( places < 0 ) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private double getRitme (){
        double hours = (timeElapsed/(1000*60*60))%24;
        double kms = distance/1000;

        hours = round(hours,2);
        kms = round(kms,2);

        if ( distance == 0 || hours == 0 || kms == 0 ) return -1;
        else return kms/hours;
    }

    private void goToMenu () {

        finish();
    }

    private String getUrlMarker ( String color, int number ) {
        String numberS = String.valueOf(number);
        String url = "https://raw.githubusercontent.com/Concept211/Google-Maps-Markers/master/images/marker_";
        url = url + color + numberS + ".png";
        return url;
    }

    private void drawMap () {
        PolylineOptions polylineOptions = new PolylineOptions();
        for ( int i=0; i<locations.size(); i++ ) {
            final LocationSignal lsAux = locations.get(i);
            polylineOptions.add(lsAux.getLatLng());
            if ( lsAux.getSignal() == "START" ) {
                String url = getUrlMarker("green", number);
                Glide.with(this)
                        .asBitmap().load(Uri.parse(url)).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(lsAux.getLatLng())
                                .title(String.valueOf(number)+ ": " + lsAux.getSignal())
                                .snippet("Time: " + lsAux.getExerciciTime() )
                                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(resource, 53, 96, false)));
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lsAux.getLatLng()));
                        mMap.setMinZoomPreference(15.0f);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
                number++;
            }
            else if ( lsAux.getSignal() == "STOP" ) {
                String url = getUrlMarker("red", number);
                Glide.with(this)
                        .asBitmap().load(Uri.parse(url)).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(lsAux.getLatLng())
                                .title(String.valueOf(number)+ ": " + lsAux.getSignal())
                                .snippet("Time: " + lsAux.getExerciciTime() )
                                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(resource, 53, 96, false)));
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lsAux.getLatLng()));
                        mMap.setMinZoomPreference(15.0f);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
                number++;
            }
            else if ( lsAux.getSignal() == "FINISH" ) {
                String url = getUrlMarker("blue", number);
                Glide.with(this)
                        .asBitmap().load(Uri.parse(url)).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(lsAux.getLatLng())
                                .title(String.valueOf(number)+ ": " + lsAux.getSignal())
                                .snippet("Time: " + lsAux.getExerciciTime() )
                                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(resource, 53, 96, false)));
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lsAux.getLatLng()));
                        mMap.setMinZoomPreference(15.0f);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
                number++;
            }
        }
        polyline = mMap.addPolyline(polylineOptions);
        locations.clear();
    }

    private void showCustomDialog (String title, String time, String kcal, String dist ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);

        final View customLayout = getLayoutInflater().inflate(R.layout.finish_activity, null);
        builder.setView(customLayout);

        Button bOkey = customLayout.findViewById(R.id.bOKFinishActivity);
        bOkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });

        TextView tvTime = customLayout.findViewById(R.id.tvActivityTime);
        TextView tvKms = customLayout.findViewById(R.id.tvDistance);
        TextView tvKcalBurned = customLayout.findViewById(R.id.tvKcalBurned);

        tvTime.setText(time);
        tvKms.setText(dist + " km");
        tvKcalBurned.setText(kcal + " kcal");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startService() {
        service = new Intent(this, MyService.class);
        startService(service);
    }

    private void finishService(){
        if ( service != null ) stopService(service);
    }

    private void makeToast( String msge ) {
        Toast.makeText(this,msge, Toast.LENGTH_LONG).show();
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                44
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void countMetres () {
        if ( locations.size() > 1 ) {
            for (int i = 0; i < locations.size() - 1; i++) {
                float[] result = new float[1];
                Location.distanceBetween(locations.get(i).getLatLng().latitude,
                        locations.get(i).getLatLng().longitude,
                        locations.get(i + 1).getLatLng().latitude,
                        locations.get(i + 1).getLatLng().longitude, result);
                distance = distance + result[0];
            }
        }
        else makeToast("No se han obtenido ubicaciones suficientes.");
    }

    class LocationSignal {
        private LatLng latLng;
        private String signal;
        private String exerciciTime;

        public LocationSignal(LatLng latLng, String signal, String exerciciTime) {
            this.latLng = latLng;
            this.signal = signal;
            this.exerciciTime = exerciciTime;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public String getSignal() {
            return signal;
        }

        public void setSignal(String signal) {
            this.signal = signal;
        }

        public String getExerciciTime() {
            return exerciciTime;
        }

        public void setExerciciTime(String exerciciTime) {
            this.exerciciTime = exerciciTime;
        }
    }
}