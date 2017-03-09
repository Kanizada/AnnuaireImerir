package com.imerir.annuaireimerir.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.imerir.annuaireimerir.models.Entreprise;

/**
 * Created by student on 06/03/2017.
 */

public class EntrepriseDetailFragment extends Fragment implements View.OnClickListener {
    Entreprise entreprise;
    CardView siteWebContainer;
    ImageButton callFixeBtn;
    TextView tvNom,tvAdresse,tvCPetVille,tvSiteWeb,tvTelFixe,tvMail;
    private final static int CALL_PHONE_PERMISSION = 1;

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
        siteWebContainer = (CardView) rootView.findViewById(R.id.sitewebContainer);
        callFixeBtn = (ImageButton) rootView.findViewById(R.id.callFixeBtn);
        tvNom.setText(entreprise.getNom());
        tvAdresse.setText(entreprise.getAdresse());
        tvCPetVille.setText(entreprise.getCode_postal()+" , "+entreprise.getVille());
        tvSiteWeb.setText(entreprise.getSiteWeb());
        tvTelFixe.setText(entreprise.getTelephone());
        tvMail.setText(entreprise.getEmail());
        siteWebContainer.setOnClickListener(this);
        callFixeBtn.setOnClickListener(this);
        //declarer tous les conteneurs du layout ici puis attribuer à chacun la donnée de l'entreprise
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v==siteWebContainer){
            //l'api doit retourner des urls complétes avec le http:// ou https://
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(entreprise.getSiteWeb()));
            startActivity(intent);
        }else if (v==callFixeBtn){
            //Check si l'application a l'autorisation d'appeler, si oui la methode showcalldialog() est appelée
            if(ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                showCallDialog();
            }
            //sinon il demande l'autorisation à l'utilisateur
            else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PHONE_PERMISSION);
            }
        }
    }

    //Methode créant une boite de dialogue demandant confirmation pour appeler
    public void showCallDialog() {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Appeler ?").setMessage("Voulez-vous appeler " + entreprise.getNom() + " ?")
                .setPositiveButton("Appeler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Si l'utilisateur appuie sur appeler , on charge une intent d'appel du numéro
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + entreprise.getTelephone()));
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CALL_PHONE_PERMISSION: {
                //Si la requete est annulée l'array granResults est vide
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Si la requete est validée, on appelle la méthode pour demander si l'utilisateur veut vraiment appeler
                    showCallDialog();
                }
            }
            break;
            default:
                break;
        }
    }
}
