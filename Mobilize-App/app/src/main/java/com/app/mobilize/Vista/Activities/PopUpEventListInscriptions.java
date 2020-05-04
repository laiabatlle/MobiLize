package com.app.mobilize.Vista.Activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Model.Usuari;
import com.app.mobilize.Presentador.Adapter.AdapterUsuarios;
import com.app.mobilize.Presentador.Interface.BuscadorUserInterface;
import com.app.mobilize.Presentador.PopUpEventListInscriptionsPresenter;
import com.app.mobilize.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class PopUpEventListInscriptions extends AppCompatActivity implements BuscadorUserInterface.View {

    String event;
    Usuari currentUser;
    BuscadorUserInterface.Presenter presenter;
    private RecyclerView lista;
    private LinearLayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_event);
        currentUser = new Usuari();
        event = this.getIntent().getStringExtra("event");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .95), (int) (height * .5));

        setViews();
    }

    @Override
    public void onStart(){
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("email", SaveSharedPreference.getEmail(this)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        currentUser.setEmail(Objects.requireNonNull(document.getData().get("email")).toString());
                        currentUser.setUsername(Objects.requireNonNull(document.getData().get("username")).toString());
                        currentUser.setGender(Objects.requireNonNull(document.getData().get("gender")).toString());
                        currentUser.setDateNaixement(Objects.requireNonNull(document.getData().get("dateNaixement")).toString());
                        String altura = Objects.requireNonNull(document.getData().get("height")).toString();
                        String peso = Objects.requireNonNull(document.getData().get("weight")).toString();
                        currentUser.setHeight(altura);
                        currentUser.setWeight(peso);
                        currentUser.setPrivacity(Objects.requireNonNull(document.getData().get("privacity")).toString());

                        currentUser.setImage(Objects.requireNonNull(document.getData().get("image")).toString());
                        currentUser.setFriendsList((List<String>) document.get("friendsList"));
                    }
                }
            }
        });
        super.onStart();
    }

    private void setViews() {
        presenter = new PopUpEventListInscriptionsPresenter(this,this, currentUser, event);
        lista = (RecyclerView) findViewById(R.id.rv);
        lista.addItemDecoration(new DividerItemDecoration(lista.getContext(), DividerItemDecoration.VERTICAL));
        lm = new LinearLayoutManager(this);
        lista.setLayoutManager(lm);

        handleChargeUserList();
    }

    private void setInputs(boolean enable) {
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
    public void handleChargeUserList() {
        presenter.toGetUserList();
    }

    @Override
    public void handleSearchUser(String s) {
    }

    @Override
    public void setBuscador() {
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, getResources().getString(R.string.userNotFound), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAdapterList(AdapterUsuarios adapterUsuarios) {
        lista.setAdapter(adapterUsuarios);
    }
}