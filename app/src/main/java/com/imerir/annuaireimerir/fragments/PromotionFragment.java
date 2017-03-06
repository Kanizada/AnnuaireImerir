package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.EntrepriseAdapter;
import com.imerir.annuaireimerir.adapters.PromotionAdapter;
import com.imerir.annuaireimerir.clients.ApiClient;
import com.imerir.annuaireimerir.models.Promotion;

import java.util.ArrayList;

/**
 * Created by student on 06/03/2017.
 */

public class PromotionFragment extends Fragment implements ApiClient.OnPromotionsListener{
    RecyclerView recyclerView;
    ArrayList<Promotion> promotions;
    PromotionAdapter adapter;
    PromotionAdapter.OnPromotionClickedListener listener;
    public PromotionFragment(){

    }

    public static PromotionFragment newInstance(){
        PromotionFragment fragment = new PromotionFragment();
        return fragment;
    }

    public void init(){
        ApiClient.getInstance().getPromotions("devTmpKey",this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        View rootView = inflater.inflate(R.layout.fragment_promotion_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.promotionList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        PromotionAdapter adapter = new PromotionAdapter(promotions,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }



    @Override
    public void onPromotionsReceived(ArrayList<Promotion> promotions) {
        this.promotions = promotions;
        Toast.makeText(this.getContext(), "Succès lors du chargement des promotions", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPromotionsFailed(String error) {
        Toast.makeText(this.getContext(), "Erreur lors du chargement des promotions", Toast.LENGTH_LONG).show();
    }
}
