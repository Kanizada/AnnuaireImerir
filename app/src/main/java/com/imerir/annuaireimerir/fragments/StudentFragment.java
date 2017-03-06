package com.imerir.annuaireimerir.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.adapters.StudentAdapter;
import com.imerir.annuaireimerir.models.Eleve;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class StudentFragment extends Fragment {
    RecyclerView recyclerView;
    StudentAdapter adapter;
    ArrayList<Eleve> eleves;
    StudentAdapter.OnStudentClickedListener listener;

    public StudentFragment(){

    }

    public static StudentFragment newInstance(){
        StudentFragment fragment = new StudentFragment();
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
        StudentAdapter adapter = new StudentAdapter(eleves,listener);
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
    public void init(){
        //simulation liste student
/*        ArrayList<Eleve> eleves = new ArrayList<>();
        Promotion promotion = new Promotion();
        promotion.setName("GOA");
        eleves.add(new Eleve("Tristan","Wagner","8 rue Raymond Parès", new Entreprise(),Formation.CDSM, promotion, true));
        eleves.add(new Eleve("Tristan","WagnerWagner","8 rue Raymond Parès", new Entreprise(),Formation.CDSM, promotion, true));
        eleves.add(new Eleve("Tristan","Wagner","8 rue Raymond Parès", new Entreprise(),Formation.CDSM, promotion, true));
        eleves.add(new Eleve("Tristan","Wagner","8 rue Raymond Parès", new Entreprise(),Formation.CDSM, promotion, true));
        this.eleves = eleves;*/
    }
}
