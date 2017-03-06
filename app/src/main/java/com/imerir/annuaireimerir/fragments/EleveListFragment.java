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

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class EleveListFragment extends Fragment implements ApiClient.OnElevesListener {
    RecyclerView recyclerView;
    EleveListAdapter adapter;
    ArrayList<Eleve> eleves;
    EleveListAdapter.OnEleveClickedListener listener;

    public EleveListFragment(){

    }

    public static EleveListFragment newInstance(){
        EleveListFragment fragment = new EleveListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_student_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.studentList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        EleveListAdapter adapter = new EleveListAdapter(eleves,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
    public void init(){
        ApiClient.getInstance().getEleves("devTmpKey",this);
    }

    @Override
    public void onElevesReceived(ArrayList<Eleve> eleves) {
        this.eleves = eleves;
        Toast.makeText(this.getContext(),"Succès du chargement de la liste des élèves",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onElevesFailed(String error) {
        Toast.makeText(this.getContext(),"Erreur lors du chargement de la liste des élèves",Toast.LENGTH_LONG).show();
    }
}
