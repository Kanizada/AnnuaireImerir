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
        holder.update(student);
    }


    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView image;
        TextView nomTV;
        TextView prenomTV;
        TextView promotionTV;
        Student student;

        public StudentViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            image = (ImageView) itemView.findViewById(R.id.student_image);
            nomTV = (TextView) itemView.findViewById(R.id.student_last_name);
            prenomTV = (TextView) itemView.findViewById(R.id.student_first_name);
            promotionTV = (TextView) itemView.findViewById(R.id.student_promotion);
        }

        public void update(Student student){
            this.student = student;
            nomTV.setText(student.getLastName());
            prenomTV.setText(student.getFirstName());
            promotionTV.setText(student.getPromotion().getName());
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
