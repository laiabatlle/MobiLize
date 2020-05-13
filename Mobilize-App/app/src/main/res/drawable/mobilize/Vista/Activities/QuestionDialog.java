package com.app.mobilize.Vista.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilize.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class QuestionDialog extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> inscriptions;
    private String type, currentUser, title, descrption, image, sport, date, hour, max_part;
    ImageButton icon;
    TextView titleDialog, textDialog;
    Button ok, cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_dialog);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .35));

        this.type = this.getIntent().getStringExtra("type");
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
        this.icon = findViewById(R.id.iconDialog);
        this.titleDialog = findViewById(R.id.titleDialog);
        this.textDialog = findViewById(R.id.textDialog);
        if(type.equals("modify")){
            icon.setImageResource(R.mipmap.ic_edit_round);
            titleDialog.setText(getResources().getString(R.string.ModificarEsdeveniment));
            textDialog.setText(getResources().getString(R.string.ModificarEsdevenimentText));
        }
        else if (type.equals("delete")){
            icon.setImageResource(R.mipmap.ic_delete);
            titleDialog.setText(getResources().getString(R.string.EliminarEsdeveniment));
            textDialog.setText(getResources().getString(R.string.EliminarEsdevenimentText));
        }

        this.ok = findViewById(R.id.OkDialogButton);
        ok.setOnClickListener(this);
        this.cancel = findViewById(R.id.CancelDialogButton);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OkDialogButton:
                if(type.equals("modify")) {
                    Intent intent = new Intent(this, ModifyEventActivity.class);
                    intent.putExtra("current_user", currentUser);
                    intent.putExtra("title", title);
                    intent.putExtra("descrption", descrption);
                    intent.putExtra("image", image);
                    intent.putExtra("sport", sport);
                    intent.putExtra("date", date);
                    intent.putExtra("hour", hour);
                    intent.putExtra("max_part", max_part);
                    intent.putStringArrayListExtra("inscriptions", inscriptions);
                    startActivity(intent);
                    finish();

                }else{
                    CollectionReference event_ref = FirebaseFirestore.getInstance().collection("Events");;
                    event_ref.document(title).delete();
                    finish();
                }
                break;
            case R.id.CancelDialogButton:
                finish();
                break;
            default:
                break;
        }
    }
}
