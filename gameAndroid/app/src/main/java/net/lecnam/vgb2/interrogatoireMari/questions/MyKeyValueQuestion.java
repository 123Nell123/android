package net.lecnam.vgb2.interrogatoireMari.questions;



import net.lecnam.vgb2.interrogatoireMari.spinner.ThemeDataUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oneal on <DATE-DU-JOUR> or 04/05/2021
 **/
public class MyKeyValueQuestion {

    private static final String TAG ="interro";

   // ThemeDataUtils themeDataUtils;


   /* public Map<String, String> getMyhasmapQuestion() {
        return myhasmapQuestion;
    }
*/


    public Map<String, String> creationMappingQuestion(  ) {

        Map<String, String> myhasmapQuestion = new HashMap<String, String>();
        ThemeDataUtils themeDataUtils = new ThemeDataUtils();

         myhasmapQuestion.put(themeDataUtils.getQuestionA1(), themeDataUtils.getNameQuestionA1());
        myhasmapQuestion.put(themeDataUtils.getQuestionA2(), themeDataUtils.getNameQuestionA2());
        myhasmapQuestion.put(themeDataUtils.getQuestionA3(), themeDataUtils.getNameQuestionA3());

        myhasmapQuestion.put(themeDataUtils.getQuestionB1(), themeDataUtils.getNameQuestionB1());
        myhasmapQuestion.put(themeDataUtils.getQuestionB2(), themeDataUtils.getNameQuestionB2());
        myhasmapQuestion.put(themeDataUtils.getQuestionB3(), themeDataUtils.getNameQuestionB3());

        myhasmapQuestion.put(themeDataUtils.getQuestionC1(), themeDataUtils.getNameQuestionC1());
        myhasmapQuestion.put(themeDataUtils.getQuestionC2(), themeDataUtils.getNameQuestionC2());
        myhasmapQuestion.put(themeDataUtils.getQuestionC3(), themeDataUtils.getNameQuestionC3());

        myhasmapQuestion.put(themeDataUtils.getQuestionD1(), themeDataUtils.getNameQuestionD1());
        myhasmapQuestion.put(themeDataUtils.getQuestionD2(), themeDataUtils.getNameQuestionD2());
        myhasmapQuestion.put(themeDataUtils.getQuestionD3(), themeDataUtils.getNameQuestionD3());

        myhasmapQuestion.put(themeDataUtils.getQuestionE1(), themeDataUtils.getNameQuestionE1());
        myhasmapQuestion.put(themeDataUtils.getQuestionE2(), themeDataUtils.getNameQuestionE2());
        myhasmapQuestion.put(themeDataUtils.getQuestionE3(), themeDataUtils.getNameQuestionE3());
        return myhasmapQuestion;
    }


    public String getKeyfromValue(Map<String, String> map, String cle) {
        String myValue = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(cle)) {
                //   System.out.println(entry.getKey());
                myValue = entry.getValue();
            }

        }

        return myValue;

    }
}