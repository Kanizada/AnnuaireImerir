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

public class EnterpriseFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Enterprise> enterprises;
    EnterpriseAdapter adapter;
    EnterpriseAdapter.OnEnterpriseClickedListener listener;

    public EnterpriseFragment(){
    }

    public static EnterpriseFragment newInstance(){
        EnterpriseFragment fragment = new EnterpriseFragment();
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
        EnterpriseAdapter adapter = new EnterpriseAdapter(enterprises,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void init(){
        enterprises = new ArrayList<>();
        Enterprise enterprise = new Enterprise();
        enterprise.setName("Iristech");
        enterprise.setCity("Perpignan");
        enterprises.add(enterprise);
        enterprises.add(enterprise);
        enterprises.add(enterprise);
        enterprises.add(enterprise);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



}
