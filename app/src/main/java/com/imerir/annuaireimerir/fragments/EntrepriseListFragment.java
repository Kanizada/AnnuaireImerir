package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.imerir.annuaireimerir.adapters.EntrepriseListAdapter;
import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.EntrepriseListViewAdapter;
import com.imerir.annuaireimerir.models.Entreprise;
import com.imerir.annuaireimerir.tools.SideBar;

import java.util.ArrayList;

/**
 * Created by student on 12/01/2017.
 */

public class EntrepriseListFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Entreprise> liste_entreprises;
    EntrepriseListAdapter adapter;
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
        ListView listView = (ListView) rootView.findViewById(R.id.entreprisesList);
        EntrepriseListViewAdapter adapter = new EntrepriseListViewAdapter(getContext(),liste_entreprises, listener);
        listView.setAdapter(adapter);
        SideBar indexBar = (SideBar) rootView.findViewById(R.id.sidebar);
        indexBar.setListView(listView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
