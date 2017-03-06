package com.imerir.annuaireimerir.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Promotion;

import java.util.ArrayList;

/**
 * Created by student on 06/03/2017.
 */

public class PromotionListAdapter extends RecyclerView.Adapter<PromotionListAdapter.PromotionViewHolder> {

    ArrayList<Promotion> promotions;
    OnPromotionClickedListener listener;

    public PromotionListAdapter(ArrayList<Promotion> promotions, OnPromotionClickedListener listener){
        this.promotions = promotions;
        this.listener = listener;
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion, parent, false);
        return new PromotionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PromotionViewHolder holder, int position) {
        Promotion promotion = promotions.get(position);
        holder.update(promotion);
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardView;
        ImageView image;
        TextView nomTV;
        TextView anneeTV;
        Promotion promotion;


        public PromotionViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            image = (ImageView) itemView.findViewById(R.id.promotion_image);
            nomTV = (TextView) itemView.findViewById(R.id.promotion_nom);
            anneeTV = (TextView) itemView.findViewById(R.id.promotion_annee);
        }
        public void update(Promotion promotion){
            nomTV.setText(promotion.getNom());
            anneeTV.setText(promotion.getAnnee());
            //image
            //cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == cardView){
                listener.onPromotionClicked(promotion);
            }
        }
    }

    public interface OnPromotionClickedListener {
        void onPromotionClicked(Promotion promotion);
    }
}
