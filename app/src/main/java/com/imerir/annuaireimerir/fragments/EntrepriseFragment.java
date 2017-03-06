package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imerir.annuaireimerir.adapters.EntrepriseAdapter;
import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.models.Entreprise;

import java.util.ArrayList;

/**
 * Created by student on 12/01/2017.
 */

public class EntrepriseFragment extends Fragment implements ApiClient.OnEntreprisesListener {
    RecyclerView recyclerView;
    ArrayList<Entreprise> entreprises;
    EntrepriseAdapter adapter;
    EntrepriseAdapter.OnEntrepriseClickedListener listener;

    public EntrepriseFragment(){
    }

    public static EntrepriseFragment newInstance(){
        EntrepriseFragment fragment = new EntrepriseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        View rootView = inflater.inflate(R.layout.fragment_enterprise_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.enterpriseList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        EntrepriseAdapter adapter = new EntrepriseAdapter(entreprises,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void init(){
        ApiClient.getInstance().getEntreprises("devTmpKey", this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onEntreprisesReceived(ArrayList<Entreprise> entreprises) {
        this.entreprises = entreprises;
        Toast.makeText(this.getContext(),"Succès du chargement de la liste des entreprises",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEntreprisesFailed(String error) {
        Toast.makeText(this.getContext(),"Erreur du chargement de la liste des entreprises",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEntrepriseReceived(Entreprise entreprise) {

    }

    @Override
    public void onEntrepriseFailed(String error) {

    }
}
