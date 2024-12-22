package com.example;

public class App {
    public static void main(String[] args) {
        Etudiant etudiant = new Etudiant("Meissa Youssef");
        etudiant.ajouterNote(15.0);
        etudiant.ajouterNote(18.5);
        etudiant.ajouterNote(12.0);

        System.out.println("Moyenne Of " + etudiant.getNom() + "Is : " + etudiant.calculerMoyenne());
    }
}
