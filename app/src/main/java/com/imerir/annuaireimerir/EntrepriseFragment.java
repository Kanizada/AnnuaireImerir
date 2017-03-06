package com.imerir.annuaireimerir;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by student on 12/01/2017.
 */

public class EntrepriseFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Entreprise> entreprises;
    EntrepriseAdapter adapter;
    EntrepriseAdapter.OnEnterpriseClickedListener listener;

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
        entreprises = new ArrayList<>();
        Entreprise entreprise = new Entreprise();
        entreprise.setName("Iristech");
        entreprise.setCity("Perpignan");
        entreprises.add(entreprise);
        entreprises.add(entreprise);
        entreprises.add(entreprise);
        entreprises.add(entreprise);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



}
