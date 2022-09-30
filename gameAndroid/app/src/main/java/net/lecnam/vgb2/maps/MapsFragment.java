package net.lecnam.vgb2.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;

import net.lecnam.vgb2.Debut.DebutActivity;
import net.lecnam.vgb2.MainActivity;
import net.lecnam.vgb2.R;
import net.lecnam.vgb2.interroMariOffciel.InterroMariActivity;
import net.lecnam.vgb2.interrogatoireMari.MyInterroActivity;
import net.lecnam.vgb2.tourelle.TourelleActivity1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , GoogleMap.OnMarkerClickListener, LocationListener {

    private static final String TAG = "Maps";
    private Location lastKnownLocation;
    private CameraPosition cameraPosition;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    Boolean locationPermissionGranted = true;
    private static final int DEFAULT_ZOOM = 15;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GeofencingClient geofencingClient;
    private GeofencingRequest geofencingRequest;
    private PendingIntent pendingIntent;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1; //todo remplacaer ds le code
    public static final int REQUEST_CODE_FOR_PERMISSIONS = 1;
    private LatLng defaultLocation;
    private GoogleMap mMap;
View myView;

MainActivity mymainActivity; //todo s'assurer que l'on travaille sur la bonne instance de la MainActivity --> singleton ?

    ///carte
    float mZoom = 16;


    LatLng ptDep;
    LatLng interro_mari;
    LatLng etape_un;
    LatLng etape_deux;
    LatLng etape_trois;
    LatLng Envoie_SMS;
    LatLng etape_quatre;
    LatLng etape_cinq;

    
    //marker
    Marker mark_ptDep;
    Marker mark_interro_mari;
    Marker mark_etape_un;
    Marker mark_etape_deux;
    Marker mark_etape_trois;
    Marker mark_etape_quatre;
    Marker mark_etape_cinq;
    float opacite = 0.7f;
    float opacite1 = 1.0f ;
    int tagMark = 0;

    boolean isAvalaibledepart =   false ;
    boolean isAvalaible_int =   false  ;
    boolean isAvalaible1 =   false ;
    boolean isAvalaible2 =   false  ;
    boolean isAvalaible3 =  false ;
    boolean isAvalaible4 =    false  ;
    boolean isAvalaible_sms =  false  ;
    boolean isAvalaible5 =    false ;


    //geofence
    
   final String name_dep = "GEOFENCE_depart";
    final String name_int = "GEOFENCE_interro_mari";
     final String name_1= "GEOFENCE_etape_un";
    final String name_2= "GEOFENCE_etape_deux";
    final String name_3= "GEOFENCE_etape_trois";
    final  String name_sms = "GEOFENCE_Envoi_SMS";
    final  String name_4= "GEOFENCE_etape_quatre";
    final String name_5 = "GEOFENCE_etape_cinq";


    
    HashMap<String, LatLng> LOCATION_LIST;
    private GoogleApiClient googleApiClient;
    public static final float GEOFENCE_RADIUS_IN_METERS = 50;   ///*********************
    private MarkerOptions markerOptions;
    private Marker currentLocationMarker;
    LatLng latLng;
    boolean isMonitoring;
    List<String> nameGeofences;
    List<Geofence> geofences ;



    String contenuSnippet0 = "position non atteinte" ;
    String contenuSnippet1 = "position ok" ;

//fromm messenger
String myidGeofence;
    String mygeofenceTransitionDetails;


    //dialog
    Intent intent1 ;
    //todo save preferences !!!!!


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ligne 108");

        return inflater.inflate(R.layout.fragment_maps, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView = view;
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
            Log.i(TAG, "onViewCreated: ");
            recupHistoriquePreference();
        }


    }




    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
            //les points
            Log.i(TAG, "onMapReady: start");
            mMap = map;

            //test *** litemode  **************
        //    GoogleMapOptions options = new GoogleMapOptions().liteMode(true);

            makeLatLong(map);
            prepareListGeofence();
            mMap.getUiSettings().setMapToolbarEnabled(false); //remove navigation on icone
            map.moveCamera(CameraUpdateFactory.newLatLng(ptDep));

            map.setMinZoomPreference(mZoom);  //10: la ville ; 15: les rues
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            Log.i(TAG, "onMapReady: checkingpermission l.107");
            //***** permission*******


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED &&
                        getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    map.setMyLocationEnabled(true);

                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                    , Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_FOR_PERMISSIONS);
                }
            } else {
                buildGoogleApiClient();
                map.setMyLocationEnabled(true);
            }

            //*******

            Log.i(TAG, "onMapReady: permission checked l.173");
            // ** position
            // position de UI sur la carte
            updateLocationUI(); //todo verifier l'interet de ces lignes

            //****geofence***********
           // https://www.rlogical.com/blog/how-to-integrate-geofencing-in-an-android-app/

            geofencingClient = LocationServices.getGeofencingClient(getContext());



            Log.i(TAG, "onMapReady: end");
        }

    };

//todo on location change à implementer ??
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged: l.198" +"A RENSEIGNERR ????");
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Google Api Client Connected");
        isMonitoring = true;
        startGeofencing();
        startLocationMonitor();

        Log.i(TAG, "onConnected: ");



    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        isMonitoring = false;
        Log.e(TAG, "Connection Failed:" + connectionResult.getErrorMessage());
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());

            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
            Log.i(TAG, "onSaveInstanceState: ");



        }
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        int response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        if (response != ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play Service Not Available");
            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), response, 1).show();
        } else {
            Log.d(TAG, "Google play service available");
        }
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        //si on est sur le marqueur et la position est available
        //on appellra un intent
        //sinon on affiche vous n'etes au bon endroit

boolean retour ;


            if (marker.equals(mark_ptDep) && (isAvalaibledepart == true))
            {
            mark_ptDep.setAlpha(opacite1);
                Toast.makeText(getContext(), "marker mark_ptDep", Toast.LENGTH_LONG).show();

             dialogDebut(myView );



                //todo launch intent
                retour =  true;
            } else if (marker.equals(mark_interro_mari) && (isAvalaible_int == true)) {

                Toast.makeText(getContext(), "mark_interro_mari", Toast.LENGTH_LONG).show();

                //todo launch intent
                mark_interro_mari.setAlpha(opacite1);
                dialogInterro(myView );


                retour =  true;

            } else if (marker.equals(mark_etape_un) && (isAvalaible1 == true) && (isAvalaible_int == true)) {

               Toast.makeText(getContext(), "mark_etape1", Toast.LENGTH_LONG).show();

                mark_etape_un.setSnippet(contenuSnippet1);
                mark_etape_un.setAlpha(opacite1);


               dialogTourelle(myView );
                  retour =  true;
             }


            else if (marker.equals(mark_etape_deux) && (isAvalaible2 == true) && (isAvalaible1 == true) && (isAvalaible_int == true)) {
                Toast.makeText(getContext(), "marker clike 2", Toast.LENGTH_LONG).show();

                //todo launch intent
                mark_etape_deux.setSnippet(contenuSnippet1);
                mark_etape_deux.setAlpha(opacite1);


              dialogPhoto(myView );
                retour =  true;
            }

            else if (marker.equals(mark_etape_trois) && (isAvalaible3 == true) && (isAvalaible2 == true) && (isAvalaible1 == true) && (isAvalaible_int == true)) {
                Toast.makeText(getContext(), "marker clike 3", Toast.LENGTH_LONG).show();

                //todo launch intent
                mark_etape_trois.setSnippet(contenuSnippet1);
                mark_etape_trois.setAlpha(opacite1);

            dialogPtVue(myView );

                retour =  true;
            }

            else if (marker.equals(mark_etape_quatre) && (isAvalaible4 == true) && (isAvalaible3 == true) && (isAvalaible2 == true) && (isAvalaible1 == true) && (isAvalaible_int == true)) {
                Toast.makeText(getContext(), "marker clike 4", Toast.LENGTH_LONG).show();

                mark_etape_quatre.setSnippet(contenuSnippet1);
                mark_etape_quatre.setAlpha(opacite1);

              dialogInterro2(myView );
                retour =  true;
            }
            else if (marker.equals(mark_etape_cinq) && (isAvalaible5 == true) && (isAvalaible4 == true) && (isAvalaible3 == true) && (isAvalaible2 == true) && (isAvalaible1 == true) && (isAvalaible_int == true)) {
                Toast.makeText(getContext(), "marker clike 5", Toast.LENGTH_LONG).show();

                mark_etape_cinq.setSnippet(contenuSnippet1);
                mark_etape_cinq.setAlpha(opacite1);
                //todo launch intent
            dialogBateau(myView );

                retour =  true;
            }


               else {
                   retour = false;
                Toast.makeText(getContext(), "assurez vous  devez  sur place et d' avoir suivi le parcours", Toast.LENGTH_LONG).show();
               }

return retour;

    }



    public void dialogInterro(View view) {


        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                intent1 =  new Intent(getContext(), MyInterroActivity.class );
                //todo sera remplace InterroMariActivity dans la version finale
                startActivity(intent1);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });






// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }


    public void dialogDebut(View view) {


        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            //    intent1 =  new Intent(getContext(), DebutActivity.class );
             //   startActivity(intent1);
                Toast.makeText(getContext(),"Il n'y a rien d'intéressant ici.. ",Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });






// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }


    public void dialogTourelle(View view) {


        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

          intent1 =  new Intent(getContext(), TourelleActivity1.class );
                startActivity(intent1);



            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });






// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }


    public void dialogPhoto(View view) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

               Toast.makeText(getContext(),"activite photo pas implemente pour le moment",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });






// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }



    public void dialogPtVue(View view) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(getContext(),"activite ptVue pas implemente pour le moment",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });






// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }



    public void dialogInterro2(View view) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(getContext(),"activite interro2 pas implemente pour le moment",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });






// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }



    public void dialogBateau(View view) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(getContext(),"activite bateau pas implemente pour le moment",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });






// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }




    private void getDeviceLocation() {
        //https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial?hl=fr
            /*
             * Get the best and most recent location of the device, which may be null in rare
             * cases when a location is not available.
             * //place la camera sur la derniere position connue
             */
            try {
                if (locationPermissionGranted) {
                    // identifiez la dernière position connue de l'appareil

                    Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                    Log.i(TAG, "getDeviceLocation: ");

                    locationResult.addOnCompleteListener(this.getActivity(), task -> {

                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    });
                }
            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage(), e);
            }
        Log.i(TAG, "getDeviceLocation: ");
        }
        //Si l'utilisateur a octroyé l'autorisation de géolocalisation, activez le calque et la commande "Ma position" sur la carte.
        // Dans le cas contraire, désactivez-les et définissez la position actuelle sur la valeur null :

        private void updateLocationUI () {
            if (mMap == null) {
                return;
            }
            try {
                if (locationPermissionGranted) {
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    mMap.setMyLocationEnabled(false);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    lastKnownLocation = null;
                    getLocationPermission();
                }
            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage());
            }
            Log.i(TAG, "updateLocationUI: ");

        }

        private void getLocationPermission () {
            /*
             * Request location permission, so that we can get the location of the
             * device. The result of the permission request is handled by a callback,
             * onRequestPermissionsResult.
             */
            //todo remplacer par code  cf lien ci dessous
            // https://webdevdesigner.com/q/add-google-maps-api-v2-in-a-fragment-31913/
            Log.i(TAG, "getLocationPermission: ligne 320");
            if (ContextCompat.checkSelfPermission(this.getContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            Log.i(TAG, "getLocationPermission:  ligne306");
        }

        //** jusqu'ici fin du to do

     public void prepareListGeofence(){
    LOCATION_LIST = new HashMap<String, LatLng>();

   
    LOCATION_LIST.put(name_dep, ptDep );

    LOCATION_LIST.put(name_int, interro_mari);
    LOCATION_LIST.put(name_1, etape_un);
    LOCATION_LIST.put(name_2, etape_deux);

    LOCATION_LIST.put(name_3, etape_trois);
    LOCATION_LIST.put(name_sms, Envoie_SMS);
    LOCATION_LIST.put(name_4, etape_quatre);
    LOCATION_LIST.put(name_5, etape_cinq);

         Log.i(TAG, "prepareListGeofence:   ligne 304 ");

}

    public void makeLatLong(GoogleMap map){

    ptDep = new LatLng(PointsJeu.PtdepartLat, PointsJeu.PtdepartLlongi);
    interro_mari = new LatLng(PointsJeu.Interrogatoire_mari_lat, PointsJeu.Interrogatoire_mari_long);
    etape_un = new LatLng(PointsJeu.etape_un_lat, PointsJeu.etape_un_long);
    etape_deux = new LatLng(PointsJeu.etape_deux_lat, PointsJeu.etape_deux_long);
    etape_trois = new LatLng(PointsJeu.etape_trois_lat, PointsJeu.etape_trois_long);
    Envoie_SMS = new LatLng(PointsJeu.Envoie_SMS_lat, PointsJeu.Envoie_SMS_long);
    etape_quatre = new LatLng(PointsJeu.etape_quatre_lat, PointsJeu.etape_quatre_long);
    etape_cinq = new LatLng(PointsJeu.etape_cinq_lat, PointsJeu.etape_cinq_long);

    defaultLocation = ptDep;
    //attache le listener a un marqueur
        map.setOnMarkerClickListener(this);


       mark_ptDep  = map.addMarker(new MarkerOptions()
            .position(ptDep)
            .alpha(opacite)
            .title("depart")
               .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

    // .icon(BitmapDescriptorFactory.fromResource(R.mipmap.button_play)));

      mark_interro_mari   = map.addMarker(new MarkerOptions()
            .position(interro_mari)
            .alpha(opacite)
              .snippet(contenuSnippet0)
              .title(name_int)
              .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mark_interro_mari.setTag(tagMark);

        //todo gestion des tag pour savoir ci ce marker a deja ete clique

                mark_etape_un = map.addMarker(new MarkerOptions()
            .position(etape_un)
            .alpha(opacite)
          .snippet(contenuSnippet0)
           .title(name_1)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mark_etape_un.setTag(tagMark);

                mark_etape_deux = map.addMarker(new MarkerOptions()
            .position(etape_deux)
            .alpha(opacite)
             .snippet(contenuSnippet0)
             .title(name_2)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mark_etape_deux.setTag(tagMark);

   mark_etape_trois = map.addMarker(new MarkerOptions()
            .position(etape_trois)
            .alpha(opacite)
           .snippet(contenuSnippet0)
           .title(name_3)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mark_etape_trois.setTag(tagMark);
     
        mark_etape_quatre = map.addMarker(new MarkerOptions()
            .position(etape_quatre)
            .alpha(opacite)
             .snippet(contenuSnippet0)
                .title(name_4)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mark_etape_quatre.setTag(tagMark);

    mark_etape_cinq = map.addMarker(new MarkerOptions()
            .position(etape_cinq)
            .alpha(opacite)
            .snippet(contenuSnippet0)
            .title(name_5)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mark_etape_cinq.setTag(tagMark);


//polyline
    Polyline polyline1 = map.addPolyline(new PolylineOptions()
            .clickable(false)
            .color(Color.GREEN)
            .add(ptDep, interro_mari, etape_un, etape_deux, etape_trois, etape_quatre, etape_cinq));

    polyline1.setWidth(15);

    List<PatternItem> pattern = Arrays.asList(
            new Dot(), new Gap(20), new Dash(30), new Gap(20));
    polyline1.setPattern(pattern);
        Log.i(TAG, "makeLatLong: ligne395");
}


   /* Used for receiving notifications from the LocationManager when the location has changed.
    These methods are called if the LocationListener has been registered with the location manager service
    using the LocationManager#requestLocationUpdates(String, long, float, LocationListener) method.
    */

    public void startLocationMonitor() {
        Log.i(TAG, "Start Location monitor");
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(2 *1000)   //todo remettre 2000
                .setFastestInterval(1 *1000) //todo remettre 10000
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Log.i(TAG, "startLocationMonitor: ligne 883 ");
        // Get the current location of the device and set the position of the map.
      getDeviceLocation(); //todo verifier l'interet de ces lignes

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {


                    if (currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }
                    markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                    markerOptions.title("Current Location");

                    currentLocationMarker = mMap.addMarker(markerOptions);
                    Log.i(TAG, "Location Change Lat Lng " + location.getLatitude() + " " + location.getLongitude());
                }
            });
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        }

        }


    private void startGeofencing() {
        Log.i(TAG, "Start geofencing monitoring call");
        pendingIntent = getGeofencePendingIntent();


        geofencingRequest = new GeofencingRequest.Builder()
                .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
                .addGeofences(getGeofence())
                .build();



        if (!googleApiClient.isConnected()) {
            Log.i(TAG, "Google API client not connected");
        } else {
            try {
                LocationServices.GeofencingApi.addGeofences(googleApiClient, geofencingRequest, pendingIntent).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Successfully Geofencing Connected");
                        } else {
                            Log.i(TAG, "Failed to add Geofencing " + status.getStatus());
                        }
                    }
                });
            } catch (SecurityException e) {
                Log.d(TAG, e.getMessage());
            }
        }
        Log.i(TAG, "startGeofencing: ");
       isMonitoring  = true;
    }


    @NonNull
    private List<Geofence> getGeofence() {

        Geofence myGeofence ;
        geofences = new ArrayList<Geofence>();
      nameGeofences = new ArrayList<String>();


        Log.i(TAG, "getGeofence: ajout des geofences ligne 470");



// parcourir la hasmap et mettre les noms ds une liste
        for (Map.Entry mapentry : LOCATION_LIST.entrySet()) {
            nameGeofences.add(mapentry.getKey().toString());
        }


//parcourir la liste de noms et creer des cercles
        LatLng latLng_init;
        for (String names:  nameGeofences) {

            latLng_init  = LOCATION_LIST.get(names)  ;

  // ajouter des  Circle circle;
    mMap.addCircle(new CircleOptions().center(latLng_init)
            .radius(GEOFENCE_RADIUS_IN_METERS).strokeColor(Color.RED).strokeWidth(10f));

    myGeofence = new Geofence.Builder()
            .setRequestId(names)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setCircularRegion(latLng_init.latitude, latLng_init.longitude, GEOFENCE_RADIUS_IN_METERS)
            .setNotificationResponsiveness(1000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT).build();

    geofences.add(myGeofence );
            latLng_init = null;
}

        return geofences ;
    }

   // Start Intent service in background

    private PendingIntent getGeofencePendingIntent() {
        if (pendingIntent != null) {
            Log.i(TAG, "getGeofencePendingIntent: ");
            // Reuse the PendingIntent if we already have it.
            return pendingIntent;
        }

        Intent intent = new Intent(getContext(), GeofenceService.class);
        Messenger messenger = new Messenger(new ResponseHandler());
        intent.putExtra("messager",messenger);



        Log.i(TAG, "getGeofencePendingIntent:  creation de pendingIntent l 527");
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(getContext(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    protected synchronized void buildGoogleApiClient() {
        if (googleApiClient != null) {
            googleApiClient = null;
        }
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    public void recupHistoriquePreference(){
        SharedPreferences sharedPref   = getActivity().getSharedPreferences("net.lecnam.vbg2.balise",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        boolean defaultValue = false;


        isAvalaibledepart  = sharedPref.getBoolean(name_dep ,defaultValue);
        isAvalaible_int  = sharedPref.getBoolean(name_int ,defaultValue);
        isAvalaible1  = sharedPref.getBoolean(name_1 ,defaultValue);
        isAvalaible2  = sharedPref.getBoolean(name_2 ,defaultValue);
        isAvalaible3  = sharedPref.getBoolean(name_3 ,defaultValue);
        isAvalaible4  = sharedPref.getBoolean(name_4 ,defaultValue);
        isAvalaible_sms  = sharedPref.getBoolean(name_sms ,defaultValue);
        isAvalaible5  = sharedPref.getBoolean(name_5 ,defaultValue);




    }

    public void creeHistorique(String name, boolean baliseAvailable ){
        SharedPreferences sharedPref   = getActivity().getSharedPreferences("net.lecnam.vbg2.balise",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(name,baliseAvailable);

        editor.apply();

    }


    //https://qastack.fr/programming/2463175/how-to-have-android-service-communicate-with-activity
    //https://developer.android.com/reference/android/app/Service.html#RemoteMessengerServiceSample



    class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Bundle extras = message.getData();
            if (extras != null) {

             
                myidGeofence = extras.getString("mId");
                mygeofenceTransitionDetails = extras.getString("mDetails");
                Log.i(TAG, "handleMessage:  l. 788"+myidGeofence);


//todo verifier que l'instance est appelle au bon moment cf valeur de myidGeofence
                switch (myidGeofence)
                {
                    case name_dep :
                        Log.i(TAG, "handleMessage: ");
                        isAvalaibledepart = true;
                       creeHistorique( myidGeofence, isAvalaibledepart);
                        break;

                    case name_int :
                        Log.i(TAG, "handleMessage: ");
                        isAvalaible_int = true ;
                        creeHistorique( myidGeofence, isAvalaible_int);
                        break;

                    case name_1 :
                        Log.i(TAG, "handleMessage: ");
                        isAvalaible1 = true ;
                        creeHistorique( myidGeofence, isAvalaible1);
                         break;

                    case name_2:
                        Log.i(TAG, "handleMessage: ");
                        isAvalaible2 = true ;
                        creeHistorique(myidGeofence , isAvalaible2);
                        break;
                    case name_3 :
                        Log.i(TAG, "handleMessage: ");
                        isAvalaible3 = true ;
                        creeHistorique( myidGeofence, isAvalaible3);
                        break;

                    case name_sms :
                        Log.i(TAG, "handleMessage: ");
                        isAvalaible_sms = true ;
                        creeHistorique( myidGeofence, isAvalaible_sms);
                        break;

                    case name_4 :
                        Log.i(TAG, "handleMessage: ");
                        isAvalaible4 = true ;
                        creeHistorique( myidGeofence, isAvalaible4);
                        break;

                    case name_5 :
                        Log.i(TAG, "handleMessage: ");
                        isAvalaible5 = true ;
                        creeHistorique(myidGeofence,  isAvalaible5);
                        break;

                }

                Toast.makeText(getActivity().getApplicationContext(),  "vous etes dans la zone: " +  myidGeofence  ,
                        Toast.LENGTH_SHORT).show();
                Log.i(TAG, "handleMessage:  myidGeofence l.691"+ "mygeofenceTransitionDetails : " + mygeofenceTransitionDetails + "myidGeofence : " + myidGeofence);
            } else {
                Log.i(TAG, "handleMessage: pas d'extra");
            }
        };

    }

}