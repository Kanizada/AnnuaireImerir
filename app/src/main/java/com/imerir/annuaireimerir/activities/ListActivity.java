package com.imerir.annuaireimerir.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.EleveListAdapter;
import com.imerir.annuaireimerir.adapters.EntrepriseListAdapter;
import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.fragments.EleveDetailFragment;
import com.imerir.annuaireimerir.fragments.EntrepriseDetailFragment;
import com.imerir.annuaireimerir.fragments.EntrepriseListFragment;
import com.imerir.annuaireimerir.fragments.EleveListFragment;
import com.imerir.annuaireimerir.fragments.PromotionListFragment;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements EntrepriseListAdapter.OnEntrepriseClickedListener,EleveListAdapter.OnEleveClickedListener,View.OnClickListener, ApiClient.OnElevesListener, ApiClient.OnEntreprisesListener, ApiClient.OnPromotionsListener, GoogleApiClient.OnConnectionFailedListener {

    enum DisplayMode {
        ELEVELIST,
        ENTREPRISELIST,
        PROMOTIONLIST,
        ELEVEDETAIL,
        ENTREPRISEDETAIL
    }

    DisplayMode mode;
    SearchView searchView;
    //DisplayMode previousMode;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions signInOptions;
    Toolbar toolbar;
    Thread thread;
    ProgressDialog loading;
    ListActivity context = this;
    ArrayList<Eleve> liste_eleves = new ArrayList<>();
    ArrayList<Entreprise> liste_entreprises = new ArrayList<>();
    ArrayList<Promotion> liste_promotions = new ArrayList<>();
    SparseArray<Eleve> elevesById = new SparseArray<>();
    SparseArray<Entreprise> entreprisesById = new SparseArray<>();
    Eleve displayedEleve;
    Entreprise displayedEntreprise;
    boolean dataDownloaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //back button sur la toolbar logique provisoire pour le moment un back press retourne le dernier fragment ou l'ou etait
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1){
                    getSupportFragmentManager().popBackStack();
                    getSupportActionBar().setTitle(" ");
                }
            }
        });
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().setHostedDomain("imerir.com").build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();
        if (!dataDownloaded){
            //dataLoading();
            if (!ApiClient.getInstance().loadData("devTmpKey",this,this,this)){
                loading = ProgressDialog.show(context,
                        "Veuillez patienter..",
                        "Chargement des données..",
                        true);
            }
            dataDownloaded=true;
        }else {
            setMode(DisplayMode.ELEVELIST);
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void setMode(DisplayMode newMode){
        if(newMode==DisplayMode.ELEVELIST){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, EleveListFragment.newInstance(liste_eleves,this), "eleves")
                    .addToBackStack("eleves")
                    .commit();

            getSupportActionBar()
                    .setTitle("Liste des élèves");

        }else if (newMode==DisplayMode.ENTREPRISELIST){

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, EntrepriseListFragment.newInstance(liste_entreprises,this), "entreprises")
                    .addToBackStack("entreprises")
                    .commit();

            getSupportActionBar()
                    .setTitle("Liste des entreprises");

        }else if (newMode==DisplayMode.PROMOTIONLIST){

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, PromotionListFragment.newInstance(liste_promotions), "promotions")
                    .addToBackStack("promotions")
                    .commit();

            getSupportActionBar()
                    .setTitle("Liste des promotions");

        }else if (newMode==DisplayMode.ELEVEDETAIL){

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, EleveDetailFragment.newInstance(displayedEleve,this,entreprisesById), "eleve")
                    .addToBackStack("eleve")
                    .commit();

            getSupportActionBar()
                    .setTitle(displayedEleve.getPrenom() + " " + displayedEleve.getNom());

        }else if (newMode==DisplayMode.ENTREPRISEDETAIL){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, EntrepriseDetailFragment.newInstance(displayedEntreprise,this,elevesById), "entreprise")
                    .addToBackStack("entreprise")
                    .commit();
            getSupportActionBar()
                    .setTitle(displayedEntreprise.getNom());
        }

        mode = newMode;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        /*MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
*/        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_sortby:
                //pop up du dialogue pour changer le mode de tri
                return true;
            /*case R.id.action_account:
                // go account page
                return true;*/
            case R.id.action_entreprise:
                setMode(DisplayMode.ENTREPRISELIST);
                return true;
            case R.id.action_eleve:
                setMode(DisplayMode.ELEVELIST);
                return true;
            /*case R.id.action_promotion:
                setMode(DisplayMode.PROMOTIONLIST);
                return true;*/
            case R.id.action_disconnect:
                signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //signout google
    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                    ListActivity.this.startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection échouée..", Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onBackPressed() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1){
                    getSupportFragmentManager().popBackStack();
                    getSupportActionBar().setTitle(" ");
                }
            }
        });
    }

    //Methode provisoire pour le chargement des données depuis l'API
    private void dataLoading(){
        loading = ProgressDialog.show(context,
                "Veuillez patienter..",
                "Chargement des données..",
                true);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("ListActivity","dataLoading() start");
                ApiClient.getInstance().getEntreprises("devTmpKey",context);
                ApiClient.getInstance().getEleves("devTmpKey",context);
                ApiClient.getInstance().getPromotions("devTmpKey",context);
                Log.e("ListActivity","dataLoading() end");
            }
        });
        thread.start();
    }

    /*void linkEntreprisesID(){
        for (Eleve eleve:liste_eleves) {
            ArrayList<Integer> entreprisesId =  eleve.getEntreprisesId();
            for (int i:entreprisesId) {
                ArrayList<Integer> elevesId = entreprisesById.get(i).getElevesId();
                elevesId.add(eleve.getId());
                Log.e("ListActivity","eleveid"+ eleve.getId() + "added to entreprise"+entreprisesById.get(i).getNom());
            }

        }
        if (loading.isShowing()){
            loading.dismiss();
        }
    }*/
    void linkEntreprises(){
        for (Eleve eleve:liste_eleves) {
            ArrayList<Integer> entreprisesId =  eleve.getEntreprisesId();
            for (int i:entreprisesId) {
                ArrayList<Eleve> eleves = entreprisesById.get(i).getEleves();
                eleves.add(eleve);
                Log.e("ListActivity","eleveid"+ eleve.getId() + "added to entreprise"+entreprisesById.get(i).getNom());
            }

        }
        /*if (loading.isShowing()){
            loading.dismiss();
        }*/
    }

    @Override
    public void onElevesReceivedSparse(ArrayList<Eleve> eleves, SparseArray<Eleve> elevesIdObj) {
        liste_eleves = eleves;
        elevesById = elevesIdObj;
        Toast.makeText(this,"Succès du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
        if (loading.isShowing()){
            loading.dismiss();
        }
        setMode(DisplayMode.ELEVELIST);
        Log.e("ListActivity","setmode eleve dataLoading()");
        linkEntreprises();

    }


    @Override
    public void onElevesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
        dataDownloaded = false;
        if (loading.isShowing()){
            loading.dismiss();
            //snackbar bug
            View view = findViewById(R.id.fragmentContainer);
            Snackbar.make(view,
                    "Erreur lors du chargement des listes",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Réessayer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loading = ProgressDialog.show(context,
                                    "Veuillez patienter..",
                                    "Chagement des données..",
                                    true);
                            ApiClient.getInstance().loadData("devTmpKey",context,context,context);
                        }
                    }).show();
        }
    }

    @Override
    public void onEntreprisesReceivedSparse(ArrayList<Entreprise> entreprises, SparseArray<Entreprise> entrepriseIdObj) {
        liste_entreprises = entreprises;
        entreprisesById = entrepriseIdObj;
        for (Entreprise entreprise :entreprises) {
            Log.e("ListAc onElevesReceived",entreprise.getNom() + " " +entreprise.getNom());
        }

    }



    @Override
    public void onEntreprisesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des entreprises",Toast.LENGTH_SHORT).show();
        /*if (loading.isShowing()){
            loading.dismiss();
            //snackbar bug
            View view = findViewById(R.id.fragmentContainer);
            Snackbar.make(view,
                    "Erreur lors du chargement des listes",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Réessayer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loading = ProgressDialog.show(context,
                                    "Veuillez patienter..",
                                    "Chagement des données..",
                                    true);
                            ApiClient.getInstance().loadData("devTmpKey",context,context,context);
                        }
                    }).show();
        }*/
    }



    @Override
    public void onPromotionsReceived(ArrayList<Promotion> promotions) {
        liste_promotions = promotions;
        for (Promotion promotion :promotions) {
            Log.e("ListAc onPromoReceived",promotion.getNom() + " " +promotion.getAnnee());
        }
        /*if (loading.isShowing()){
            loading.dismiss();
            setMode(DisplayMode.ELEVELIST);

        }*/
        //Toast.makeText(this,"Succès du chargement de la liste des promotions",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPromotionsFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des promotions",Toast.LENGTH_SHORT).show();
        /*if (loading.isShowing()){
            loading.dismiss();
            //snackbar bug
            View view = findViewById(R.id.fragmentContainer);
            Snackbar.make(view,
                    "Erreur lors du chargement des listes",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Réessayer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loading = ProgressDialog.show(context,
                                    "Veuillez patienter..",
                                    "Chagement des données..",
                                    true);
                            ApiClient.getInstance().loadData("devTmpKey",context,context,context);
                        }
                    }).show();
        }*/
    }

    @Override
    public void onEleveClicked(Eleve eleve) {
        this.displayedEleve = eleve;
        setMode(DisplayMode.ELEVEDETAIL);
    }

    @Override
    public void onEntrepriseClicked(Entreprise entreprise) {
        this.displayedEntreprise = entreprise;
        setMode(DisplayMode.ENTREPRISEDETAIL);
    }

}
