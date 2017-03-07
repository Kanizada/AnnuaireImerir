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
import com.imerir.annuaireimerir.adapters.PromotionListAdapter;
import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Promotion;

import java.util.ArrayList;

/**
 * Created by student on 06/03/2017.
 */

public class PromotionListFragment extends Fragment{
    RecyclerView recyclerView;
    ArrayList<Promotion> liste_promotions;
    PromotionListAdapter adapter;
    PromotionListAdapter.OnPromotionClickedListener listener;

    public PromotionListFragment(){
    }

    //création d'une instance statique du fragments prenant en paramètres une liste de Promotion qui est inclue
    // en arguments du fragment pour passer les données de l'activité vers le fragment pour ensuite les passer à l'adapterr
    //de la recyclerview
    public static PromotionListFragment newInstance(ArrayList<Promotion> promotions){
        PromotionListFragment fragment = new PromotionListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("liste_promotions", promotions);
        fragment.setArguments(bundle);
        return fragment;
    }

    //récuperation de la liste_promotions via les arguments du fragment
    public void init(){
        liste_promotions = (ArrayList<Promotion>) this.getArguments().getSerializable("liste_promotions");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        View rootView = inflater.inflate(R.layout.fragment_promotion_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.promotionsList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        PromotionListAdapter adapter = new PromotionListAdapter(liste_promotions,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
