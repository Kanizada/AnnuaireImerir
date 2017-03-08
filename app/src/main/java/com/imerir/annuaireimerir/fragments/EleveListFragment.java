package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.EleveListAdapter;
import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class EleveListFragment extends Fragment {
    RecyclerView recyclerView;
    EleveListAdapter adapter;
    ArrayList<Eleve> liste_eleves;
    EleveListAdapter.OnEleveClickedListener listener;

    public EleveListFragment(){

    }
    public EleveListFragment(EleveListAdapter.OnEleveClickedListener listener){
        this.listener = listener;
    }

    //création d'une instance statique du fragments prenant en paramètres une liste Eleve qui est inclue
    // en arguments du fragment pour passer les données de l'activité vers le fragment pour ensuite les passer à l'adapterr
    //de la recyclerview
    public static EleveListFragment newInstance(ArrayList<Eleve> eleves,EleveListAdapter.OnEleveClickedListener listener){
        EleveListFragment fragment = new EleveListFragment(listener);
        Bundle bundle = new Bundle();
        bundle.putSerializable("liste_eleves", eleves);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        View rootView = inflater.inflate(R.layout.fragment_eleve_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.elevesList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        EleveListAdapter adapter = new EleveListAdapter(liste_eleves,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    //récuperation de la liste_eleves via les arguments du fragment
    public void init(){
        liste_eleves = (ArrayList<Eleve>) this.getArguments().getSerializable("liste_eleves");
    }

}
