package model;

import java.io.Serializable;

public class Module {
    private Long id;
    private  String intitule;
    private model.Filiere filiere;
    private model.Enseignant professeur;

    public Module(Long id, String intitule, model.Filiere filiere, model.Enseignant professeur) {
        this.id=id;
        this.intitule = intitule;
        this.filiere = filiere;
        this.professeur = professeur;
    }

    public Module() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public model.Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(model.Filiere filiere) {
        this.filiere = filiere;
    }

    public model.Enseignant getProfesseur() {
        return professeur;
    }

    public void setProfesseur(model.Enseignant professeur) {
        this.professeur = professeur;
    }

    @Override
    public String toString() {
        return "Module "+ id+" : "+
                intitule + " (Filière: " + filiere.getIntitule() + ", Professeur: " + professeur.getNom() + " " + professeur.getPrenom() + ")";

    }
}

