package net.lecnam.vgb2.ptVue;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.lecnam.vgb2.MainActivity;
import net.lecnam.vgb2.R;
import net.lecnam.vgb2.Score.MySummurize;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;


public class GyroscopeFragment extends Fragment implements View.OnClickListener, SensorEventListener {
    Button vers2ndFragment;
    Button lancementCalcul;
    SensorManager sensorManager;
    Sensor gyroscoper;
    Sensor accelerometer;

    float[] mGravityValues = null;
    float[] mGyroscopeValues = null;
    float valueGyro ;
    String TAG="TAG";
    ProgressBar bar;
    Handler handler;
    int scoregagne = 15;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gyroscope, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lancementCalcul = view.findViewById(R.id.lancementMesure);
        lancementCalcul.setOnClickListener(this::onClick);
        vers2ndFragment = view.findViewById(R.id.button_first);
        vers2ndFragment.setOnClickListener(this::onClick2);
        vers2ndFragment.setEnabled(false);
        bar = view.findViewById(R.id.progressBar);

        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
///******************************
        accelerometer  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
//Un accéléromètre est disponible
            sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
//Aucun accéléromètre de disponible
        }
//////


        gyroscoper  = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (accelerometer != null) {

            sensorManager.registerListener(this,gyroscoper,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
//Aucun accéléromètre de disponible
        }
///*** definition d'un hanlder ***
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int progress = msg.getData().getInt("MyKey");
                bar.setProgress(progress);

                if (bar.getProgress()>=19) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "zone scannée", Toast.LENGTH_LONG);
                    toast.show();
                    vers2ndFragment.setEnabled(true);
                }
            }


        };

    }

    @Override
    public void onStop() {
        super.onStop();

        MySummurize.getinstance().addScore(scoregagne);
    }


    @Override
    public void onClick(View view) {


        Thread threadAnalyse = new Thread(new Runnable() {
            int time = 0;
            @Override
            public void run() {
                // recupere la valeur de la mesure
                //valueGyro
                try {
                    while(time<20) {
                        Thread.sleep(250);
                        time++;
                        float absmaxScan = Math.abs(valueGyro);
                        if ( absmaxScan> 0.2 & absmaxScan < 3 ) {

                            Message msg = handler.obtainMessage();  //Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putInt("MyKey",time);
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                            Log.i(TAG, "run: " + "time " + time + "valueGyro " + absmaxScan );


                        }

                        else
                        {      Log.i(TAG, "echec " + "time" + time + "valueGyro" + absmaxScan  +"/*******************************");
                            //        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "scanne trop rapide", Toast.LENGTH_LONG);
                            //         toast.show();


                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadAnalyse.start();

    }

    public void onClick2(View view) {
       Intent intent =   new Intent(getActivity(), MainActivity.class);
      // todo ajouter le score
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // Création des tableaux de float vide.
        float[] R = new float[9];
        float[] I = new float[9];
        List valeurGyroscope = new ArrayList();

        if (sensorEvent.sensor == accelerometer) {
            float g1 = 9.80665f;
            // mGravityValues = new float[4];
            mGravityValues = sensorEvent.values.clone();
            //  Toast toast = Toast.makeText(getActivity().getApplicationContext(), "accelerometer declenched", Toast.LENGTH_LONG);
            //    toast.show();
        } else if (sensorEvent.sensor == gyroscoper) {

            mGyroscopeValues = sensorEvent.values.clone();
            valueGyro = sensorEvent.values[1];

          /*
            Thread threadMesure = new Thread(new Runnable() {
                int time = 0;
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        time++;
                        valueGyro = sensorEvent.values[1];

                        if (valueGyro > 0.2 & valueGyro < 1 ) {
                            Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),String.valueOf(time)+ " "+  String.valueOf(valueGyro), Toast.LENGTH_LONG);
                            toast1.show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadMesure.start();
*/

            // afficheData();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void afficheData(){

        //thread qui recupere les valeurs du gyroscope tout les
        // 1 tour = 1s

        float maxScan=0;
        float[] scann = new float[9];
        for (int i = 0;i< 10; i++)
        {

            scann[i] = valueGyro;
            maxScan= scann[i];

        }

        for (int i = 0;i< 10; i++)
        {
            maxScan= scann[0];
            if (scann[i]> maxScan)
            { maxScan= scann[i];}
        }

        float absmaxScan = Math.abs(maxScan);
        if (absmaxScan > 0.2 & absmaxScan < 2) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "scanne trop lent", Toast.LENGTH_LONG);
            toast.show();
        }

        else if (absmaxScan > 2 & absmaxScan < 2.5) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "zone scannée", Toast.LENGTH_LONG);
            toast.show();
            vers2ndFragment.setEnabled(true);

        }
        else  {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "scanne trop rapide", Toast.LENGTH_LONG);
            toast.show();


        }
    }
}