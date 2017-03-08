package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imerir.annuaireimerir.adapters.EntrepriseListAdapter;
import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.models.Entreprise;

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

    public EntrepriseListFragment(EntrepriseListAdapter.OnEntrepriseClickedListener listener){
        this.listener = listener;
    }

    //création d'une instance statique du fragments prenant en paramètres une liste  Entreprise qui est inclue
    // en arguments du fragment pour passer les données de l'activité vers le fragment pour ensuite les passer à l'adapterr
    //de la recyclerview
    public static EntrepriseListFragment newInstance(ArrayList<Entreprise> entreprises, EntrepriseListAdapter.OnEntrepriseClickedListener listener){
        EntrepriseListFragment fragment = new EntrepriseListFragment(listener);
        Bundle bundle = new Bundle();
        bundle.putSerializable("liste_entreprises", entreprises);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        View rootView = inflater.inflate(R.layout.fragment_entreprise_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.entreprisesList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        EntrepriseListAdapter adapter = new EntrepriseListAdapter(liste_entreprises,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    //récuperation de la liste_promotions via les arguments du fragment
    public void init(){
        this.liste_entreprises = (ArrayList<Entreprise>) this.getArguments().getSerializable("liste_entreprises");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



        //Toast.makeText(this.getContext(),"Succès du chargement de la liste des entreprises",Toast.LENGTH_LONG).show();

        //Toast.makeText(this.getContext(),"Erreur du chargement de la liste des entreprises",Toast.LENGTH_LONG).show();

}
