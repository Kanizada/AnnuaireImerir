package com.imerir.annuaireimerir.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.EleveListAdapter;
import com.imerir.annuaireimerir.adapters.EleveListViewAdapter;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.tools.SideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 10/01/2017.
 */

public class EleveListFragment extends Fragment {
    RecyclerView recyclerView;
    EleveListAdapter rvAdapter;
    EleveListViewAdapter adapter;
    SideBar indexBar;
    ListView listView;
    View rootView;
    ArrayList<Eleve> liste_eleves;
    EleveListAdapter.OnEleveClickedListener listener;

    public EleveListFragment(){

    }
    public EleveListFragment(ArrayList<Eleve> liste_eleves, EleveListAdapter.OnEleveClickedListener listener){
        this.listener = listener;
        this.liste_eleves = liste_eleves;
    }

    //création d'une instance statique du fragments prenant en paramètres une liste Eleve qui est inclue
    // en arguments du fragment pour passer les données de l'activité vers le fragment pour ensuite les passer à l'adapterr
    //de la recyclerview
    public static EleveListFragment newInstance(ArrayList<Eleve> eleves,EleveListAdapter.OnEleveClickedListener listener){
        EleveListFragment fragment = new EleveListFragment(eleves,listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager)  getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Rechercher");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText == null || newText.trim().isEmpty()){
                    Log.e("frag","empty or null");
                    adapter = new EleveListViewAdapter(getContext(),liste_eleves, listener);
                    listView.setAdapter(adapter);
                    indexBar.setListView(listView);
                    return false;
                }
                newText = newText.replaceAll("\\s+", "").trim().toLowerCase();
                ArrayList<Eleve> eleves = new ArrayList<Eleve>(liste_eleves);
                //ArrayList<Eleve> removeEleves = new ArrayList<>();
                for (Eleve eleve:liste_eleves){
                    String nomPrenom = eleve.getPrenom().replaceAll("\\s+", "").trim()+eleve.getNom().replaceAll("\\s+", "").trim();
                    if (!nomPrenom.toLowerCase().contains(newText)){
                        eleves.remove(eleve);
                    }
                }
                adapter = new EleveListViewAdapter(getContext(),eleves, listener);
                listView.setAdapter(adapter);
                indexBar.setListView(listView);
                Log.e("frag","listview updated");

                return false;
            }
        });
        super.onPrepareOptionsMenu(menu);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_eleve_list_test, container, false);
        listView = (ListView) rootView.findViewById(R.id.elevesList);
        indexBar = (SideBar) rootView.findViewById(R.id.sidebar);
        initlistView(liste_eleves);

        return rootView;
    }

    public void initlistView(ArrayList<Eleve> liste_eleves){
        adapter = new EleveListViewAdapter(getContext(),liste_eleves, listener);
        listView.setAdapter(adapter);
        indexBar.setListView(listView);
    }

    /*public View initRecyclerView(LayoutInflater inflater,ViewGroup container){
        rootView = inflater.inflate(R.layout.fragment_eleve_list_test, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.elevesList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        rvAdapter = new EleveListAdapter(liste_eleves,listener);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(rvAdapter);
    }*/
    /*@Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()){
            Log.e("frag","empty or null");
            initlistView(liste_eleves);
            return false;
        }

        ArrayList<Eleve> filteredEleves = new ArrayList<>(liste_eleves);
        for (Eleve eleve:filteredEleves){
            String nomPrenom = eleve.getPrenom()+eleve.getNom();
            if (!nomPrenom.toLowerCase().contains(newText.toLowerCase())){
                filteredEleves.remove(eleve);
            }
        }

        adapter = new EleveListViewAdapter(getContext(),filteredEleves, listener);
        listView.setAdapter(adapter);
        indexBar.setListView(listView);
        Log.e("frag","listview updated");

        return false;
    }*/
}
