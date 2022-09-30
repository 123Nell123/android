package net.lecnam.vgb2.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oneal on <DATE-DU-JOUR> or 07/06/2021
 **/
public final  class MySummurize {


    // ces primitifs sont aprtagées par plusieurs Threads
    private volatile int score = 0;
    private volatile String indiceEngime1 = "a remplacer par enigme trouvee";
    private volatile String indiceEngime2 = "a remplacer par enigme trouvee";
    private volatile String indiceEngime3 = "a remplacer par enigme trouvee";
    private volatile String indiceEngime4 = "a remplacer par enigme trouvee";
    private volatile String indiceInterrogation = "a remplacer par enigme trouvee";


    private volatile String indiceEngime5 = "a remplacer par enigme trouvee";
    private volatile String indiceEngime6 = "a remplacer par enigme trouvee";
    List<String> ListIndiceInterroMari;

    public List<String> getListIndiceInterroMari() {
        return ListIndiceInterroMari;
    }

    public void setListIndiceInterroMari(List<String> listIndiceInterroMari) {
        ListIndiceInterroMari = listIndiceInterroMari;
    }






    // getter  setters
    public String getIndiceEngime1() {
        return indiceEngime1;
    }

    public void setIndiceEngime1(String indiceEngime1) {
        this.indiceEngime1 = indiceEngime1;
    }

    public String getIndiceEngime2() {
        return indiceEngime2;
    }

    public void setIndiceEngime2(String indiceEngime2) {
        this.indiceEngime2 = indiceEngime2;
    }

    public String getIndiceEngime3() {
        return indiceEngime3;
    }

    public void setIndiceEngime3(String indiceEngime3) {
        this.indiceEngime3 = indiceEngime3;
    }

    public String getIndiceEngime4() {
        return indiceEngime4;
    }

    public void setIndiceEngime4(String indiceEngime4) {
        this.indiceEngime4 = indiceEngime4;
    }

    public String getIndiceInterrogation() {
        return indiceInterrogation;
    }

    public void setIndiceInterrogation(String indiceInterrogation) {
        this.indiceInterrogation = indiceInterrogation;
    }

    public String getIndiceEngime5() {
        return indiceEngime5;
    }

    public void setIndiceEngime5(String indiceEngime5) {
        this.indiceEngime5 = indiceEngime5;
    }

    public String getIndiceEngime6() {
        return indiceEngime6;
    }

    public void setIndiceEngime6(String indiceEngime6) {
        this.indiceEngime6 = indiceEngime6;
    }


    private static volatile MySummurize mMySummurize = null;
    private MySummurize() {
        super();
    }

    //instancié une seule fois en private
    public static MySummurize getinstance() {
        if (MySummurize.mMySummurize == null) {
            synchronized (MySummurize.class) {
                if (MySummurize.mMySummurize == null) {
                    MySummurize.mMySummurize = new MySummurize();
                }
            }
        }
        return MySummurize.mMySummurize;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int scoreToAdd) {
        {
            this.score = score + scoreToAdd;

        }
    }

        public void historization() {

        }


}