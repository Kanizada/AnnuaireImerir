package com.imerir.annuaireimerir.models;

import com.imerir.annuaireimerir.models.Eleve;
import com.imerir.annuaireimerir.models.Entreprise;

import java.util.Comparator;

/**
 * Created by student on 03/05/2017.
 */

public class ComparatorNomEntreprise implements Comparator<Entreprise> {
    @Override
    public int compare(Entreprise e1, Entreprise e2) {
        return e1.getNom().compareTo(e2.getNom());
    }
}
