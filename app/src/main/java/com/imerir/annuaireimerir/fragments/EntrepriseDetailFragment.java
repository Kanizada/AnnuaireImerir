package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;

/**
 * Created by student on 06/03/2017.
 */

public class EntrepriseDetailFragment extends Fragment {
    Entreprise entreprise;
    public EntrepriseDetailFragment(){

    }

    public static EntrepriseDetailFragment newInstance(Entreprise entreprise){
        Bundle bundle = new Bundle();
        bundle.putSerializable("entreprise", entreprise);
        EntrepriseDetailFragment fragment = new EntrepriseDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.entreprise = (Entreprise) this.getArguments().getSerializable("entreprise");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_entreprise_detail, container, false);
        //declarer tous les conteneurs du layout ici puis attribuer à chacun la donnée de l'entreprise
        return rootView;
    }
}
