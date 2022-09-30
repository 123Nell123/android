package net.lecnam.vgb2.Debut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.lecnam.vgb2.R;

public class DebutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debut);
    }




    public void Suite(View view) {

        Intent intent1 = new Intent(getApplicationContext(), PuzzleActivity.class);
        startActivity(intent1);

    }
}