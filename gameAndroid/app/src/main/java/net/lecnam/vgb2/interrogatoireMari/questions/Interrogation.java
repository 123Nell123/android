package net.lecnam.vgb2.interrogatoireMari.questions;

/**
 * Created by Oneal on <DATE-DU-JOUR> or 06/05/2021
 **/
public class Interrogation {
    private String question;
    private String niveau_apparent;
    private String niveau_reel;
    private String reponseGlobale;

    public Interrogation(String question, String niveau_apparent, String niveau_reel, String reponseGlobale) {
        this.question = question;
        this.niveau_apparent = niveau_apparent;
        this.niveau_reel = niveau_reel;
        this.reponseGlobale = reponseGlobale;
    }




    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNiveau_apparent() {
        return niveau_apparent;
    }

    public void setNiveau_apparent(String niveau_apparent) {
        this.niveau_apparent = niveau_apparent;
    }

    public String getNiveau_reel() {
        return niveau_reel;
    }

    public void setNiveau_reel(String niveau_reel) {
        this.niveau_reel = niveau_reel;
    }

    public String getReponseGlobale() {
        return reponseGlobale;
    }

    public void setReponseGlobale(String reponseGlobale) {
        this.reponseGlobale = reponseGlobale;
    }


    public Interrogation() {
    }
}
