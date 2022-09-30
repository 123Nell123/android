package net.lecnam.vgb2.interroMariOffciel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.lecnam.vgb2.R;
import net.lecnam.vgb2.interrogatoireMari.questions.Interrogation;
import net.lecnam.vgb2.interrogatoireMari.questions.MyKeyValueQuestion;
import net.lecnam.vgb2.interrogatoireMari.reponses.MyKeyValueReponse;
import net.lecnam.vgb2.Score.MySummurize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InterroMariFragment extends Fragment {

    private static final String TAG = "interro";
    MyKeyValueReponse myKeyValueReponse;
    MyKeyValueQuestion myKeyValueQuestion;
    String CodeQuestion;
    Boolean debloqOlivier= false;
    Boolean debloqBateau= false;
    int scoregagne = 5;
    int niveauEnerv = 0;
//todo jauge si pb

    public String getCodeQuestion() {
        return CodeQuestion;
    }

    public void setCodeQuestion(String codeQuestion) {
        CodeQuestion = codeQuestion;
    }

    Map<String,String> myhasmapQuestion;
    Map<String,String> myhasmapReponse;



    public int getNiveauEnerv() {
        return niveauEnerv;
    }

    public void setNiveauEnerv(int niveauEnerv) {
        this.niveauEnerv = niveauEnerv;
    }

    public static String getTAG() {
        return TAG;
    }

    public Boolean getDebloqOlivier() {
        return debloqOlivier;
    }

    public void setDebloqOlivier(Boolean debloqOlivier) {
        this.debloqOlivier = debloqOlivier;
    }

    public Boolean getDebloqBateau() {
        return debloqBateau;
    }

    public void setDebloqBateau(Boolean debloqBateau) {
        this.debloqBateau = debloqBateau;
    }




    public List<Interrogation> getListInterrogation() {
        return listInterrogation;
    }

    List<Interrogation> listInterrogation;
    Interrogation interrogation;



    public Map<String, String> getMyhasmapQuestion() {
        return myhasmapQuestion;
    }

    public Map<String, String> getMyhasmapReponse() {
        return myhasmapReponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intero_mari, container, false);
    }

    @Override
   public void onStart() {
        super.onStart();
    Log.i(TAG, "onStart: ");


    Thread thread1 = new Thread( new Runnable(){

        @Override
        public void run() {
            myhasmapQuestion = new HashMap<String,String>();
            myKeyValueQuestion = new MyKeyValueQuestion();
            myhasmapQuestion =   myKeyValueQuestion.creationMappingQuestion();



            myhasmapReponse = new  HashMap<String,String>();
            myKeyValueReponse= new MyKeyValueReponse();
            myhasmapReponse =   myKeyValueReponse.creationMappingReponse();
            Log.i(TAG, "run: thread1");
        }
    });


    Thread thread2 = new Thread( new Runnable(){
        @Override
        public void run() {
            listInterrogation=   ReadFile();
            Log.i(TAG, "run: thread2");
        }
    });

        thread1.start();
        thread2.start();

}

    public List ReadFile(){

        InputStream is = getResources().openRawResource(R.raw.interrogatoire);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;
        List mylist = new ArrayList();
        while (true) {
            try {
                if (!((line = buffer.readLine()) != null)) break;

                //  Log.i(TAG, line);
                String[] str = line.split(";", 4); // defining 3 columns with null or blank field //values acceptance
                String question = str[0].toString();
                String niveau_apparent = str[1].toString();
                String niveau_reel = str[2].toString();
                String reponse = str[3].toString();
                interrogation = new Interrogation(question, niveau_apparent , niveau_reel, reponse);
                //  Log.i(TAG, "ReadFile: "+question +" " + niveau_apparent+" "+ reponse);
                mylist.add(interrogation);
                //   Log.i(TAG, "ReadFile: taille du jeu de donnée"+listInterrogation.size());
                Log.i(TAG, "ReadFile: donnée lues depuis csv ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mylist;

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
        // si pas de zone rouge 5 pt de gagnées
        if (niveauEnerv <= 8 )
        {
            MySummurize.getinstance().addScore(scoregagne);
        }
    }

}