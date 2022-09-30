package net.lecnam.vgb2.Score;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.lecnam.vgb2.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileSummarize extends AppCompatActivity {
    static  String FILENAME = "";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_summarize);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_summarize, container,
                    false);

            return rootView;
        }
    }


    public void writeClicked(View v) {

        Date currentDate = new Date();
        String donne_date = dateFormat.format(currentDate);
        String format_donne_date0 = donne_date.replace(":", "_");
        String format_donne_date1 = format_donne_date0.replace(" ", "_");
        String format_donne_date2 = format_donne_date1.replace("/", "_");
        String fichier_to_save = "sauv_du_"+format_donne_date2+".txt";

        try {
            FileOutputStream fos = openFileOutput(fichier_to_save, Context.MODE_PRIVATE);
            //todo remplace par le nom du joeur et autre donnee dynamique
            String name = "";
            String enimge1="" ;
            String score ="";
           String resummeEngime1="";

            fos.write(name.getBytes());
            fos.write(enimge1.getBytes());
            fos.write(score.getBytes());
            fos.write(resummeEngime1.getBytes());

            fos.close();

            Toast.makeText(this, "Enregistré", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Une erreur s'est produite",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void readClicked(View v) {

        File fichier = new File(getFilesDir() + "/" + FILENAME);

        FileInputStream fis = null;

        try {
            fis = openFileInput(FILENAME);
            if (fis != null) {

                byte fileContent[] = new byte[(int) fichier.length()];

                fis.read(fileContent);
                String content = new String(fileContent);
                Toast.makeText(this, content, Toast.LENGTH_SHORT).show();

            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Fichier introuvable", Toast.LENGTH_SHORT)
                    .show();
            // e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Impossible de lire le fichier",
                    Toast.LENGTH_SHORT).show();
            // e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }

    }

}