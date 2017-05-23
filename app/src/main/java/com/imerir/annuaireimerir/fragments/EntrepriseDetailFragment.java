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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.EleveListAdapter;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;

import java.util.ArrayList;

/**
 * Created by student on 06/03/2017.
 */

public class EntrepriseDetailFragment extends Fragment implements View.OnClickListener {
    EleveListAdapter.OnEleveClickedListener listener;
    EleveListAdapter adapter;
    RecyclerView recyclerView;
    Entreprise entreprise;
    CardView siteWebContainer;
    ImageButton callFixeBtn;
    TextView tvNom,tvAdresse,tvCPetVille,tvSiteWeb,tvTelFixe,tvMail;
    ArrayList<Eleve> eleves;
    ArrayList<Integer> elevesId =new ArrayList<>();
    SparseArray<Eleve> elevesById = new SparseArray<>();

    private final static int CALL_PHONE_PERMISSION = 1;

    public EntrepriseDetailFragment(){

    }

    public EntrepriseDetailFragment(Entreprise entreprise,EleveListAdapter.OnEleveClickedListener listener,SparseArray<Eleve> elevesById){
        this.listener = listener;
        this.entreprise = entreprise;
        this.elevesById = elevesById;
    }

    //création d'une instance statique du fragments prenant en paramètres une Entreprise qui est inclue
    // en arguments du fragment pour passer les données de l'activité vers le fragment
    public static EntrepriseDetailFragment newInstance(Entreprise entreprise, EleveListAdapter.OnEleveClickedListener listener,SparseArray<Eleve> elevesById){
        EntrepriseDetailFragment fragment = new EntrepriseDetailFragment(entreprise,listener,elevesById);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_entreprise_detail, container, false);
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
        tvCPetVille.setText(entreprise.getCode_postal()+", "+entreprise.getVille());
        tvSiteWeb.setText(entreprise.getSiteWeb());
        tvTelFixe.setText(entreprise.getTelephone());
        tvMail.setText(entreprise.getEmail());
        elevesId = entreprise.getElevesId();
        eleves = entreprise.getEleves();
        if (eleves == null){
            eleves = new ArrayList<>();
            for (int i:elevesId) {
                eleves.add(elevesById.get(i));
            }
        }
        if (eleves != null)
        {
            recyclerView = (RecyclerView) rootView.findViewById(R.id.elevesList);
            final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mlayoutManager);
            EleveListAdapter adapter = new EleveListAdapter(eleves,listener,true);
            this.adapter = adapter;
            recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recyclerView.setViewCacheExtension(null);
            recyclerView.setAdapter(adapter);
        }
        tvAdresse.setOnClickListener(this);
        tvCPetVille.setOnClickListener(this);
        tvMail.setOnClickListener(this);
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
        }else if (v == tvCPetVille || v == tvAdresse){
            if (!entreprise.getAdresse().isEmpty() || entreprise.getAdresse() != null && !entreprise.getVille().isEmpty() || entreprise.getVille() != null){
                try {
                    String address = entreprise.getAdresse()+"+"+entreprise.getVille();
                    Intent geoIntent = new Intent (android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
                    startActivity(geoIntent);
                } catch (Exception e){
                    Log.e("EntrepriseDetail",""+e);
                }
            }
        }else if (v == tvMail){
            if (!entreprise.getEmail().isEmpty() || entreprise.getEmail() != null){
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: " + entreprise.getEmail()));
                startActivity(Intent.createChooser(emailIntent, "Envoyer un email"));
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
