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

/**
 * Created by student on 06/03/2017.
 */

public class EleveDetailFragment extends Fragment {
    Eleve eleve;
    TextView tvNom,tvPromo,tvAdresse,tvCPetVille,tvSiteWeb,tvTelFixe,tvTelMobile,tvMail;

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
        View rootView = inflater.inflate(R.layout.fragment_eleve_detail_tmp, container, false);
        //declarer tous les conteneurs du layout ici puis attribuer à chacun la donnée de l'eleve
        tvNom = (TextView) rootView.findViewById(R.id.tvNom);
        tvPromo = (TextView) rootView.findViewById(R.id.tvPromotion);
        tvAdresse = (TextView) rootView.findViewById(R.id.tvAdresse);
        tvCPetVille = (TextView) rootView.findViewById(R.id.tvCPetVille);
        tvSiteWeb = (TextView) rootView.findViewById(R.id.tvSiteWeb);
        tvTelFixe = (TextView) rootView.findViewById(R.id.tvTelephoneFixe);
        tvTelMobile = (TextView) rootView.findViewById(R.id.tvTelephoneMobile);
        tvMail = (TextView) rootView.findViewById(R.id.tvAdresseEmail);

        tvNom.setText(eleve.getPrenom()+" "+eleve.getNom());
        tvPromo.setText(eleve.getPromotion().getNom()+ " " +eleve.getPromotion().getAnnee());
        tvAdresse.setText(eleve.getAdresse());
        tvCPetVille.setText(eleve.getCodePostal()+" , " +eleve.getVille());
        tvSiteWeb.setText(eleve.getSiteWeb());
        tvTelFixe.setText(eleve.getTelephoneFixe());
        tvTelMobile.setText(eleve.getTelephoneMobile());
        tvMail.setText(eleve.getEmail());

        return rootView;
    }
}
