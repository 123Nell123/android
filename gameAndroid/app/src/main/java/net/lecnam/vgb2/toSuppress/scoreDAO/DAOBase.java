package net.lecnam.vgb2.toSuppress.scoreDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oneal on <DATE-DU-JOUR> or 29/05/2021
 **/
public class DAOBase {
    private static final String DATABASE_NAME = "interrogatoire.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG ="TAG" ;
  

    //  protected CnamDBHelper mHelper = null;

    private SQLiteDatabase database;
    private static DAOBase instance;
    private SQLiteOpenHelper openHelper;

    public DAOBase(Context context) {
    }


    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DAOBase getInstance(Context context) {
        if (instance == null) {
            instance = new DAOBase(context);
        }
        return instance;
    }


        /*
        //leDBhelp a besoin d'un contexte
        //on instancie un DAO base avec un cnamDBHelper
        mHelper = new CnamDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION );
        if (mHelper != null) Log.i("daobase","creation ok");
        else Log.i("daobase","creation nok");
*/




    public SQLiteDatabase open() {
        SQLiteDatabase mDb = openHelper.getWritableDatabase();
        return mDb;
    }


    //ouverture en ecriture de la db
    public SQLiteDatabase openReadable() {
        SQLiteDatabase mDb = openHelper.getReadableDatabase();
        return mDb;
    }

}
