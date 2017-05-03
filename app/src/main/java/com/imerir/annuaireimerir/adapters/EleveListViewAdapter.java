package com.imerir.annuaireimerir.adapters;

import java.util.ArrayList;
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

public class EleveListViewAdapter extends BaseAdapter implements SectionIndexer, View.OnClickListener {
    private ArrayList<Eleve> eleves;
    private EleveListAdapter.OnEleveClickedListener listener;
    private Context context;

    public EleveListViewAdapter(Context context, ArrayList<Eleve> eleves , final EleveListAdapter.OnEleveClickedListener listener) {
        this.eleves = eleves;
        this.context = context;
        this.listener = listener;
    }
    public int getCount() {
        return eleves.size();
    }
    public Eleve getItem(int arg0) {
        return eleves.get(arg0);
    }
    public long getItemId(int arg0) {
        return eleves.get(arg0).getId();
    }
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
        View view = (View) inflate.inflate(R.layout.fragment_eleve_list_test_row, null);
        LinearLayout header = (LinearLayout) view.findViewById(R.id.section);
        String label = eleves.get(position).getNom();
        String prenom = eleves.get(position).getPrenom();
        char firstChar = label.toUpperCase().charAt(0);
        if (position == 0) {
            setSection(header, label);
        } else {
            String preLabel = eleves.get(position - 1).getNom();
            char preFirstChar = preLabel.toUpperCase().charAt(0);
            if (firstChar != preFirstChar) {
                setSection(header, label);
            } else {
                header.setVisibility(View.GONE);
            }
        }
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(label + " " + prenom);
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
        for (int i = 0; i < eleves.size(); i++) {
            String l = eleves.get(i).getNom();
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
        Eleve eleve = eleves.get(position);
        listener.onEleveClicked(eleve);
    }
}