package net.lecnam.vgb2.interroMariOffciel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.lecnam.vgb2.MainActivity;
import net.lecnam.vgb2.R;
import net.lecnam.vgb2.interrogatoireMari.questions.MyKeyValueQuestion;
import net.lecnam.vgb2.interrogatoireMari.reponses.MyKeyValueReponse;
import net.lecnam.vgb2.Score.MySummurize;

public class InterroMariActivity extends AppCompatActivity {
    private static final String TAG = "interro";
    MyKeyValueReponse myKeyValueReponse;
    MyKeyValueQuestion myKeyValueQuestion;
    String CodeQuestion;
    Boolean debloqOlivier= false;
    Boolean debloqBateau= false;
    int scoregagne = 5;
    int niveauEnerv = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interro_mari);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //todo clause if success
        MySummurize.getinstance().addScore(scoregagne);
    }


    public void backToMap(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }








}