package net.lecnam.vgb2;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;

import net.lecnam.vgb2.didactique.DidactiqueFragment;
import net.lecnam.vgb2.maps.MapsFragment;
import net.lecnam.vgb2.Score.MySummurize;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TAG" ;
    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //FOR FRAGMENTS
    private Fragment fragmentDidactique;
    //activiterealise

    Boolean isRealisedPiratage = false;
    Boolean isRealisedInterromari= false;
    Boolean isRealisedTourelle= false;
    Boolean isRealisedPhoto = false;
    Boolean isRealisedP2Vue = false;
    Boolean isRealisedInterro2= false;
    Boolean isRealisedBateau = false;

    public Boolean getRealisedInterromari() {
        return isRealisedInterromari;
    }

    public void setRealisedInterromari(Boolean realisedInterromari) {
        isRealisedInterromari = realisedInterromari;
    }

    public Boolean getRealisedPiratage() {
        return isRealisedPiratage;
    }

    public void setRealisedPiratage(Boolean realisedPiratage) {
        isRealisedPiratage = realisedPiratage;
    }

    public Boolean getRealisedTourelle() {
        return isRealisedTourelle;
    }

    public void setRealisedTourelle(Boolean realisedTourelle) {
        isRealisedTourelle = realisedTourelle;
    }

    public Boolean getRealisedPhoto() {
        return isRealisedPhoto;
    }

    public void setRealisedPhoto(Boolean realisedPhoto) {
        isRealisedPhoto = realisedPhoto;
    }
    public Boolean getRealisedP2Vue() {
        return isRealisedP2Vue;
    }

    public void setRealisedP2Vue(Boolean realisedP2Vue) {
        isRealisedP2Vue = realisedP2Vue;
    }

    public Boolean getRealisedInterro2() {
        return isRealisedInterro2;
    }

    public void setRealisedInterro2(Boolean realisedInterro2) {
        isRealisedInterro2 = realisedInterro2;
    }

    public Boolean getRealisedBateau() {
        return isRealisedBateau;
    }

    public void setRealisedBateau(Boolean realisedBateau) {
        isRealisedBateau = realisedBateau;
    }

    //FOR DATAS
    private static final int SUMMARIZE_didactique = 0;
     private static final int SUMMARIZE_inteeromari= 1;
    private static final int SUMMARIZE_tourelle = 2;
    private static final int SUMMARIZE_photo = 3;
    private static final int SUMMARIZE_ptVue= 4;
    private static final int SUMMARIZE_interrojaune = 5;
    private static final int SUMMARIZE_engimeBateau = 6;
    private static final int SUMMARIZE_score = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        // Show First Fragment
      //  this.showFirstFragment();

        //recupere les infos des activites qui lon lances
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Boolean valMari = extras.getBoolean("interroMari");
            if (valMari == true) {
                // do something with the data
                isRealisedInterromari= true;

            }
        }
// Get data via the key



        //fragement map dynamique   //todo verifier interet de mettre en statique ?
        Log.i(TAG, "onCreate: fragementmap");
        Fragment fragementmap = new MapsFragment();
        //OpenFragement
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.map,fragementmap);
        transaction.commit();


    }

    //garder en memoire si une enigme a deja ete faite
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("Piratage", isRealisedPiratage);
        outState.putBoolean("Interromari", isRealisedInterromari);
        outState.putBoolean("Tourelle", isRealisedTourelle);
        outState.putBoolean("Photo", isRealisedPhoto);
        outState.putBoolean("P2Vue", isRealisedP2Vue);
        outState.putBoolean("Interro2", isRealisedInterro2);
        outState.putBoolean("Bateau", isRealisedBateau);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        isRealisedPiratage = savedInstanceState.getBoolean("Piratage");
        isRealisedInterromari = savedInstanceState.getBoolean("Interromari");
        isRealisedTourelle = savedInstanceState.getBoolean("Tourelle");
        isRealisedPhoto = savedInstanceState.getBoolean("Photo");
        isRealisedP2Vue = savedInstanceState.getBoolean("P2Vue");
        isRealisedInterro2 = savedInstanceState.getBoolean("Interro2");
        isRealisedBateau = savedInstanceState.getBoolean("Bateau");


    }

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Show fragment after user clicked on a menu item
        switch (id){
            case R.id.activity_main_drawer_didactique :
                this.showSummary(SUMMARIZE_didactique);
               break;


            case R.id.enigme1_interroMari:
                Log.i(TAG, "onNavigationItemSelected: + R.id.enigme1_interroMari" +  R.id.enigme1_interroMari);
                this.showSummary(SUMMARIZE_inteeromari);
                break;

            case R.id.enigme2_tourelle:
                this.showSummary(SUMMARIZE_tourelle);
                break;
            case R.id.enigme3_photo:
                this.showSummary(SUMMARIZE_photo);
                break;
            case R.id.enigme4_ptVue:
                this.showSummary(SUMMARIZE_ptVue);
                break;
            case R.id.enigme5_interro2:
                this.showSummary(SUMMARIZE_interrojaune);
                break;
            case R.id.enigme6_bateau:
                this.showSummary(SUMMARIZE_engimeBateau);
                break;
            case R.id.score:
                this.showSummary(SUMMARIZE_score);
                break;

            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    // ---------------------
    // FRAGMENTS
    // ---------------------

    /*
    // Show first fragment when activity is created
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            // Show News Fragment
            this.showFragment(FRAGMENT_didactique);
            // Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
*/


    // Show fragment according an Identifier

    private void showSummary(int fragmentIdentifier){

        switch (fragmentIdentifier){
            case SUMMARIZE_didactique :
                this.showdidactiqueFragment();
                break;

            case SUMMARIZE_inteeromari:
                Log.i(TAG, "showSUMMARIZE: isRealisedInterromari" + isRealisedInterromari);
                if (isRealisedInterromari)
                {
                    Log.i(TAG, "showSUMMARIZE: avant showInterroMari() ");
                    this.showInterroMari();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"vous n'avez pas encore résolu cette énigme", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;

            case SUMMARIZE_tourelle:
                if (isRealisedTourelle)
                {this.showTourelle();}
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"vous n'avez pas encore résolu cette énigme", Toast.LENGTH_LONG);
                    toast.show();

                }
                break;
            case SUMMARIZE_photo:
                if (isRealisedPhoto)
                {this.showPhoto();}
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"vous n'avez pas encore résolu cette énigme", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;

            case SUMMARIZE_ptVue:
                if (isRealisedP2Vue)
                {this.showPtVue();}
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"vous n'avez pas encore résolu cette énigme", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;

            case SUMMARIZE_interrojaune:
                if (isRealisedInterromari)
                {this.showInterro2();}
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"vous n'avez pas encore résolu cette énigme", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case SUMMARIZE_engimeBateau:
                if (isRealisedInterromari)
                {this.showbateau();}
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"vous n'avez pas encore résolu cette énigme", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;



            case SUMMARIZE_score:
               int score =  MySummurize.getinstance().getScore();
                Toast toast = Toast.makeText(getApplicationContext(),"score"+score , Toast.LENGTH_LONG);
                toast.show();
                break;




            default:
                break;
        }
    }

    // ---

    // Create each fragment page and show it

    private void showdidactiqueFragment(){
        if (this.fragmentDidactique == null) this.fragmentDidactique = DidactiqueFragment.newInstance();
        this.startTransactionFragment(this.fragmentDidactique);
    }


    private void showInterroMari(){
       afficherDialog("mari");
    }

    private void showTourelle(){
        afficherDialog("tourelle");
    }



    private void showPhoto(){
        afficherDialog("photo");
    }
    private void showPtVue(){
        afficherDialog("pointDeVue");
    }
    private void showInterro2(){
        afficherDialog("Interro2");
    }
    private void showbateau(){
        afficherDialog("bateau");
    }

    ///*****

    public void afficherDialog(String monCompteRendu){

        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        List<String> reponses = MySummurize.getinstance().getListIndiceInterroMari();
        StringBuilder str = new StringBuilder();
        for (String reponse : reponses) {
            str.append(reponse);
            str.append(" ");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String title = "titre";
        String message = "message";

        switch (monCompteRendu){
            case "mari" :
                title = "compte rendu mari";
                message = str.toString();
                break;
            case "tourelle":
                title = "compte rendu tourelle";
                message = MySummurize.getinstance().getIndiceEngime2();
                break;

            case "photo":
                title = "compte rendu photo";
                message =  MySummurize.getinstance().getIndiceEngime3();
                break;

            case "pointDeVue":
                title = "compte rendu pointDeVue";
                message =  MySummurize.getinstance().getIndiceEngime4();
                break;

            case "interro2":
                title = "compte rendu interro2";
                message =  MySummurize.getinstance().getIndiceEngime5();
                break;



            case "bateau":
                title = "compte rendu bateau";
                message =  MySummurize.getinstance().getIndiceEngime6();
                break;

            default:
                break;
        }

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(title)
                .setTitle(message);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();

}

    // ---

    // Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
}