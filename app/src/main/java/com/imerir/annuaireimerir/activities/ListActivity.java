package com.imerir.annuaireimerir.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.imerir.annuaireimerir.models.ComparatorNomEleve;
import com.imerir.annuaireimerir.models.ComparatorNomEntreprise;
import com.imerir.annuaireimerir.models.Relation;

import java.util.ArrayList;
import java.util.TreeSet;

public class ListActivity extends AppCompatActivity implements EntrepriseListAdapter.OnEntrepriseClickedListener,EleveListAdapter.OnEleveClickedListener,View.OnClickListener, ApiClient.OnElevesListener, ApiClient.OnEntreprisesListener, ApiClient.OnPromotionsListener, GoogleApiClient.OnConnectionFailedListener, ApiClient.OnRelationsListener {

    enum DisplayMode {
        ELEVELIST,
        ENTREPRISELIST,
        PROMOTIONLIST,
        ELEVEDETAIL,
        ENTREPRISEDETAIL
    }

    private Fragment fragment;
    private FragmentManager fragmentManager;
    DisplayMode mode = DisplayMode.ELEVELIST;
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
    ArrayList<Relation> liste_relations = new ArrayList<>();
    SparseArray<Eleve> elevesById = new SparseArray<>();
    SparseArray<Entreprise> entreprisesById = new SparseArray<>();
    SparseArray<Promotion> promotionsById = new SparseArray<>();
    TreeSet<Entreprise> sorted_entreprises = new TreeSet<>(new ComparatorNomEntreprise());
    TreeSet<Eleve> sorted_eleves = new TreeSet<>(new ComparatorNomEleve());
    Eleve displayedEleve;
    Entreprise displayedEntreprise;
    boolean dataDownloaded = false;
    boolean relationLoaded = false;
    boolean elevesLoaded = false;
    boolean entreprisesLoaded = false;
    boolean promoLoaded = false;
    int pendingRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ApiClient.createInstance(this);
        //back button sur la toolbar logique provisoire pour le moment un back press retourne le dernier fragment ou l'ou etait
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1){
                    switch (mode){
                        case ELEVEDETAIL:
                            setMode(DisplayMode.ELEVELIST);
                            break;
                        case ENTREPRISEDETAIL:
                            setMode(DisplayMode.ENTREPRISELIST);
                            break;
                        case ENTREPRISELIST:
                            setMode(DisplayMode.ELEVELIST);
                            break;
                        case ELEVELIST:
                            setMode(DisplayMode.ENTREPRISELIST);
                            break;
                        default:
                            getSupportFragmentManager().popBackStack();
                            break;
                    }

                    /*Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                    Log.e("fragment tag", f.getTag());
                    switch (f.getTag()){
                        case "eleves":
                            getSupportActionBar().setTitle("Liste des élèves");
                            break;
                        case "entreprises":
                            getSupportActionBar().setTitle("Liste des entreprises");
                            break;
                        default:
                            getSupportActionBar().setTitle(" ");
                            break;
                    }*/

                    //getSupportActionBar().setTitle(" ");
                }
            }
        });
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().setHostedDomain("imerir.com").build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

        loadData();
    }

    private void loadData(){
        pendingRequest = 4;
        ApiClient.getInstance().loadData(this,this,this,this);
        loading = ProgressDialog.show(context,
                "Veuillez patienter..",
                "Chargement des données..",
                true);
    }

    private void checkLoadData(){
        if (pendingRequest == 0){
            loading.dismiss();
            linkPromotions();
            linkRelation();
            setMode(DisplayMode.ELEVELIST);
        }else if (pendingRequest == -10){
            if (loading.isShowing()) {
                loading.dismiss();
            }
            View view = findViewById(R.id.fragmentContainer);
            Snackbar.make(view,
                    "Erreur lors du chargement des données",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Réessayer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadData();
                        }
                    }).show();

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
                    .setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_left)
                    .replace(R.id.fragmentContainer, EleveListFragment.newInstance(liste_eleves,this), "")
                    .addToBackStack("")
                    .commit();

            getSupportActionBar()
                    .setTitle("Liste des élèves");

        }else if (newMode==DisplayMode.ENTREPRISELIST){

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_left)
                    .replace(R.id.fragmentContainer, EntrepriseListFragment.newInstance(liste_entreprises,this), "")
                    .addToBackStack("")
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
                    .replace(R.id.fragmentContainer, EleveDetailFragment.newInstance(displayedEleve,this,entreprisesById), "eleves")
                    .addToBackStack("eleves")
                    .commit();

            getSupportActionBar()
                    .setTitle(displayedEleve.getPrenom() + " " + displayedEleve.getNom());

        }else if (newMode==DisplayMode.ENTREPRISEDETAIL){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, EntrepriseDetailFragment.newInstance(displayedEntreprise,this,elevesById), "entreprises")
                    .addToBackStack("entreprises")
                    .commit();
            getSupportActionBar()
                    .setTitle(displayedEntreprise.getNom());
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
            /*case R.id.action_sortby:
                //pop up du dialogue pour changer le mode de tri
                return true;*/
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

    void linkRelation(){
        for (Relation relation:liste_relations) {
            int ideleve = relation.getIdeleve();
            int identreprise = relation.getIdentreprise();

            Eleve eleve = elevesById.get(ideleve);
            Entreprise entreprise = entreprisesById.get(identreprise);
            Promotion promotion = promotionsById.get(eleve.getIdpromotion());
            eleve.addEntreprise(entreprise);
            entreprise.addEleve(eleve);
            eleve.setPromotion(promotion);
            //promotion.addEleve(eleve);
        }
    }

    void linkPromotions(){
        for (Eleve eleve:liste_eleves) {
            int idpromotion = eleve.getIdpromotion();
            Promotion promotion = promotionsById.get(idpromotion);
            eleve.setPromotion(promotion);
            //promotion.addEleve(eleve);
        }
    }

    @Override
    public void onElevesReceivedSparse(ArrayList<Eleve> eleves, SparseArray<Eleve> elevesIdObj) {
        sorted_eleves.addAll(eleves);
        this.liste_eleves = new ArrayList<>(sorted_eleves);
        elevesById = elevesIdObj;
        pendingRequest--;
        checkLoadData();
        Log.e("pendingRequest", ""+pendingRequest);



    }


    @Override
    public void onElevesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des élèves",Toast.LENGTH_SHORT).show();
        pendingRequest = -10;
    }

    @Override
    public void onEntreprisesReceivedSparse(ArrayList<Entreprise> entreprises, SparseArray<Entreprise> entrepriseIdObj) {
        sorted_entreprises.addAll(entreprises);
        this.liste_entreprises = new ArrayList<>(sorted_entreprises);
        entreprisesById = entrepriseIdObj;
        pendingRequest--;
        Log.e("pendingRequest", ""+pendingRequest);
        checkLoadData();

    }



    @Override
    public void onEntreprisesFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des entreprises",Toast.LENGTH_SHORT).show();
        pendingRequest = -10;
    }



    @Override
    public void onPromotionsReceived(ArrayList<Promotion> promotions, SparseArray<Promotion> promotionsId) {
        liste_promotions = promotions;
        promotionsById = promotionsId;
        pendingRequest--;
        checkLoadData();
        Log.e("pendingRequest", ""+pendingRequest);
    }

    @Override
    public void onPromotionsFailed(String error) {
        Toast.makeText(this,"Erreur du chargement de la liste des promotions",Toast.LENGTH_SHORT).show();
        pendingRequest = -10;
    }

    @Override
    public void onRelationsReceived(ArrayList<Relation> relations) {
        this.liste_relations = relations;
        pendingRequest--;
        checkLoadData();
        Log.e("pendingRequest", ""+pendingRequest);
    }

    @Override
    public void onRelationsFailed(String error) {
        pendingRequest = -10;
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
