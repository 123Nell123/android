package net.lecnam.vgb2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import net.lecnam.vgb2.R;
import net.lecnam.vgb2.interrogatoireMari.MyInterroActivity;
import net.lecnam.vgb2.QuestionFragmentArgs;
import net.lecnam.vgb2.QuestionFragmentDirections;
import net.lecnam.vgb2.interrogatoireMari.questions.MyKeyValueQuestion;
import net.lecnam.vgb2.interrogatoireMari.spinner.Theme;
import net.lecnam.vgb2.interrogatoireMari.spinner.ThemeDataUtils;

public class QuestionFragment extends Fragment {
    private static final String TAG = "interro";
    MyInterroActivity mainActivity;


    SeekBar toolbar;


    String questionChoisie = "aucune";

    //valeur pour calcul reponse

    int valeurToolBar; // barrre valeur de (-2 à 2)
    String codeQuestion = "";

    // data for spinner
    Theme[] themes;
    ThemeDataUtils themeDataUtils;
    Spinner spinnerThemes;
    TextView tvSpinner;


    // textview des questions
    TextView tvquestion1;
    TextView tvquestion2;
    TextView tvquestion3;
    TextView textTypeEmotionView;


    // ***pour debug
    TextView textJaugeView;
    TextView textinputQuestionView;
    Button checkButton;
    Switch switchPourDebug;
    TextView enervInterrog;
    String ValeurQuestionChoisie = "";
    TextView textcommentaireinputQuestion;
    TextView textcommentaireEnervInterro;

    //
    MyKeyValueQuestion mykeyValue;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        return v;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MyInterroActivity) getActivity();
        themeDataUtils = new ThemeDataUtils();
        toolbar = view.findViewById(R.id.seekBar);
        mykeyValue = new MyKeyValueQuestion();

        // champs pour question
        tvquestion1 = view.findViewById(R.id.textquestion1);
        tvquestion2 = view.findViewById(R.id.textquestion2);
        tvquestion3 = view.findViewById(R.id.textquestion3);
        textTypeEmotionView = view.findViewById(R.id.textTypeEmotion);

        // champs pour debbug
        textinputQuestionView = (TextView) view.findViewById(R.id.textinputQuestion);
        textJaugeView = (TextView) view.findViewById(R.id.textJauge);
        enervInterrog = (TextView) view.findViewById(R.id.EnervInterro);
        checkButton = view.findViewById(R.id.buttonCheck);
        textcommentaireinputQuestion = (TextView) view.findViewById(R.id.textcommentaireinputQuestion);
        textcommentaireEnervInterro = (TextView) view.findViewById(R.id.textcommentaireEnervInterro);
        enervInterrog.setText(String.valueOf(mainActivity.getNiveauEnerv()));
        //***** valeurs à recuperer  ici *****/////////////////////////////////////////////////////////////

        Log.i(TAG, "onViewCreated: ");
        if (getArguments() != null) {
            QuestionFragmentArgs args1 = QuestionFragmentArgs.fromBundle(getArguments());

        }
            ///*** validation question et envoi au deuxieme fragment **************

            final NavController navController = Navigation.findNavController(view);
            view.findViewById(R.id.button_valideQuestion).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (questionChoisie.contentEquals("aucune")) {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "vous devez choisir une question", Toast.LENGTH_LONG);
                        toast.show();
                    } else {

                        affectValeur();
                        //todo donner la question choisie a l'activité principale

                        //valeurToolBar= toolbar.getProgress();
                        // definition de l'action 1st -->  2nd
                        QuestionFragmentDirections.ActionFirstFragmentToSecondFragment action = QuestionFragmentDirections.actionFirstFragmentToSecondFragment();


                        mainActivity.setCodeQuestion(codeQuestion);
                        //action.setKeyJauge(valeurToolBar);
                        //cleanTheGame();
                        navController.navigate(action);
                    }

                    /////////////////////////////////////////////////////////


                }
            });


            //todo declarer un listener commun a toute les questions qui en fonction de la valeur de l'objet affiche la question 1, 2 ou 3 dans la question choisie
            tvquestion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // on click on recupere la question pour debug
                    //en realite on itulisera surtout inputQuestion
                    //  inputQuestion = 1;
                    tvquestion1.setTypeface(null, Typeface.BOLD);
                    tvquestion2.setTypeface(null, Typeface.NORMAL);
                    tvquestion3.setTypeface(null, Typeface.NORMAL);
                    questionChoisie = tvquestion1.getText().toString();

                }
            });

            tvquestion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // inputQuestion = 2;
                    tvquestion1.setTypeface(null, Typeface.NORMAL);
                    tvquestion2.setTypeface(null, Typeface.BOLD);
                    tvquestion3.setTypeface(null, Typeface.NORMAL);

                    questionChoisie = tvquestion2.getText().toString();

                }
            });

            tvquestion3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   inputQuestion = 3;
                    tvquestion1.setTypeface(null, Typeface.NORMAL);
                    tvquestion2.setTypeface(null, Typeface.NORMAL);
                    tvquestion3.setTypeface(null, Typeface.BOLD);

                    questionChoisie = tvquestion3.getText().toString();
                }
            });

            this.spinnerThemes = (Spinner) view.findViewById(R.id.spinner);
            themes = themeDataUtils.getTheme();


            preparationSpinner();


            switchPourDebug = (Switch) view.findViewById(R.id.switch1);
            switchPourDebug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Log.i(TAG, "onCheckedChanged: true ");

                        textinputQuestionView.setVisibility(View.VISIBLE);
                        textJaugeView.setVisibility(View.VISIBLE);
                        checkButton.setVisibility(View.VISIBLE);
                        textcommentaireinputQuestion.setVisibility(View.VISIBLE);
                        textcommentaireEnervInterro.setVisibility(View.VISIBLE);
                        enervInterrog.setVisibility(View.VISIBLE);
                    } else {
                        Log.i(TAG, "onCheckedChanged: false");

                        textinputQuestionView.setVisibility(View.INVISIBLE);
                        textJaugeView.setVisibility(View.INVISIBLE);
                        checkButton.setVisibility(View.INVISIBLE);
                        textcommentaireinputQuestion.setVisibility(View.INVISIBLE);
                        textcommentaireEnervInterro.setVisibility(View.INVISIBLE);
                        enervInterrog.setVisibility(View.INVISIBLE);
                    }
                }
            });

            checkButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    affectValeur();

                }
            });

        }

        @Override
        public void onViewStateRestored(@Nullable Bundle savedInstanceState){
            super.onViewStateRestored(savedInstanceState);

        }

        public void affectValeur(){

            valeurToolBar = toolbar.getProgress();

            //
            codeQuestion = MappingQuestionchoisie();

            textinputQuestionView.setText(String.valueOf(codeQuestion));

            textJaugeView.setText(String.valueOf(valeurToolBar));


        }

        public void preparationSpinner(){
            // (@resource) android.R.layout.simple_spinner_item:
            //   The resource ID for a layout file containing a TextView to use when instantiating views.
            //    (Layout for one ROW of Spinner)
            ArrayAdapter<Theme> adapter = new ArrayAdapter<Theme>(getContext(),
                    android.R.layout.simple_spinner_item,
                    themes) {
                @Override
                public boolean isEnabled(int position) {

                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }

                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    tvSpinner = (TextView) view;
                    //
                    parent.setBackgroundColor(Color.rgb(173, 216, 230));
                    //
                    if (position == 0) {
                        // Set the hint text color gray
                        tvSpinner.setTextColor(Color.GRAY);
                    } else {
                        tvSpinner.setTextColor(Color.BLACK);
                    }
                    if (position == 4 & mainActivity.getDebloqOlivier() == false) {
                        tvSpinner.setVisibility(View.INVISIBLE);
                    }

                    if (position == 5 & mainActivity.getDebloqBateau() == false) {
                        tvSpinner.setVisibility(View.INVISIBLE);
                    }
                    return view;
                }

            };


            // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            this.spinnerThemes.setAdapter(adapter);
            // When user select a List-Item.
            this.spinnerThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onItemSelectedHandler(parent, view, position, id);
                    // ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        private void onItemSelectedHandler(AdapterView<?> adapterView, View view,int position,long id){
            Adapter adapter = adapterView.getAdapter();
            Theme themes = (Theme) adapter.getItem(position);

            // Toast.makeText(getContext(), " Question: " + themes.getQuestion1() ,Toast.LENGTH_SHORT).show();
            tvquestion1.setText(themes.getQuestion1());
            tvquestion2.setText(themes.getQuestion2());
            tvquestion3.setText(themes.getQuestion3());
            textTypeEmotionView.setText(themes.getEmotion());

        }


        public void cleanTheGame() {

            tvquestion1.setTypeface(null, Typeface.NORMAL);
            tvquestion2.setTypeface(null, Typeface.NORMAL);
            tvquestion3.setTypeface(null, Typeface.NORMAL);


        }


        public String MappingQuestionchoisie() {
            Log.i(TAG, "onViewCreated:ValeurQuestionChoisie " + questionChoisie);
            ValeurQuestionChoisie = mykeyValue.getKeyfromValue(mainActivity.getMyhasmapQuestion(), questionChoisie);
            Log.i(TAG, "affichehashmapQuestion :  " + mainActivity.getMyhasmapQuestion().size());
            Log.i(TAG, "onViewCreated:mappingQuestionChoisie :  " + ValeurQuestionChoisie);
            return ValeurQuestionChoisie;
        }

}