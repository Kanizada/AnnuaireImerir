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
 * Created by student on 12/01/2017.
 */

public class EntrepriseAdapter extends RecyclerView.Adapter<EntrepriseAdapter.EnterpriseViewHolder> {

    ArrayList<Entreprise> entreprises;
    private OnEnterpriseClickedListener listener;

    public EntrepriseAdapter(ArrayList<Entreprise> entreprises, OnEnterpriseClickedListener listener){
        this.entreprises = entreprises;
        this.listener = listener;

    }
    @Override
    public EnterpriseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enterprise, parent, false);
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
        ImageView image;
        TextView nomTV, villeTV;
        Entreprise entreprise;


        public EnterpriseViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            image = (ImageView) itemView.findViewById(R.id.enterprise_image);
            nomTV = (TextView) itemView.findViewById(R.id.enterprise_name);
            villeTV = (TextView) itemView.findViewById(R.id.enterprise_city);
        }

        public void update(Entreprise entreprise){
            this.entreprise = entreprise;
            nomTV.setText(entreprise.getName());
            villeTV.setText(entreprise.getCity());
            //image
            //cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if ( view == cardView){
                listener.onEnterpriseClicked(entreprise);
            }
        }
    }



    public interface OnEnterpriseClickedListener{
        void onEnterpriseClicked(Entreprise entreprise);
    }
}
