package net.lecnam.vgb2.didactique;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.lecnam.vgb2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DidactiqueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DidactiqueFragment extends Fragment {

    public static DidactiqueFragment newInstance() {
        return (new DidactiqueFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_didactique, container, false);
    }
}