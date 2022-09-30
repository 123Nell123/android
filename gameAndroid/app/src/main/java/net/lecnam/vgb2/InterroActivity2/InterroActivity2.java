package net.lecnam.vgb2.InterroActivity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.lecnam.vgb2.MainActivity;
import net.lecnam.vgb2.R;
import net.lecnam.vgb2.Score.MySummurize;

public class InterroActivity2 extends AppCompatActivity {
    int scoregagne = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interro2);
    }
    @Override
    protected void onStop() {
        super.onStop();
        //todo clause if success pas de zone rouge
        MySummurize.getinstance().addScore(scoregagne);
    }


    public void backToMap(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}