package net.lecnam.vgb2.interrogatoireMari.spinner;

/**
 * Created by Oneal on <DATE-DU-JOUR> or 30/04/2021
 **/
public class Theme {
    private String NomTheme;
    private String Question1;
    private String Question2;
    private String Question3;
    private String emotion;

    @Override
    public String toString() {
        return  NomTheme ;
    }

    public String getQuestion1() {
        return Question1;
    }

    public String getNomTheme() {
        return NomTheme;
    }

    public void setNomTheme(String nomTheme) {
        NomTheme = nomTheme;
    }

    public void setQuestion1(String question1) {
        Question1 = question1;
    }

    public String getQuestion2() {
        return Question2;
    }

    public void setQuestion2(String question2) {
        Question2 = question2;
    }

    public Theme(String nomTheme, String question1, String question2, String question3, String emotion) {
        NomTheme = nomTheme;
        Question1 = question1;
        Question2 = question2;
        Question3 = question3;
        this.emotion = emotion;
    }

    public String getQuestion3() {
        return Question3;
    }

    public void setQuestion3(String question3) {
        Question3 = question3;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public Theme(String question1, String question2, String question3, String emotion) {
        Question1 = question1;
        Question2 = question2;
        Question3 = question3;
        this.emotion = emotion;
    }
}
