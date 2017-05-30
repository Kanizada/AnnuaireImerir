package com.imerir.annuaireimerir.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.models.Promotion;
import com.imerir.annuaireimerir.models.ComparatorNomEleve;
import com.imerir.annuaireimerir.models.ComparatorNomEntreprise;
import com.imerir.annuaireimerir.models.Relation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class FragmentManagerActivity extends AppCompatActivity implements EntrepriseListAdapter.OnEntrepriseClickedListener,EleveListAdapter.OnEleveClickedListener,View.OnClickListener, ApiClient.OnElevesListener, ApiClient.OnEntreprisesListener, ApiClient.OnPromotionsListener, GoogleApiClient.OnConnectionFailedListener, ApiClient.OnRelationsListener, ApiClient.OnDatabaseListener {



    //définit le mode affiché dans l'application
    enum DisplayMode {
        ELEVELIST,
        ENTREPRISELIST,
        ELEVEDETAIL,
        ENTREPRISEDETAIL
    }

    DisplayMode mode = DisplayMode.ELEVELIST;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions signInOptions;
    Toolbar toolbar;
    ProgressDialog loading;
    FragmentManagerActivity context = this;
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
    int pendingRequest;
    Date currentDbVersion;
    Date checkDbVersion;
    Timer timer;
    TimerTask timerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ApiClient.createInstance(this);

        //logique du bouton back dans la toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        //Google connect
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().setHostedDomain("imerir.com").build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

        //requete api
        loadData();



    }


    //requete api
    private void loadData(){
        pendingRequest = 5;
        ApiClient.getInstance().loadData(this,this,this,this,this);
        loading = ProgressDialog.show(context,
                "Veuillez patienter..",
                "Chargement des données..",
                true);
    }

    //Si le chargement des données depuis l'api on dismiss le loading, on cancel la queue volley et on affiche la snackbar pour relancer la requete
    private void loadFailed(){
        if (loading.isShowing()) {
            loading.dismiss();
        }
        ApiClient.getInstance().cancelQueue();
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

    //méthode appelée quand pending request = 0
    private void loadDone(){
        loading.dismiss();
        linkPromotions();
        linkRelation();
        setMode(DisplayMode.ELEVELIST);
        ApiClient.getInstance().checkDb(context);
        //tâche qui verifie la version de la base de données
        timerTask = new TimerTask() {
            @Override
            public void run() {
                ApiClient.getInstance().checkDb(context);
                if (currentDbVersion != null){
                    //Si la version est à jour
                    if (currentDbVersion.equals(checkDbVersion)){
                        FragmentManagerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FragmentManagerActivity.this, "DB à jour", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.e("timertask", "à jour");
                    //Si la version n'est pas à jour
                    }else {
                        FragmentManagerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timer.cancel();
                                View view = findViewById(R.id.fragmentContainer);
                                Snackbar.make(view,
                                        "Les données ne sont plus à jour",
                                        Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Charger", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                loadData();
                                            }
                                        }).show();
                            }
                        });
                        Log.e("timertask", "pas à jour");
                        Log.e("timertask", checkDbVersion.toString() + " /// " +currentDbVersion);
                    }
                }
            }
        };
        timer = new Timer();
        //On dit au timer d'effectuer la timerTask toutes les 10 secondes
        timer.scheduleAtFixedRate(timerTask,10000,10000);
    }
    @Override
    public void onClick(View view) {

    }

    //methode fermer le clavier
    public void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //methode de changement de display mode, selon le mode la methode instancie un nouveau fragment et passe les arguments necessaires au nouveau fragment
    public void setMode(DisplayMode newMode){
        hideKeyBoard();
        if(newMode==DisplayMode.ELEVELIST){
            /*Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("eleves",liste_eleves);*/
            EleveListFragment eleveFragment = EleveListFragment.newInstance(liste_eleves,this);
            //eleveFragment.setArguments(bundle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_left)
                    .replace(R.id.fragmentContainer, eleveFragment, "")
                    .addToBackStack("")
                    .commit();

            getSupportActionBar()
                    .setTitle("Liste des élèves");

        }else if (newMode==DisplayMode.ENTREPRISELIST){
            /*Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("entreprises",liste_entreprises);*/
            EntrepriseListFragment entrepriseFragment = EntrepriseListFragment.newInstance(liste_entreprises,this);
            //entrepriseFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_left)
                    .replace(R.id.fragmentContainer,entrepriseFragment , "")
                    .addToBackStack("")
                    .commit();

            getSupportActionBar()
                    .setTitle("Liste des entreprises");

        }else if (newMode==DisplayMode.ELEVEDETAIL){
            /*Bundle bundle = new Bundle();
            bundle.putParcelable("eleve",displayedEleve);
            bundle.putSparseParcelableArray("entreprises", entreprisesById);*/
            EleveDetailFragment eleveFragment = EleveDetailFragment.newInstance(displayedEleve,this,entreprisesById);
            //eleveFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer,eleveFragment , "eleves")
                    .addToBackStack("eleves")
                    .commit();

            getSupportActionBar()
                    .setTitle(displayedEleve.getPrenom() + " " + displayedEleve.getNom());

        }else if (newMode==DisplayMode.ENTREPRISEDETAIL){
            /*Bundle bundle = new Bundle();
            bundle.putParcelable("entreprise",displayedEntreprise);
            bundle.putSparseParcelableArray("eleves", elevesById);*/
            EntrepriseDetailFragment entrepriseFragment =EntrepriseDetailFragment.newInstance(displayedEntreprise,this,elevesById);
            //entrepriseFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, entrepriseFragment, "entreprises")
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

    //menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_entreprise:
                setMode(DisplayMode.ENTREPRISELIST);
                return true;
            case R.id.action_eleve:
                setMode(DisplayMode.ELEVELIST);
                return true;
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
                    Intent intent = new Intent(FragmentManagerActivity.this, LoginActivity.class);
                    FragmentManagerActivity.this.startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection échouée..", Toast.LENGTH_SHORT).show();
    }




    //Quand le bouton back est pressé selon le mode on met un autre mode
    @Override
    public void onBackPressed() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode) {
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
            }
        });
    }



    //Methode de liage des élèves et des entreprises par rapport à la liste des relations
    void linkRelation(){
        for (Relation relation:liste_relations) {
            int ideleve = relation.getIdeleve();
            int identreprise = relation.getIdentreprise();

            Eleve eleve = elevesById.get(ideleve);
            Entreprise entreprise = entreprisesById.get(identreprise);
            eleve.addEntreprise(entreprise);
            entreprise.addEleve(eleve);
        }
    }

    //Méthode de liage des promotions aux élèves
    void linkPromotions(){
        for (Eleve eleve:liste_eleves) {
            int idpromotion = eleve.getIdpromotion();
            Promotion promotion = promotionsById.get(idpromotion);
            eleve.setPromotion(promotion);
        }
    }

    //Callback quand la requete api de la liste des élèves a réussie
    @Override
    public void onElevesReceived(ArrayList<Eleve> eleves, SparseArray<Eleve> elevesIdObj) {
        switch (pendingRequest){
            case -10:
                loadFailed();
                break;
            case 1:
                sorted_eleves.addAll(eleves);
                this.liste_eleves = new ArrayList<>(sorted_eleves);
                elevesById = elevesIdObj;
                pendingRequest--;
                loadDone();
                break;
            default:
                sorted_eleves.addAll(eleves);
                this.liste_eleves = new ArrayList<>(sorted_eleves);
                elevesById = elevesIdObj;
                pendingRequest--;
        }

        Log.e("pendingRequest", ""+pendingRequest);



    }

    //Callback quand la requete api de la liste des élèves a échoué
    @Override
    public void onElevesFailed(String error) {
        pendingRequest = -10;
        loadFailed();
    }

    //Callback quand la requete api de la liste des entreprises a réussie
    @Override
    public void onEntreprisesReceived(ArrayList<Entreprise> entreprises, SparseArray<Entreprise> entrepriseIdObj) {
        switch (pendingRequest){
            case -10:
                loadFailed();
                break;
            case 1:
                sorted_entreprises.addAll(entreprises);
                this.liste_entreprises = new ArrayList<>(sorted_entreprises);
                entreprisesById = entrepriseIdObj;
                pendingRequest--;
                loadDone();
                break;
            default:
                sorted_entreprises.addAll(entreprises);
                this.liste_entreprises = new ArrayList<>(sorted_entreprises);
                entreprisesById = entrepriseIdObj;
                pendingRequest--;
        }
        Log.e("pendingRequest", ""+pendingRequest);


    }


    //Callback quand la requete api de la liste des élèves a échouée
    @Override
    public void onEntreprisesFailed(String error) {
        pendingRequest = -10;
        loadFailed();
    }


    //Callback quand la requete api de la liste des promotions a réussie
    @Override
    public void onPromotionsReceived(ArrayList<Promotion> promotions, SparseArray<Promotion> promotionsId) {
        switch (pendingRequest){
            case -10:
                loadFailed();
                break;
            case 1:
                liste_promotions = promotions;
                promotionsById = promotionsId;
                pendingRequest--;
                loadDone();
                break;
            default:
                liste_promotions = promotions;
                promotionsById = promotionsId;
                pendingRequest--;
        }

        Log.e("pendingRequest", ""+pendingRequest);
    }

    //Callback quand la requete api de la liste des promotions a échoué
    @Override
    public void onPromotionsFailed(String error) {
        pendingRequest = -10;
        loadFailed();
    }

    //Callback quand la requete api de la liste des relations eleves-entreprises a réussie
    @Override
    public void onRelationsReceived(ArrayList<Relation> relations) {
        switch (pendingRequest){
            case -10:
                loadFailed();
                break;
            case 1:
                this.liste_relations = relations;
                pendingRequest--;
                loadDone();
                break;
            default:
                this.liste_relations = relations;
                pendingRequest--;
        }

        Log.e("pendingRequest", ""+pendingRequest);
    }

    //Callback quand la requete api de la liste des relations a échoué
    @Override
    public void onRelationsFailed(String error) {
        pendingRequest = -10;
        loadFailed();
    }

    //Callback quand un élève de la liste est clické
    @Override
    public void onEleveClicked(Eleve eleve) {
        this.displayedEleve = eleve;
        setMode(DisplayMode.ELEVEDETAIL);
    }

    //Callback quand un entreprise de la liste est clické
    @Override
    public void onEntrepriseClicked(Entreprise entreprise) {
        this.displayedEntreprise = entreprise;
        setMode(DisplayMode.ENTREPRISEDETAIL);
    }

    //on recoit pour la premiere fois la version de la base de données chargée
    @Override
    public void onDatabaseVersionReceived(Date date) {
        switch (pendingRequest){
            case -10:
                loadFailed();
                break;
            case 1:
                currentDbVersion = date;
                pendingRequest--;
                loadDone();
                break;
            default:
                currentDbVersion = date;
                pendingRequest--;
        }
        Log.e("DB VERSION",currentDbVersion.toString());

    }

    //call back de ApiClient.getInstance().checkDb(context);
    @Override
    public void onDataVersionCheck(Date date) {
        checkDbVersion = date;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
