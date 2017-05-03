package com.imerir.annuaireimerir.tools;

import com.imerir.annuaireimerir.models.Eleve;

import java.util.Comparator;

/**
 * Created by student on 03/05/2017.
 */

public class ComparatorNomEleve implements Comparator<Eleve> {
    @Override
    public int compare(Eleve e1, Eleve e2) {
        return e1.getNom().compareTo(e2.getNom());
    }
}
