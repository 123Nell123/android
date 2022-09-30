package net.lecnam.applitrace;
//https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  ,GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener  {

    //todo : trouvez la bonne valeur pour cet element


    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = "TAG";
    private GoogleMap mMap;
    Boolean locationPermissionGranted = false ;

    //position actuelle
    private Location lastKnownLocation;

    private CameraPosition cameraPosition;
    // The entry point to the Places API.
    private static final int DEFAULT_ZOOM = 15;
    // The entry point to the Places API.
    private PlacesClient placesClient;
    private final LatLng defaultLocation = new LatLng(R.string.lat_ptZoom, R.string.long_ptZoom);
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;
//pour garder la position si l'activité redémarre
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());

            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Sets up the options menu.
     * @param menu The options menu.
     * @return Boolean.
     */

   // devrait permettre de zoomer
    /* MapController getController()
    }
*/
   private void getLocationPermission() {
       /*
        * Request location permission, so that we can get the location of the
        * device. The result of the permission request is handled by a callback,
        * onRequestPermissionsResult.
        */
       if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
               android.Manifest.permission.ACCESS_FINE_LOCATION)
               == PackageManager.PERMISSION_GRANTED) {
           locationPermissionGranted = true;
       }
       else {
           ActivityCompat.requestPermissions(this,
                   new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                   PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
       }
   }


   //traitement du resultat de la permission

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //demande l'autorisation
        getLocationPermission();

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition  = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }




        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), getString(R.string.maps_api_key));

        placesClient = Places.createClient(this);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

       mMap = googleMap;
/*
        // Add a marker in Sydney and move the camera
        LatLng martinique = new LatLng(14.562699, -60.931602);
        LatLng point1 = new LatLng(15, -60);
        LatLng point2 = new LatLng(16, -60);
        mMap.addMarker(new MarkerOptions().position(martinique).title("Marker in StEsprit"));
        mMap.addMarker(new MarkerOptions().position(point1).title("point1"));
        mMap.addMarker(new MarkerOptions().position(point2).title("point2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(martinique));
        mMap.getMaxZoomLevel();
*/
        // localisation des evenements
        double lat_even1= Double.parseDouble(getResources().getString(R.string.lat_even1));
        double long_even1= Double.parseDouble(getResources().getString(R.string.long_even1));
        double lat_even2= Double.parseDouble(getResources().getString(R.string.lat_even2));
        double long_even2= Double.parseDouble(getResources().getString(R.string.long_even2));

        // localisation des monuments
        double lat_pt1= Double.parseDouble(getResources().getString(R.string.lat_pt1));
        double long_pt1= Double.parseDouble(getResources().getString(R.string.long_pt1));
        double lat_pt2= Double.parseDouble(getResources().getString(R.string.lat_pt2));
        double long_pt2= Double.parseDouble(getResources().getString(R.string.long_pt2));
        double lat_pt3= Double.parseDouble(getResources().getString(R.string.lat_pt3));
        double long_pt3= Double.parseDouble(getResources().getString(R.string.long_pt3));
        double lat_pt4= Double.parseDouble(getResources().getString(R.string.lat_pt4));
        double long_pt4= Double.parseDouble(getResources().getString(R.string.long_pt4));
        double lat_pt5= Double.parseDouble(getResources().getString(R.string.lat_pt5));
        double long_pt5= Double.parseDouble(getResources().getString(R.string.long_pt5));
        double lat_pt6= Double.parseDouble(getResources().getString(R.string.lat_pt6));
        double long_pt6= Double.parseDouble(getResources().getString(R.string.long_pt6));
        //pt de zoom
        double lat_ptZoom= Double.parseDouble(getResources().getString(R.string.lat_ptZoom));
        double long_ptZoom= Double.parseDouble(getResources().getString(R.string.long_ptZoom));

        //pt de perimetre de jeu
        double lat_per1= Double.parseDouble(getResources().getString(R.string.lat_per1));
        double long_per1= Double.parseDouble(getResources().getString(R.string.long_per1));
        double lat_per2= Double.parseDouble(getResources().getString(R.string.lat_per2));
        double long_per2= Double.parseDouble(getResources().getString(R.string.long_per2));
        double lat_per3= Double.parseDouble(getResources().getString(R.string.lat_per3));
        double long_per3= Double.parseDouble(getResources().getString(R.string.long_per3));
        double lat_per4= Double.parseDouble(getResources().getString(R.string.lat_per4));
        double long_per4= Double.parseDouble(getResources().getString(R.string.long_per4));
        double lat_per5= Double.parseDouble(getResources().getString(R.string.lat_per5));
        double long_per5= Double.parseDouble(getResources().getString(R.string.long_per5));


        // ... get a map.
        // Add a circle in St malo
        Circle even1 = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat_even1, long_even1))
                .radius(10)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));


        // Add a circle in t malo
        Circle even2 = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat_even2, long_even2))
                .radius(10)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));


// Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.

        LatLng pt1 = new LatLng(lat_pt1, long_pt1);
        LatLng pt2 = new LatLng(lat_pt2, long_pt2);
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        pt1,pt2,
                        new LatLng(lat_pt3, long_pt3),
                        new LatLng(lat_pt4, long_pt4),
                        new LatLng(lat_pt5, long_pt5),
                        new LatLng(lat_pt6, long_pt6)));

        polyline1.setTag("cheminPlusCourt");

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat_ptZoom, long_ptZoom), 4));

        // Set listeners for click events.
        mMap.setOnPolylineClickListener(this);
        mMap.setOnPolygonClickListener(this);


// Add polygons to indicate areas on the map.
        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(lat_per1, long_per1),
                        new LatLng(lat_per2, long_per2),
                        new LatLng(lat_per3, long_per3),
                        new LatLng(lat_per4, long_per4),
                        new LatLng(lat_per5, long_per5)));
// Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.setTag("alpha");


        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        mMap.addMarker(new MarkerOptions().position(pt1).title("Monument1"));
        mMap.addMarker(new MarkerOptions().position(pt1).title("Monument2"));

        // affichage d'une carte superposée
        float lat= Float.parseFloat(getResources().getString(R.string.lat_ptZoom));
        float lon= Float.parseFloat(getResources().getString(R.string.long_ptZoom));
        double lat1= Double.parseDouble(getResources().getString(R.string.lat_ptZoom));

        Log.i(TAG, "onMapReady: "+lat1);
        // Add a marker in Sydney and move the camera
        LatLng StServan = new LatLng(lat,lon);

        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922))
                .anchor(0, 1) // ancre
                .position(StServan, 600f, 650f)
                .transparency(0.5f) // 0 à 1
                .zIndex(3) // feuille de superposition ?
                .bearing(90); //bearing ==rotation
        //860m et 652m
        mMap.addGroundOverlay(newarkMap);


    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
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
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    //Si l'utilisateur a octroyé l'autorisation de géolocalisation, activez le calque et la commande "Ma position" sur la carte.
    // Dans le cas contraire, désactivez-les et définissez la position actuelle sur la valeur null :

    private void updateLocationUI() {
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
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    protected boolean isRouteDisplayed(){
       // Cette méthode permet de savoir si d'une manière ou d'une autre la vue qui affichera la carte
        // permettra de visualiser des informations de type itinéraire ou parcours.
        boolean resultat = true ;
        return resultat ;
    }
    protected boolean isLocationDisplayed(){
        boolean resultat = true ;
        return resultat ;
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    /**
     * Listens for clicks on a polyline.
     * @param polyline The polyline object that the user has clicked.
     */



    @Override
    public void onPolylineClick(Polyline polyline) {
     //   stylePolyline(polyline);

        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }


    // complement pour le trait de la polyline
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;

    /**
     * Styles the polyline, based on type.
     * @param polyline The polyline object that needs styling.
     */
    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "cheminPlusCourt":
                // Use a custom bitmap as the cap at the start of the line.
              //  polyline.setStartCap(new CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.arrow_background), 10));
                polyline.setStartCap(new RoundCap());
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }



}