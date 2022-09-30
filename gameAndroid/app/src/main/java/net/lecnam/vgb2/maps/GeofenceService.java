package net.lecnam.vgb2.maps;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.model.LatLng;

import net.lecnam.vgb2.MainActivity;
import net.lecnam.vgb2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 *
 * helper methods.
 */
public class GeofenceService extends IntentService {

    //https://www.rlogical.com/blog/how-to-integrate-geofencing-in-an-android-app/


    private static final String TAG = "inService";

 String idGeofence="";


    //todo a supprimer ci besoin

    public GeofenceService() {
        super(TAG);
        Log.i(TAG, "GeofenceService: ");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Log.i(TAG, "onHandleIntent: ");



        if (!geofencingEvent.hasError()) {
            // Get the transition type.
            int transaction = geofencingEvent.getGeofenceTransition();

            // Test that the reported transition was of interest.
            /*

            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals("GEOFENCE_depart")) {
                Log.i(TAG, "You are inside GEOFENCE_depart (Geofence Location)");
            } else {
                Log.i(TAG, "You are outside GEOFENCE_depart(Geofence Location)");
            }
*/
//http://android-doc.github.io/training/location/geofencing.html


            if (transaction== Geofence.GEOFENCE_TRANSITION_ENTER ||
                    transaction == Geofence.GEOFENCE_TRANSITION_EXIT)

                   {

                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

                //todo check why get(0) .. faudra peut etre sortir en fonction de la position du triggeringGeofences
                Geofence geofence = triggeringGeofences.get(0);
                Log.i(TAG, "onHandleIntent: triggeringGeofences.get(0) " + triggeringGeofences.get(0).getRequestId());
                // Fetch Entering / Exiting Detail
                String geofenceTransitionDetails = getGeofenceTransitionDetails(transaction, triggeringGeofences);

                idGeofence = geofence.getRequestId();
                Log.i(TAG, "onHandleIntent geofenceTransitionDetails: " + geofenceTransitionDetails + " idGeofence  " + idGeofence);


                //todo essayer notification sound
                //    sendNotification(geofenceTransitionDetails);

                if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER) {


                    Log.i(TAG, "onHandleIntent: idGeofence  " + idGeofence);

                    //renseigner isAvailable


                    // ****gestion du message  ***
                    // Le message arrive via une intent
                    Bundle extras = intent.getExtras();
                    Messenger messager = (Messenger) extras.get("messager");

                    // Le service demande et obtient un message
                    Message msg = Message.obtain();

                    // Un Bundle contient les données à transmettre,
                    Bundle bundle = new Bundle();
                    bundle.putString("mId", idGeofence);
                    bundle.putString("mDetails", geofenceTransitionDetails);
                    msg.setData(bundle);

                    // Le messager repart avec son message, méthode send.
                    try {
                        messager.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
/////////////////////************************************************
                }

            }
        }
        else{
            Log.i(TAG, "onHandleIntent: error reception l. 71 ");
        }
    }


    //Create a detail message with Geofences received
    private String getGeofenceTransitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
// get the ID of each geofence triggered
        Log.i(TAG, "getGeofenceTransitionDetails: ");
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesList.add(geofence.getRequestId());
        }

        String status = null;
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
            status = "Entering ";
        else if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT)
            status = "Exiting ";

        return status + TextUtils.join(", ", triggeringGeofencesList);
    }



    private void sendNotification(String msg) {
        Log.i(TAG, "sendNotification: ");
// Intent to start the main Activity
        Intent notificationIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

// Creating and sending Notification
        long when = Calendar.getInstance().getTimeInMillis();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i(TAG, "sendNotification: getREADY notifChannel ");


// Configure the notification channel.
            String CHANNEL_ID = "myChannelID";
            CharSequence name = "monChannel1";
            String description = "maDescription";
            int importance = NotificationManager.IMPORTANCE_HIGH;



            NotificationChannel notifChannel = new NotificationChannel(CHANNEL_ID, msg, importance);
            notifChannel.setDescription(description);
            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.GREEN);
            notifChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notifChannel.enableVibration(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(notifChannel);
        }


// NotificationCompat.Builder   Allows easier control over all the flags, as well as help constructing the typical notification layouts.
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "CH_ID")  //todo verfifier ce channel
                .setTicker(msg)
                .setContentTitle(msg)
                .setOngoing(false)
                .setAutoCancel(true)
                .setWhen(when)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100}).setSmallIcon(R.mipmap.ic_launcher_round);

        notificationManager.notify((int) when, notifBuilder.build());
    }





}