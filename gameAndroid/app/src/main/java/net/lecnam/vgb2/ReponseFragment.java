package net.lecnam.vgb2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import net.lecnam.vgb2.MainActivity;
import net.lecnam.vgb2.R;
import net.lecnam.vgb2.Score.MySummurize;
import net.lecnam.vgb2.interrogatoireMari.questions.Interrogation;
import net.lecnam.vgb2.interrogatoireMari.MyInterroActivity;
import net.lecnam.vgb2.interrogatoireMari.reponses.MyKeyValueReponse;
import net.lecnam.vgb2.ReponseFragmentArgs;
import net.lecnam.vgb2.ReponseFragmentDirections;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ReponseFragment extends Fragment {

    private static final String TAG = "interro";
    MyInterroActivity mainActivity;
    TextView reponse;
    String laReponseA_Affiche="" ;
    String laReponseCode="" ;
    String laQuestion="";
    ProgressBar progressbarVerti;

    int resultatsympathie = 10; //todo a rendre dynamique

    //pour deblocage
    boolean debOlivier=false;
    boolean debBateau=false;

    //valeur renvoyer
    int niveauEnerv;


    /// pour debuugage
    Switch switchPourDebug2;
    TextView rappelQuestion;
    TextView rappelValeurJauge;
    TextView reponseEnervement;
    TextView textcommentaireErnevdebbug;
    TextView textcommentaireJaugedebbug;
    ///////
    int valeurJauge=0;
    //bouton
    Button button_second;
    Button button_sortir;

//interrogation


    List<Interrogation> listInterrogation;
    Interrogation interrogation;
    Interrogation interrogation1;
    MyKeyValueReponse myKeyValueReponse;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment

        interrogation1  = new Interrogation();
        return inflater.inflate(R.layout.fragment_reponse, container, false);

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MyInterroActivity) getActivity();

        button_second = (Button) view.findViewById(R.id.button_second);
        button_sortir = (Button) view.findViewById(R.id.button_sortir);
        reponse= (TextView) view.findViewById( R.id.Reponse);
        reponseEnervement= (TextView) view.findViewById( R.id.ReponseEnervement);
        progressbarVerti = (ProgressBar) view.findViewById( R.id.progressBarHorizon);
        myKeyValueReponse = new MyKeyValueReponse();

        //** pour debug **
        rappelQuestion = (TextView) view.findViewById( R.id.rappelvaleurquestion);
        rappelValeurJauge = view.findViewById( R.id.rappelValeurJauge);
        textcommentaireErnevdebbug=(TextView) view.findViewById( R.id.textcommentaireErnevdebbug);
        textcommentaireJaugedebbug=(TextView) view.findViewById( R.id.textcommentaireJaugedebbug);




//****************************reccuperation des valeurs du premier fragment  ici ****************************************************************
        if (getArguments()!=null) {
            ReponseFragmentArgs args2 = ReponseFragmentArgs.fromBundle(getArguments());

            //  laQuestion= args2.getKeyQuestion();
            laQuestion= mainActivity.getCodeQuestion();
            Log.i(TAG, "onViewCreated:mainActivity.getCodeQuestion() " + mainActivity.getCodeQuestion());
            rappelQuestion.setText(laQuestion);
            valeurJauge = args2.getKeyJauge();
            rappelValeurJauge.setText(String.valueOf(valeurJauge));

            //todo garder en memoire la valeur de l'enervement

        }







        //////////////////////////////////


        listInterrogation = new ArrayList<Interrogation>();
        listInterrogation=   mainActivity.getListInterrogation();

        Log.i(TAG, "onViewCreated listeinterrogation: "+listInterrogation.size());


//todo recuperer une interrogation non ull

        int j =0;
        ListIterator<Interrogation> listItr = listInterrogation.listIterator();
        String valeurJaugeString = String.valueOf(valeurJauge);
        Log.i(TAG, "onViewCreated: valeurJaugeString"+valeurJaugeString);
        Log.i(TAG, "onViewCreated laQuestion: "+laQuestion);
        while(listItr.hasNext())
        {
            Interrogation interro = listItr.next();
            j++;
            if ((interro.getQuestion().equals(laQuestion)) & (interro.getNiveau_apparent().equals(valeurJaugeString)) )
            {interrogation1 = interro ; }
        }



        // getNiveau_Reel_question(laQuestion,);
/*
        String valeurJaugeString = String.valueOf(valeurJauge);
        for (Interrogation interro : listInterrogation)
            if ((interro.getQuestion().contentEquals(laQuestion)) & (interro.getNiveau_apparent().contentEquals(valeurJaugeString)) )
            {interrogation1 = interro ; }
*/



        //

        if ( interrogation1 != null)
        { Log.i(TAG, "onCreate: interrogation1 "+ interrogation1.getReponseGlobale()+""+interrogation1.getQuestion());

        }
        else
        {
            Log.i(TAG, "onCreate: la requete est nulle ");
        }
        laReponseCode = interrogation1.getReponseGlobale();
        Log.i(TAG, "onViewCreated laReponseCode: "+ laReponseCode);
        Log.i(TAG, "onViewCreated: interrogation1.getReponseGlobale()"+ interrogation1.getReponseGlobale());
        switch (laReponseCode) {
            case "RC1":
                if (niveauEnerv<=4)
                { laReponseCode = "RC1B" ;}
                else  { laReponseCode= "RC1M" ;}
                break;
            case "RC2":
                if (niveauEnerv<=4)
                { laReponseCode = "RC2B" ;}
                else  { laReponseCode = "RC2M" ;}
                break;
            case "RD1":
                if (niveauEnerv<=4)
                { laReponseCode = "RD1B" ;}
                else  { laReponseCode = "RD1M" ;}
                break;
            case "RD2":
                if (niveauEnerv<=4)
                { laReponseCode = "RD2B" ;}
                else  { laReponseCode = "RD2M" ;}
                break;
            default:
                laReponseCode= laReponseCode;
        }

        laReponseA_Affiche =  MappingReponsechoisie(laReponseCode);
        //recuperation de la liste de reponse sauve et ajout de la reponse actuelle
        mainActivity.getReponseSaved().add(laReponseA_Affiche);
        reponse.setText(laReponseA_Affiche);

        reponseEnervement.setText(interrogation1.getNiveau_reel());


        niveauEnerv=  mainActivity.getNiveauEnerv() + Integer.parseInt(interrogation1.getNiveau_reel());
        mainActivity.setNiveauEnerv(niveauEnerv);

        ///////////
        if  (niveauEnerv < 10)
        {
            progressbarVerti.setProgress(niveauEnerv);
            changeColorBarprogression(progressbarVerti,niveauEnerv);
        }
        else if    (niveauEnerv >= 10)  {
            button_second.setEnabled(false);

            Toast toast = Toast.makeText(getActivity().getApplicationContext(),"l'interrogé n'est plus disposé à continuer",Toast.LENGTH_LONG);
            toast.show();
            button_second.setVisibility(View.INVISIBLE);
        }



        final NavController navController2 = Navigation.findNavController(view) ;
        button_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (laReponseCode.contentEquals("RA1"))
                {

                    mainActivity.setDebloqOlivier(true);

                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"theme Olivier debloqué !",Toast.LENGTH_LONG);
                    toast.show();
                }
                else    if (laReponseCode.contentEquals("RA3"))
                {

                    mainActivity.setDebloqBateau(true);
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"theme bateau debloqué !",Toast.LENGTH_LONG);
                    toast.show();
                }

                //***** valeurs à envoyer ici *************************************************************************///////////////


               ReponseFragmentDirections.ActionSecondFragmentToFirstFragment action2 = ReponseFragmentDirections.actionSecondFragmentToFirstFragment();
                //action2.setEnervString(String.valueOf(niveauEnerv));
              navController2.navigate(action2);

/////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////
            }



        });

        switchPourDebug2  = (Switch) view.findViewById(R.id.switch2);


        switchPourDebug2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Log.i(TAG, "onCheckedChanged: true ");

                    rappelQuestion.setVisibility(View.VISIBLE);
                    rappelValeurJauge.setVisibility(View.VISIBLE);
                    textcommentaireErnevdebbug.setVisibility(View.VISIBLE);
                    textcommentaireJaugedebbug.setVisibility(View.VISIBLE);
                }
                else{
                    Log.i(TAG, "onCheckedChanged: false");
                    rappelQuestion.setVisibility(View.INVISIBLE);
                    rappelValeurJauge.setVisibility(View.INVISIBLE);
                    textcommentaireErnevdebbug.setVisibility(View.INVISIBLE);
                }
            }

        });

        button_sortir.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sauveReponses();
                Intent intentsortir = new Intent (getContext(), MainActivity.class);
                intentsortir.putExtra("interroMari",true);
                startActivity(intentsortir);
            }
        });
    }


    public Interrogation getNiveau_Reel_question( String question, String niveau_apparent){
        Interrogation interroX = new Interrogation();
        for (Interrogation interro : listInterrogation)
            if ((interro.getQuestion().contentEquals(question)) & (interro.getNiveau_apparent().contentEquals(niveau_apparent)) )
            {interroX = interro ; }
        return interroX;
    }

    public String MappingReponsechoisie(String reponseATransformer) {

        Log.i(TAG, "getNiveau_Reel_question: ValeurReponseChoisie " + reponseATransformer);
        laReponseA_Affiche = myKeyValueReponse.getKeyfromValueReponse(mainActivity.getMyhasmapReponse(),reponseATransformer) ;
        Log.i(TAG, "getNiveau_Reel_question :mappingValeurReponseChoisie :  " + laReponseA_Affiche);
        return laReponseA_Affiche;

    }

    public void changeColorBarprogression(ProgressBar progressBar,int niveau){

        Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
        int maxi = progressBar.getMax();
        if (niveau<=(0.3*maxi)) {
            progressDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        else if (niveau>=(0.4*maxi) && niveau<=(0.6*maxi))
        {

            // int color = 0xFF00FF00;
            // progressbarVerti.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            progressDrawable.setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        else if (niveau>(0.6*maxi) ) {  //color red
            // progressbarVerti.setProgressBackgroundTintList(ColorStateList.valueOf(0xFF0000));
            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        }

        progressBar.setProgressDrawable(progressDrawable);
    }

    @Override
    public void onPause() {
        super.onPause();
        //todo sauvegarder les questions et le score ici
        //todo  sauvegarder le niveau d'enervenement
    }


    public void sauveReponses() {
        //on ajoute chaque reponse a l arraylist engime interro present dans le score
               MySummurize.getinstance().setListIndiceInterroMari(mainActivity.getReponseSaved());
    }

}