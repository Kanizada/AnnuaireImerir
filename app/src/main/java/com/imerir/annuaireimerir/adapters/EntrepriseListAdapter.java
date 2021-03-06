package com.imerir.annuaireimerir.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Entreprise;

import java.util.ArrayList;

/**
 * Created by eleve on 12/01/2017.
 */

public class EntrepriseListAdapter extends RecyclerView.Adapter<EntrepriseListAdapter.EnterpriseViewHolder> {
    View v;
    ArrayList<Entreprise> entreprises;
    private OnEntrepriseClickedListener listener;
    int eleveLayout = R.layout.item_entreprise_in_eleve;
    boolean isInEleveDetail = false;


    public EntrepriseListAdapter(ArrayList<Entreprise> entreprises, OnEntrepriseClickedListener listener){
        this.entreprises = entreprises;
        this.listener = listener;
    }

    public EntrepriseListAdapter(ArrayList<Entreprise> entreprises, OnEntrepriseClickedListener listener, boolean isInEleveDetail){
        this.entreprises = entreprises;
        this.listener = listener;
        this.isInEleveDetail = isInEleveDetail;
    }
    @Override
    public EnterpriseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(eleveLayout, parent, false);
        return new EnterpriseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EnterpriseViewHolder holder, int position) {
        Entreprise entreprise = entreprises.get(position);
        holder.update(entreprise);
    }



    @Override
    public int getItemCount() {
        return entreprises.size();
    }

    public class EnterpriseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardView;
        TextView nomTV, villeTV;
        Entreprise entreprise;


        public EnterpriseViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            nomTV = (TextView) itemView.findViewById(R.id.enterprise_name);
            villeTV = (TextView) itemView.findViewById(R.id.enterprise_city);
        }

        public void update(Entreprise entreprise){
            this.entreprise = entreprise;
            nomTV.setText(entreprise.getNom());
            villeTV.setText(entreprise.getVille());
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if ( view == cardView){
                listener.onEntrepriseClicked(entreprise);
            }
        }
    }



    public interface OnEntrepriseClickedListener {
        void onEntrepriseClicked(Entreprise entreprise);
    }
}
