package com.imerir.annuaireimerir.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.fragments.EntrepriseListFragment;
import com.imerir.annuaireimerir.fragments.EleveListFragment;
import com.imerir.annuaireimerir.fragments.PromotionListFragment;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, ApiClient.OnElevesListener, ApiClient.OnEntreprisesListener, ApiClient.OnPromotionsListener {
    FloatingActionButton fab;
    DisplayMode mode;
    Thread thread;
    ProgressDialog loading;
    ListActivity context = this;
    ArrayList<Eleve> liste_eleves = new ArrayList<>();
    ArrayList<Entreprise> liste_entreprises = new ArrayList<>();
    ArrayList<Promotion> liste_promotions = new ArrayList<>();
    Boolean dataDownloaded = false;



    enum DisplayMode {
        ELEVE,
        ENTREPRISE,
        PROMOTION
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Liste des élèves");
        if (!dataDownloaded){
            dataLoading();
            dataDownloaded=true;
        }else {
            setMode(DisplayMode.ELEVE);
        }


        //setMode(DisplayMode.ELEVE);
        //fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == fab){
            if (mode == DisplayMode.ELEVE){

            }else if (mode == DisplayMode.ENTREPRISE){

            }else if (mode == DisplayMode.PROMOTION){

            }
        }
    }

    public void setMode(DisplayMode newMode){
        if(newMode==DisplayMode.ELEVE){
            /*fab.setImageDrawable(new IconicsDrawable(this)
                    .icon(GoogleMaterial.Icon.//àdefinir)
                    .color(Color.WHITE).sizeDp(24));*/
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, EleveListFragment.newInstance(liste_eleves), "eleves").commit();
        }else if (newMode==DisplayMode.ENTREPRISE){
            /*fab.setImageDrawable(new IconicsDrawable(this)
                    .icon(GoogleMaterial.Icon.//àdefinir)
                    .color(Color.WHITE).sizeDp(24));*/
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, EntrepriseListFragment.newInstance(liste_entreprises), "entreprises").commit();
        }else if (newMode==DisplayMode.PROMOTION){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, PromotionListFragment.newInstance(liste_promotions), "promotions").commit();
        }
        mode = newMode;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_sortby:
                //pop up du dialogue pour changer le mode de tri
                return true;
            case R.id.action_account:
                // go account page
                return true;
            case R.id.action_entreprise:
                setMode(DisplayMode.ENTREPRISE);
                return true;
            case R.id.action_eleve:
                setMode(DisplayMode.ELEVE);
                return true;
            case R.id.action_promotion:
                setMode(DisplayMode.PROMOTION);
                return true;
            case R.id.action_disconnect:
                Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                ListActivity.this.startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //test au cas ou on load au splashscreen
    /*public void getData(){
            liste_eleves = getIntent().getParcelableArrayListExtra("liste_eleves");
            liste_entreprises = getIntent().getParcelableArrayListExtra("liste_entreprises");
            liste_promotions = getIntent().getParcelableArrayListExtra("liste_promotions");
            for (Eleve eleve :liste_eleves) {
                Log.e("ListAc onElevesReceived",eleve.getNom() + " " +eleve.getPrenom());
            }
            for (Entreprise entreprise :liste_entreprises) {
                Log.e("ListAc onEntReceived",entreprise.getNom() + " " +entreprise.getNom());
            }
            for (Promotion promotion :liste_promotions) {
                Log.e("ListAc onPromReceived",promotion.getNom() + " " +promotion.getAnnee());
            }
    }*/

    public void dataLoading(){
        loading = ProgressDialog.show(context,
                "Veuillez patienter..",
                "Chagement des données..",
                true);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApiClient.getInstance().getEleves("devTmpKey",context);
                ApiClient.getInstance().getEntreprises("devTmpKey",context);
                ApiClient.getInstance().getPromotions("devTmpKey",context);

                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        setMode(DisplayMode.ELEVE);
                        Log.e("ListeActivity","setmode eleve dataLoading()");
                        loading.dismiss();
                    }
                });*/
            }
        });
        thread.start();
        //trouver une maniere de savoir si le chargement est fini, si il est finit retirer le chargement et charger le frag eleves
        /*if (loading.isShowing() && !thread.isAlive()){
            loading.dismiss();
            setMode(DisplayMode.ELEVE);
        }*/
    }

    @Override
    public void onElevesReceived(ArrayList<Eleve> eleves) {
        liste_eleves = eleves;
        for (Eleve eleve :eleves) {
            Log.e("ListAc onElevesReceived",eleve.getNom() + " " +eleve.getPrenom());
        }
        Toast.makeText(this,"Succès du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
        setMode(DisplayMode.ELEVE);
        Log.e("ListeActivity","setmode eleve dataLoading()");
        loading.dismiss();
    }

    @Override
    public void onElevesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEntreprisesReceived(ArrayList<Entreprise> entreprises) {
        liste_entreprises = entreprises;
        for (Entreprise entreprise :entreprises) {
            Log.e("ListAc onElevesReceived",entreprise.getNom() + " " +entreprise.getNom());
        }
        //Toast.makeText(this,"Succès du chargement de la liste des entreprises",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEntreprisesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des entreprises",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEntrepriseReceived(Entreprise entreprise) {

    }

    @Override
    public void onEntrepriseFailed(String error) {

    }

    @Override
    public void onPromotionsReceived(ArrayList<Promotion> promotions) {
        liste_promotions = promotions;
        for (Promotion promotion :promotions) {
            Log.e("ListAc onElevesReceived",promotion.getNom() + " " +promotion.getAnnee());
        }
        //Toast.makeText(this,"Succès du chargement de la liste des promotions",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPromotionsFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
    }
}
