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
    private final static int CALL_PHONE_PERMISSION_FIXE = 1;
    private final static int CALL_PHONE_PERMISSION_MOBILE = 2;

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

    //Methode créant une boite de dialogue demandant confirmation pour appeler le numero fixe
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

    //Methode créant une boite de dialogue demandant confirmation pour appeler le numero mobile
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
            //l'api doit retourner des urls complétes avec le http:// ou https://
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(eleve.getSiteWeb()));
            startActivity(intent);
        }else if (v==callFixeBtn){
            //Check si l'application a l'autorisation d'appeler, si oui la methode showcalldialogfixe() est appelée
            if(ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                showCallDialogFixe();
            }
            //sinon il demande l'autorisation à l'utilisateur
            else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PHONE_PERMISSION_FIXE);
            }
        }else if (v==callMobileBtn){
            //Check si l'application a l'autorisation d'appeler, si oui la methode showcalldialogmobile() est appelée
            if(ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                showCallDialogMobile();
            }
            //sinon il demande l'autorisation à l'utilisateur
            else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PHONE_PERMISSION_MOBILE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_PHONE_PERMISSION_FIXE: {
                //Si la requete est annulée l'array granResults est vide
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCallDialogFixe();
                }
            }
            break;
            case CALL_PHONE_PERMISSION_MOBILE:{
                //Si la requete est annulée l'array granResults est vide
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Si la requete est validée, on appelle la méthode pour demander si l'utilisateur veut vraiment appeler
                    showCallDialogMobile();
                }
            }
            default:
                break;
        }
    }
}
