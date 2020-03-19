package com.app.mobilize.Fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.mobilize.R;
import com.app.mobilize.Usuari;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener  {

    private FirebaseFirestore db;
    private StorageReference st;
    private static final int GALLERY_INTENT = 1;
    private Usuari user;
    private EditText peso, altura, dateNaixement;
    private Button options;
    private Spinner genero;
    private String gendre;
    private ImageView avatar;
    private String imageUri;
    private SearchView buscadorAmigos;
    ArrayList<String> usernameList;
    ArrayList<String> imageuserList;


    public PerfilFragment(Usuari user) {
        this.user = user;
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        st = FirebaseStorage.getInstance().getReference();

        //Imatge de l'avatar de l'usuari:
        avatar = (ImageView)view.findViewById(R.id.AvatarIV);
        imageUri = user.getImage();
        Glide.with(this).load(Uri.parse(user.getImage())).into(avatar);
        avatar.setOnClickListener(this);

        //TextView de l'username:
        TextView username = (TextView) view.findViewById(R.id.usernameTV);
        username.setText(user.getUsername());

        //Buscador de Amics per a l'ususari:
        buscadorAmigos = (SearchView) view.findViewById(R.id.cearchFriendsSV);

        //Spinner del genere de l'usuari:
        genero = (Spinner)view.findViewById(R.id.generoSpin);
        String [] generos = {"","Hombre", "Mujer", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, generos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adapter);
        genero.setSelection(obtenerPosicion(user.getGender()));
        genero.setOnItemSelectedListener(this);

        //EditText de la dataNaixement de l'usuari:
        dateNaixement = (EditText)view.findViewById(R.id.dataCumpleañosTV);
        dateNaixement.setText(user.getDateNaixement());
        dateNaixement.setOnClickListener(this);

        //EditText del pes de l'usuari:
        peso = (EditText) view.findViewById(R.id.pesoET);
        peso.setText(user.getWeight());

        //EditText del altura de l'usuari:
        altura = (EditText) view.findViewById(R.id.alturaET);
        altura.setText(user.getHeight());

        //Boto de guardar canvis. S'actualitza la Base de Dades amb els parametres seleccionats als diferents widgets:
        Button guardar_cambios = (Button) view.findViewById(R.id.guardar);
        guardar_cambios.setOnClickListener(this);
        return view;
    }

    //Funcio per retornar l'element corresponent a l'string "gendre" de l'spinner per seleccionar el genere de l'usuari:
    private int obtenerPosicion(String gendre) {
        int posicion = 0;
        for (int i = 0; i < genero.getCount(); i++) {
            if (genero.getItemAtPosition(i).toString().equalsIgnoreCase(gendre)) {
                posicion = i;
            }
        }
        return posicion;
    }
    //Funcio que retorna l'item seleccionat de l'spinner per seleccionar el genere de l'usuari:
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gendre = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Funcio per obrir la galeria i seleccionar la nova imatge d'acatar de l'usuari:
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
        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            final StorageReference filePath = st.child("profileImages").child(uri.getLastPathSegment());
            UploadTask uploadTask = filePath.putFile(uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imageUri = downloadUri.toString();
                        Glide.with(getActivity()).load(downloadUri).into(avatar);
                        avatar.setImageURI(Uri.parse(imageUri));
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }

    //Funcio que mostra el calendari per a poder seleccionar la data de naixement de l'usuari:
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
                dateNaixement.setText(selectedDate);
            }
        }, day, month, years);
        datePickerDialog.show();
    }

    //Funcio per passar la data en format de dos digits
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    //Codi per demanar permis a l'usuari
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AvatarIV:
                if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) openGallery();
                break;

            case R.id.dataCumpleañosTV:
                showDatePickerDialog();
                break;

            case R.id.guardar:
                user.setWeight(peso.getText().toString());
                user.setHeight(altura.getText().toString());
                user.setGender(gendre);
                user.setDateNaixement(dateNaixement.getText().toString());
                user.setImage(imageUri);
                db.collection("users").document(user.getUsername()).update("weight", user.getWeight());
                db.collection("users").document(user.getUsername()).update("height", user.getHeight());
                db.collection("users").document(user.getUsername()).update("gender", gendre);
                db.collection("users").document(user.getUsername()).update("dateNaixement", user.getDateNaixement());
                db.collection("users").document(user.getUsername()).update("image",user.getImage());
                Toast.makeText(getContext(), "Sus datos se han actualizado correctamente.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
