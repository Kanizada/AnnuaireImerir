package com.imerir.annuaireimerir.adapters;

import android.os.Parcelable;
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

public class EleveListAdapter extends RecyclerView.Adapter<EleveListAdapter.EleveViewHolder> {
    View v;
    ArrayList<Eleve> eleves;
    private OnEleveClickedListener listener;
    int eleveLayout = R.layout.item_eleve_in_entreprise;
    boolean isInEntrepriseDetail = false;


    public EleveListAdapter(ArrayList<Eleve> eleves, final OnEleveClickedListener listener){
        this.eleves = eleves;
        this.listener = listener;
    }

    public EleveListAdapter(ArrayList<Eleve> eleves, final OnEleveClickedListener listener, boolean isInEntrepriseDetail){
        this.eleves = eleves;
        this.listener = listener;
        this.isInEntrepriseDetail = isInEntrepriseDetail;
    }


    @Override
    public EleveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(eleveLayout, parent, false);
        return new EleveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EleveViewHolder holder, int position) {
        Eleve eleve = eleves.get(position);
        holder.update(eleve);
    }


    @Override
    public int getItemCount() {
        return eleves.size();
    }

    public class EleveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView image;
        TextView nomTV;
        TextView promotionTV;
        Eleve eleve;

        public EleveViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            nomTV = (TextView) itemView.findViewById(R.id.student_last_name);
            promotionTV = (TextView) itemView.findViewById(R.id.student_promotion);
        }

        public void update(Eleve eleve){
            this.eleve = eleve;
            nomTV.setText(eleve.getPrenom()+" "+eleve.getNom());
            if (eleve.getPromotion()!= null){
                promotionTV.setText(eleve.getPromotion().getNom() + " " + eleve.getPromotion().getAnnee());
            }

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == cardView){
                listener.onEleveClicked(eleve);
            }
        }
    }

    public interface OnEleveClickedListener {
        void onEleveClicked(Eleve eleve);
    }
}
