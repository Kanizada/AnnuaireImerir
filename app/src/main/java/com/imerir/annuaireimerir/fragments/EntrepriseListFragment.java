package com.imerir.annuaireimerir.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.imerir.annuaireimerir.adapters.EleveListViewAdapter;
import com.imerir.annuaireimerir.adapters.EntrepriseListAdapter;
import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.EntrepriseListViewAdapter;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.tools.SideBar;

import java.util.ArrayList;

/**
 * Created by student on 12/01/2017.
 */

public class EntrepriseListFragment extends Fragment {
    RecyclerView recyclerView;
    SideBar indexBar;
    ListView listView;
    ArrayList<Entreprise> liste_entreprises;
    EntrepriseListViewAdapter adapter;
    EntrepriseListAdapter.OnEntrepriseClickedListener listener;


    public EntrepriseListFragment(){
    }

    public EntrepriseListFragment(ArrayList<Entreprise> entreprises,EntrepriseListAdapter.OnEntrepriseClickedListener listener){
        this.listener = listener;
        this.liste_entreprises = entreprises;
    }


    //création d'une instance statique du fragments prenant en paramètres une liste  Entreprise qui est inclue
    // en arguments du fragment pour passer les données de l'activité vers le fragment pour ensuite les passer à l'adapterr
    //de la recyclerview
    public static EntrepriseListFragment newInstance(ArrayList<Entreprise> entreprises, EntrepriseListAdapter.OnEntrepriseClickedListener listener){
        EntrepriseListFragment fragment = new EntrepriseListFragment(entreprises,listener);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Pour recyclerview
        /*View rootView = inflater.inflate(R.layout.fragment_entreprise_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.entreprisesList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        EntrepriseListAdapter adapter = new EntrepriseListAdapter(liste_entreprises,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);*/
        //Pour listview
        View rootView = inflater.inflate(R.layout.fragment_entreprise_list_test, container, false);
        listView = (ListView) rootView.findViewById(R.id.entreprisesList);
        adapter = new EntrepriseListViewAdapter(getContext(),liste_entreprises, listener);
        listView.setAdapter(adapter);
        indexBar = (SideBar) rootView.findViewById(R.id.sidebar);
        indexBar.setListView(listView);
        return rootView;
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
                    adapter = new EntrepriseListViewAdapter(getContext(),liste_entreprises, listener);
                    listView.setAdapter(adapter);
                    indexBar.setListView(listView);
                    return false;
                }
                newText = newText.replaceAll("\\s+", "").trim().toLowerCase();
                ArrayList<Entreprise> entreprises = new ArrayList<Entreprise>(liste_entreprises);
                //ArrayList<Eleve> removeEleves = new ArrayList<>();
                for (Entreprise entreprise:liste_entreprises){
                    String nom = entreprise.getNom().replaceAll("\\s+", "").trim();
                    if (!nom.toLowerCase().contains(newText)){
                        entreprises.remove(entreprise);
                    }
                }
                adapter = new EntrepriseListViewAdapter(getContext(),entreprises, listener);
                listView.setAdapter(adapter);
                indexBar.setListView(listView);
                Log.e("frag","listview updated");
                return false;
            }
        });
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_list,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


}
