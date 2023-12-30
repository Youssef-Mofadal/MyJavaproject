package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Filiere   {
    private Long id;
    private String intitule;
    private model.Enseignant responsable;
    private model.Departement departement;
    private  ArrayList<Module> modules;

    public Filiere(Long id, String intitule, model.Enseignant responsable, model.Departement departement) {
        this.id=id;
        this.intitule = intitule;
        this.responsable = responsable;
        this.departement = departement;
        this.modules = new ArrayList<>();
    }

    public Filiere() {

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

    public model.Enseignant getResponsable() {
        return responsable;
    }

    public void setResponsable(model.Enseignant responsable) {
        this.responsable = responsable;
    }

    public model.Departement getDepartement() {
        return departement;
    }

    public void setDepartement(model.Departement departement) {
        this.departement = departement;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }


    @Override
    public String toString() {
        return "Filière "+ id+ " : " +
                intitule + " (Responsable: " + responsable.getNom() + " " + responsable.getPrenom() + ", Département: " + departement.getIntitule() + ")" ;

    }
}
