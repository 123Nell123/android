package net.lecnam.applitrace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    /**
     * Handles a click on the menu option to get a place.
     * @param item The menu item to handle.
     * @return Boolean.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.infoHistorique) {
            //showHistorique();

        }
        else if (item.getItemId() == R.id.affichageHelp) {
            //showHelp();

        }

        else if (item.getItemId() == R.id.infoCarte) {
            //showHelp();
Intent intent = new Intent (this,MapsActivity.class);
startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}