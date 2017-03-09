package com.imerir.annuaireimerir.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Eleve;

/**
 * Created by student on 06/03/2017.
 */

public class EleveDetailFragment extends Fragment implements View.OnClickListener {
    Eleve eleve;
    CardView siteWebContainer;
    ImageButton callFixeBtn,callMobileBtn;
    TextView tvNom,tvPromo,tvAdresse,tvCPetVille,tvSiteWeb,tvTelFixe,tvTelMobile,tvMail;
    private final static int CALL_PHONE_PERMISSION = 1;

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
        siteWebContainer = (CardView) rootView.findViewById(R.id.sitewebContainer);
        callFixeBtn = (ImageButton) rootView.findViewById(R.id.callFixeBtn);
        callMobileBtn = (ImageButton) rootView.findViewById(R.id.callMobileBtn);
        tvNom.setText(eleve.getPrenom()+" "+eleve.getNom());
        tvPromo.setText(eleve.getPromotion().getNom()+ " " +eleve.getPromotion().getAnnee());
        tvAdresse.setText(eleve.getAdresse());
        tvCPetVille.setText(eleve.getCodePostal()+" , " +eleve.getVille());
        tvSiteWeb.setText(eleve.getSiteWeb());
        tvTelFixe.setText(eleve.getTelephoneFixe());
        tvTelMobile.setText(eleve.getTelephoneMobile());
        tvMail.setText(eleve.getEmail());
        siteWebContainer.setOnClickListener(this);
        callMobileBtn.setOnClickListener(this);
        callFixeBtn.setOnClickListener(this);

        return rootView;
    }

    public void showCallDialogFixe() {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Appeler ?").setMessage("Voulez-vous appeler " + eleve.getPrenom()+" "+ eleve.getNom() + " ?")
                .setPositiveButton("Appeler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + eleve.getTelephoneFixe()));
                        try {
                            startActivity(intent);
                        }
                        catch(SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void showCallDialogMobile() {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Appeler ?").setMessage("Voulez-vous appeler " + eleve.getPrenom()+" "+ eleve.getNom() + " ?")
                .setPositiveButton("Appeler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + eleve.getTelephoneMobile()));
                        try {
                            startActivity(intent);
                        }
                        catch(SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        if (v==siteWebContainer){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(eleve.getSiteWeb()));
            startActivity(intent);
        }else if (v==callFixeBtn){
            if(ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                showCallDialogFixe();
            }
            else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PHONE_PERMISSION);
            }
        }else if (v==callMobileBtn){
            if(ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                showCallDialogMobile();
            }
            else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PHONE_PERMISSION);
            }
        }
    }
}
