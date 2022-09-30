package net.lecnam.vgb2.Debut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.lecnam.vgb2.MainActivity;
import net.lecnam.vgb2.R;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);




    }

    public void backToMap2(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}