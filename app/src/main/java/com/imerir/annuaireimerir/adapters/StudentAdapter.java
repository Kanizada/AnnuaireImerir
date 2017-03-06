package com.imerir.annuaireimerir.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Eleve;

import java.util.ArrayList;

/**
 * Created by eleve on 10/01/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    ArrayList<Eleve> eleves;
    private OnStudentClickedListener listener;


    public StudentAdapter(ArrayList<Eleve> eleves, final OnStudentClickedListener listener){
        this.eleves = eleves;
        this.listener = listener;
    }


    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Eleve eleve = eleves.get(position);
        holder.update(eleve);
    }


    @Override
    public int getItemCount() {
        return eleves.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView image;
        TextView nomTV;
        TextView prenomTV;
        TextView promotionTV;
        Eleve eleve;

        public StudentViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            image = (ImageView) itemView.findViewById(R.id.student_image);
            nomTV = (TextView) itemView.findViewById(R.id.student_last_name);
            prenomTV = (TextView) itemView.findViewById(R.id.student_first_name);
            promotionTV = (TextView) itemView.findViewById(R.id.student_promotion);
        }

        public void update(Eleve eleve){
            this.eleve = eleve;
            nomTV.setText(eleve.getNom());
            prenomTV.setText(eleve.getPrenom());
            promotionTV.setText(eleve.getPromotion().getName());
            //image
            //cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == cardView){
                listener.onStudentClicked(eleve);
            }
        }
    }

    public interface OnStudentClickedListener{
        void onStudentClicked(Eleve eleve);
    }
}
