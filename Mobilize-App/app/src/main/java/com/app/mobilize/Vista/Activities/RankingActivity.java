package com.app.mobilize.Vista.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Objects;

import com.app.mobilize.Model.Ranking;
import com.app.mobilize.Presentador.Adapter.AdapterRanking;
import com.app.mobilize.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RankingActivity extends AppCompatActivity {

    String email;
    private RecyclerView ranking;
    private ArrayList<Ranking> globalRankingList;
    private ArrayList<Ranking> friendRankingList;
    private ArrayList<String> friendList;
    private CollectionReference rank_ref;
    private CollectionReference user_ref;
    private Switch rankType;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        mContext = this;
        rank_ref = FirebaseFirestore.getInstance().collection("Ranking");
        user_ref = FirebaseFirestore.getInstance().collection("users");

        email = SaveSharedPreference.getEmail(this);

        friendList = new ArrayList<>();
        user_ref.whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        friendList = ((ArrayList<String>) document.get("friendsList"));
                    }
                }
            }
        });

        globalRankingList = new ArrayList<>();
        friendRankingList = new ArrayList<>();
        ranking = (RecyclerView) findViewById(R.id.rv_rank);
        ranking.addItemDecoration(new DividerItemDecoration(ranking.getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager lm1 = new LinearLayoutManager(this);
        ranking.setLayoutManager(lm1);
        handleChargeGlobalRank();
        handleFriendRank();

        rankType = (Switch) this.findViewById(R.id.switchRank);
        rankType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AdapterRanking ad = new AdapterRanking(mContext, friendRankingList);
                    ranking.setAdapter(ad);
                }
                else {
                    AdapterRanking ad = new AdapterRanking(mContext, globalRankingList);
                    ranking.setAdapter(ad);
                }
            }
        });

    }

    private void handleFriendRank() {
        rank_ref.orderBy("points", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (friendList.contains(document.getId()) || document.getId().equals(email)){
                            Log.d("saaaaaaaa", document.getId());
                            Ranking u = new Ranking();
                            u.setUser(document.getData().get("user").toString());
                            u.setPoints((Long) document.getData().get("points"));
                            friendRankingList.add(u);
                        }
                    }
                }
            }
        });
    }

    private void handleChargeGlobalRank() {

        rank_ref.orderBy("points", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Ranking u = new Ranking();
                        u.setUser(document.getData().get("user").toString());
                        u.setPoints((Long) document.getData().get("points"));
                        globalRankingList.add(u);
                        AdapterRanking ad = new AdapterRanking(mContext, globalRankingList);
                        ranking.setAdapter(ad);
                    }
                }
            }
        });
    }
}
