package model;

import java.util.ArrayList;
import java.util.List;

public class Departement   {

    private Long id;
    private String intitule;
    private Enseignant responsable;
    private  List<Filiere> filieres;

    public Departement(Long id, String intitule, Enseignant responsable) {
        this.id=id;
        this.intitule = intitule;
        this.responsable = responsable;
        this.filieres = new ArrayList<>();
    }

    public Departement() {

    }


    public Long getId() {
        return id;
    }


    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Enseignant getResponsable() {
        return responsable;
    }

    public void setResponsable(Enseignant responsable) {
        this.responsable = responsable;
    }

    public List<Filiere> getFilieres() {
        return filieres;
    }

    public void setFilieres(List<Filiere> filieres) {
        this.filieres = filieres;
    }

    @Override
    public String toString() {
        return "Département " + id + " : " +
                intitule + " (Responsable: " + responsable.getNom() + " " + responsable.getPrenom() +")" ;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

