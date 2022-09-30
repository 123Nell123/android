package net.lecnam.vgb2.interrogatoireMari.spinner;

/**
 * Created by Oneal on <DATE-DU-JOUR> or 30/04/2021
 **/
public class ThemeDataUtils {

    private   String nomTheme0="choissisez votre Theme";
    private   String QA0="";


    private   String nomThemeA="blandine";
    private   String QA1="comment connaissez vous Blandine ?";
    private   String QA2="Etiez vous intimes ?";
    private   String QA3="quand l'avez vous vu pour la dernière fois ?";
    private   String emotionA="assurance";

    private   String nomThemeB="antiquaire";
    private   String QB1="êtes vous déjà allé à la boutique d'antiquité d'Intra Muros ?";
    private   String QB2="Connaissez vous cet homme (vous montrez une photo) ?";
    private   String QB3="L'avez vous déjà vu avec Blandine (vous montrez une photo)?";
    private   String emotionB="?";

    private   String nomThemeC="travail dans l'entreprise Jeanneau";
    private   String QC1="En quoi consiste votre travail ?";
    private   String QC2="Vous faites autre chose à côté ?";
    private   String QC3="Qui est votre patron ?";
    private   String emotionC="connivence";

    private   String nomThemeD="Olivier";
    private   String QD1="Le croisez vous souvent ?";
    private   String QD2="S'entendait il bien avec sa femme ?";
    private   String QD3="Le connaissez vous bien ?";
    private   String emotionD="enjoué";

    private   String nomThemeE="bateau";
    private   String QE1="Combien coûte le bateau ? comment l'a-t-elle payé ?";
    private   String QE2="De quel type de bateau s'agit il ?";
    private   String QE3="savez vous ce qu'elle connait faire avec ce bateau ?";
    private   String emotionE="suspicieux ";



    public   Theme[] getTheme()  {


        //on cree des instances de Theme
        Theme theme0 = new Theme(nomTheme0,QA0, QA0, QA0, QA0);
        Theme themeA = new Theme(nomThemeA,QA1, QA2, QA3, emotionA);
        Theme themeB = new Theme(nomThemeB,QB1, QB2, QB3, emotionB);
        Theme themeC = new Theme(nomThemeC,QC1, QC2, QC3, emotionC);
        Theme themeD = new Theme(nomThemeD,QD1, QD2, QD3, emotionD);
        Theme themeE = new Theme(nomThemeE,QE1, QE2, QE3, emotionE);
        
        return new Theme[] {theme0, themeA, themeB, themeC,themeD,themeE};
    }

    public   String getNomTheme0() {
        return nomTheme0;
    }

    public   String getQuestionA0() {
        return QA0;
    }

    public   String getNomThemeA() {
        return nomThemeA;
    }

  
    
    public   String getQuestionA1() {
        return QA1;
    }

    public   String getQuestionA2() {
        return QA2;
    }

    public   String getQuestionA3() {
        return QA3;
    }

    public   String getEmotionA() {
        return emotionA;
    }

    public   String getNomThemeB() {
        return nomThemeB;
    }

    public   String getQuestionB1() {
        return QB1;
    }

    public   String getQuestionB2() {
        return QB2;
    }

    public   String getQuestionB3() {
        return QB3;
    }

    public   String getEmotionB() {
        return emotionB;
    }

    public   String getNomThemeC() {
        return nomThemeC;
    }

    public   String getQuestionC1() {
        return QC1;
    }

    public   String getQuestionC2() {
        return QC2;
    }

    public   String getQuestionC3() {
        return QC3;
    }

    public   String getEmotionC() {
        return emotionC;
    }

    public   String getNomThemeD() {
        return nomThemeD;
    }

    public   String getQuestionD1() {
        return QD1;
    }

    public   String getQuestionD2() {
        return QD2;
    }

    public   String getQuestionD3() {
        return QD3;
    }

    public   String getEmotionD() {
        return emotionD;
    }

    public   String getNomThemeE() {
        return nomThemeE;
    }

    public   String getQuestionE1() {
        return QE1;
    }

    public   String getQuestionE2() {
        return QE2;
    }

    public   String getQuestionE3() {
        return QE3;
    }

    public   String getEmotionE() {
        return emotionE;
    }

///*****




    public   String getNameQuestionA1() {
        return "QA1";
    }

    public   String getNameQuestionA2() {
        return "QA2";
    }

    public   String getNameQuestionA3() {
        return "QA3";
    }


    public   String getNameQuestionB1() {
        return "QB1";
    }

    public   String getNameQuestionB2() {
        return "QB2";
    }

    public   String getNameQuestionB3() {
        return "QB3";
    }


    public   String getNameQuestionC1() {
        return "QC1";
    }

    public   String getNameQuestionC2() {
        return "QC2";
    }

    public   String getNameQuestionC3() {
        return "QC3";
    }


    public   String getNameQuestionD1() {
        return "QD1";
    }

    public   String getNameQuestionD2() {
        return "QD2";
    }

    public   String getNameQuestionD3() {
        return "QD3";
    }



    public   String getNameQuestionE1() {
        return "QE1";
    }

    public   String getNameQuestionE2() {
        return "QE2";
    }

    public   String getNameQuestionE3() {
        return "QE3";
    }


}
