package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;

/**
 * Created by student on 06/03/2017.
 */

public class EntrepriseDetailFragment extends Fragment {
    Entreprise entreprise;
    TextView tvNom,tvAdresse,tvCPetVille,tvSiteWeb,tvTelFixe,tvMail;

    public EntrepriseDetailFragment(){

    }

    //création d'une instance statique du fragments prenant en paramètres une Entreprise qui est inclue
    // en arguments du fragment pour passer les données de l'activité vers le fragment
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
        //récuperation de l'objet entreprise via les arguments du fragment
        this.entreprise = (Entreprise) this.getArguments().getSerializable("entreprise");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_entreprise_detail_tmp, container, false);
        tvNom = (TextView) rootView.findViewById(R.id.tvNom);
        tvAdresse = (TextView) rootView.findViewById(R.id.tvAdresse);
        tvCPetVille = (TextView) rootView.findViewById(R.id.tvCPetVille);
        tvSiteWeb = (TextView) rootView.findViewById(R.id.tvSiteWeb);
        tvTelFixe = (TextView) rootView.findViewById(R.id.tvTelephoneFixe);
        tvMail = (TextView) rootView.findViewById(R.id.tvAdresseEmail);
        tvNom.setText(entreprise.getNom());
        tvAdresse.setText(entreprise.getAdresse());
        tvCPetVille.setText(entreprise.getCode_postal()+" , "+entreprise.getVille());
        tvSiteWeb.setText(entreprise.getSiteWeb());
        tvTelFixe.setText(entreprise.getTelephone());
        tvMail.setText(entreprise.getEmail());
        //declarer tous les conteneurs du layout ici puis attribuer à chacun la donnée de l'entreprise
        return rootView;
    }
}
