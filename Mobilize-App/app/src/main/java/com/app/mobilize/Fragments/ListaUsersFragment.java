package com.app.mobilize.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilize.Adapter.AdapterUsuarios;
import com.app.mobilize.Pojo.Usuari;
import com.app.mobilize.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaUsersFragment extends Fragment {

    //private final Usuari user;
    String username;
    RecyclerView lista;
    SearchView buscador;
    AdapterUsuarios adapterUsuarios;
    LinearLayoutManager lm;
    ArrayList<Usuari> listaUsuarios;
    private FirebaseFirestore ref;

    public ListaUsersFragment(/*Usuari user,*/ String busqueda) {
        //this.user = user;
        this.username = busqueda;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listausers_fragment, container, false);
        ref = FirebaseFirestore.getInstance();

        lista = (RecyclerView) view.findViewById(R.id.rv);
        buscador = (SearchView) view.findViewById(R.id.searchView);
        lm = new LinearLayoutManager(getContext());
        lista.setLayoutManager(lm);

        listaUsuarios = new ArrayList<>();
        adapterUsuarios = new AdapterUsuarios(listaUsuarios);
        lista.setAdapter(adapterUsuarios);

        ref.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Usuari u = new Usuari();
                        u.setUsername(document.getData().get("username").toString());
                        u.setImage(document.getData().get("image").toString());
                        listaUsuarios.add(u);
                    }
                    adapterUsuarios.notifyDataSetChanged();
                    buscador.setQuery(username,true);
                } else {
                    Toast.makeText(getContext(), "No se ha encontrado ningún usuario con ese nombre.", Toast.LENGTH_SHORT).show();
               }
            }
        });

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });
        return view;
    }

    private void buscar(String s) {
        ArrayList<Usuari> miLista = new ArrayList<>();
        for (Usuari u : listaUsuarios) {
            if (u.getUsername().toLowerCase().contains(s.toLowerCase())) {
                miLista.add(u);
            }
        }
        if (miLista.isEmpty())
            Toast.makeText(getContext(), "No se ha encontrado ningún usuario con ese nombre.", Toast.LENGTH_SHORT).show();
            AdapterUsuarios ad = new AdapterUsuarios(miLista);
            lista.setAdapter(ad);
    }
}
