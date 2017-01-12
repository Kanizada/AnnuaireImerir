package com.imerir.annuaireimerir;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class StudentFragment extends Fragment {
    RecyclerView recyclerView;
    StudentAdapter adapter;
    ArrayList<Student> students;

    public StudentFragment(){

    }

    public static StudentFragment newInstance(){
        StudentFragment fragment = new StudentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Tristan","Wagner","8 rue Raymond Parès", new Enterprise(),Formation.CDSM, new Promotion(), true));
        students.add(new Student("Tristan","Wagner","8 rue Raymond Parès", new Enterprise(),Formation.CDSM, new Promotion(), true));
        students.add(new Student("Tristan","Wagner","8 rue Raymond Parès", new Enterprise(),Formation.CDSM, new Promotion(), true));
        students.add(new Student("Tristan","Wagner","8 rue Raymond Parès", new Enterprise(),Formation.CDSM, new Promotion(), true));
        this.students = students;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_student_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.studentList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        StudentAdapter adapter = new StudentAdapter(students,(StudentAdapter.OnStudentClickedListener) this.getActivity());
        this.adapter = adapter;
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setViewCacheExtension(null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
