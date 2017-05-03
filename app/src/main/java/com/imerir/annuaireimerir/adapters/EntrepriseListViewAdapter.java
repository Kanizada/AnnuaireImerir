package com.imerir.annuaireimerir.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.imerir.annuaireimerir.R;
import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;

import java.util.ArrayList;

/**
 * Created by student on 03/05/2017.
 */

public class EntrepriseListViewAdapter extends BaseAdapter implements SectionIndexer, View.OnClickListener {
    private ArrayList<Entreprise> entreprises;
    private EntrepriseListAdapter.OnEntrepriseClickedListener listener;
    private Context context;

    public EntrepriseListViewAdapter(Context context, ArrayList<Entreprise> entreprises , final EntrepriseListAdapter.OnEntrepriseClickedListener listener) {
        this.entreprises = entreprises;
        this.context = context;
        this.listener = listener;
    }
    public int getCount() {
        return entreprises.size();
    }
    public Entreprise getItem(int arg0) {
        return entreprises.get(arg0);
    }
    public long getItemId(int arg0) {
        return entreprises.get(arg0).getId();
    }
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
        View view = (View) inflate.inflate(R.layout.fragment_entreprise_list_test_row, null);
        LinearLayout header = (LinearLayout) view.findViewById(R.id.section);
        String label = entreprises.get(position).getNom();
        char firstChar = label.toUpperCase().charAt(0);
        if (position == 0) {
            setSection(header, label);
        } else {
            String preLabel = entreprises.get(position - 1).getNom();
            char preFirstChar = preLabel.toUpperCase().charAt(0);
            if (firstChar != preFirstChar) {
                setSection(header, label);
            } else {
                header.setVisibility(View.GONE);
            }
        }
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(label);
        view.setTag(position);
        view.setOnClickListener(this);
        return view;
    }
    private void setSection(LinearLayout header, String label) {
        TextView text = new TextView(context);
        header.setBackgroundColor(0xffaabbcc);
        text.setTextColor(Color.WHITE);
        text.setText(label.substring(0, 1).toUpperCase());
        text.setTextSize(20);
        text.setPadding(5, 0, 0, 0);
        text.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text);
    }
    public int getPositionForSection(int section) {
        if (section == 35) {
            return 0;
        }
        for (int i = 0; i < entreprises.size(); i++) {
            String l = entreprises.get(i).getNom();
            char firstChar = l.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
    public int getSectionForPosition(int arg0) {
        return 0;
    }
    public Object[] getSections() {
        return null;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Entreprise entreprise = entreprises.get(position);
        listener.onEntrepriseClicked(entreprise);
    }
}
