package com.example;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Etudiant {
    private String nom;
    private List<Double> notes = new ArrayList<>();

    public Etudiant(String nom) {
        this.nom = nom;
    }

    public void ajouterNote(double note) {
        notes.add(note);
    }

    public double calculerMoyenne() {
        if (notes.isEmpty()) {
            return 0.0;
        }
        double somme = 0;
        for (double note : notes) {
            somme += note;
        }
        return somme / notes.size();
    }
}
