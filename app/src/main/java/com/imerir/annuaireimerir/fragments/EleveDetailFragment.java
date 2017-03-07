package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Eleve;

/**
 * Created by student on 06/03/2017.
 */

public class EleveDetailFragment extends Fragment {
    Eleve eleve;

    public EleveDetailFragment(){

    }

    public static EleveDetailFragment newInstance(Eleve eleve){
        Bundle bundle = new Bundle();
        bundle.putSerializable("eleve", eleve);
        EleveDetailFragment fragment = new EleveDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.eleve = (Eleve) this.getArguments().getSerializable("eleve");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_eleve_detail, container, false);
        //declarer tous les conteneurs du layout ici puis attribuer à chacun la donnée de l'eleve
        return rootView;
    }
}
