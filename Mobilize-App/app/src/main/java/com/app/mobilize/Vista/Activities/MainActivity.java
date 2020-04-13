package com.app.mobilize.Vista.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.mobilize.R;
import com.app.mobilize.Vista.Fragments.EntrenaminetoFragment;
import com.app.mobilize.Vista.Fragments.EventosFragment;
import com.app.mobilize.Vista.Fragments.PerfilFragment;
import com.app.mobilize.Vista.Fragments.PlanFragment;
import com.app.mobilize.Vista.Fragments.ProgresoFragment;
import com.app.mobilize.Model.Usuari;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    Usuari user;
    String email;
    BottomNavigationView mBottomNavigation;

    FirebaseFirestore db;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        user = new Usuari();
        email = this.getIntent().getStringExtra("email");
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // Create a reference to the cities collection
        db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                user.setEmail(email);
                                user.setUsername(Objects.requireNonNull(document.getData().get("username")).toString());
                                user.setGender(Objects.requireNonNull(document.getData().get("gender")).toString());
                                user.setDateNaixement(Objects.requireNonNull(document.getData().get("dateNaixement")).toString());
                                String altura = Objects.requireNonNull(document.getData().get("height")).toString();
                                String peso = Objects.requireNonNull(document.getData().get("weight")).toString();
                                user.setHeight(altura);
                                user.setWeight(peso);
                                user.setImage(Objects.requireNonNull(document.getData().get("image")).toString());
                                user.setFriendsList((ArrayList<String>) document.get("friendsList"));
                            }
                        } else {
                            Log.d("MainActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        mBottomNavigation.setSelectedItemId(R.id.Entrenamiento);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.Evento){
                    showSelectedFragment(new EventosFragment());
                }
                if (menuItem.getItemId() == R.id.Plan){
                    showSelectedFragment(new PlanFragment());
                }
                if (menuItem.getItemId() == R.id.Entrenamiento){
                    showSelectedFragment(new EntrenaminetoFragment());
                }
                if (menuItem.getItemId() == R.id.Progreso){
                    showSelectedFragment(new ProgresoFragment());
                }
                if (menuItem.getItemId() == R.id.Perfil){
                    showSelectedFragment(new PerfilFragment(user));
                }
                return true;
            }
        });
    }

    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
