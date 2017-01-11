package com.imerir.annuaireimerir;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by student on 10/01/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    ArrayList<Student> students;
    private OnStudentClickedListener listener;


    public StudentAdapter(ArrayList<Student> students, final OnStudentClickedListener listener){
        this.students = students;
        this.listener = listener;
    }


    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.update(student, listener);
    }


    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView image;
        TextView nomTv;
        TextView prenomTv;
        TextView promotionTv;
        Student student;

        public StudentViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById();
            image = (ImageView) itemView.findViewById();
            nomTv = (TextView) itemView.findViewById();
            prenomTv = (TextView) itemView.findViewById();
            promotionTv = (TextView) itemView.findViewById();
        }

        public void update(Student student, final OnStudentClickedListener listener){
            this.student = student;
            nomTv.setText(student.getLastName());
            prenomTv.setText(student.getLastName());
            promotionTv.setText(student.getPromotion().getName());
            //image
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == cardView){
                listener.onStudentClicked(student);
            }
        }
    }

    public interface OnStudentClickedListener{
        void onStudentClicked(Student student);
    }
}
