package net.lecnam.vgb2.interrogatoireMari.reponses;

/**
 * Created by Oneal on <DATE-DU-JOUR> or 04/05/2021
 **/

import java.util.HashMap;
import java.util.Map;

public class MyKeyValueReponse {
    


    private static final String TAG ="interro";




    public Map creationMappingReponse() {


        Map<String, String> myhasmapReponse = new HashMap<String, String>();
        Reponse reponse = new Reponse();

        myhasmapReponse.put(reponse.getRA1(), reponse.getnameRA1());
        myhasmapReponse.put(reponse.getRA2(), reponse.getnameRA2());
        myhasmapReponse.put(reponse.getRA3(), reponse.getnameRA3());

        myhasmapReponse.put(reponse.getRB1(), reponse.getnameRB1());
        myhasmapReponse.put(reponse.getRB2(), reponse.getnameRB2());
        myhasmapReponse.put(reponse.getRB3(), reponse.getnameRB3());

        myhasmapReponse.put(reponse.getRC1M(), reponse.getnameRC1M());
        myhasmapReponse.put(reponse.getRC1B(), reponse.getnameRC1B());
        
        myhasmapReponse.put(reponse.getRC2M(), reponse.getnameRC2M());
        myhasmapReponse.put(reponse.getRC2B(), reponse.getnameRC2B()); 
        myhasmapReponse.put(reponse.getRC3(), reponse.getnameRC3());

        myhasmapReponse.put(reponse.getRD1M(), reponse.getnameRD1M());
        myhasmapReponse.put(reponse.getRD1B(), reponse.getnameRD1B());
        
        myhasmapReponse.put(reponse.getRD2(), reponse.getnameRD2());
        myhasmapReponse.put(reponse.getRD3(), reponse.getnameRD3());

        myhasmapReponse.put(reponse.getRE1(), reponse.getnameRE1());
        
        myhasmapReponse.put(reponse.getRE2M(), reponse.getnameRE2M());
        myhasmapReponse.put(reponse.getRE2B(), reponse.getnameRE2B());
        
        myhasmapReponse.put(reponse.getRE3(), reponse.getnameRE3());

    return myhasmapReponse;
    }


    public String getKeyfromValueReponse(Map<String, String> map, String valeur) {
        String myCle = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(valeur)) {
                //   System.out.println(entry.getKey());
                myCle  = entry.getKey();
            }

        }

        return myCle;

    }
}
