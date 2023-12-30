package model;

import java.io.Serializable;

public class Enseignant   {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String grade;
    private model.Departement departement;

    public Enseignant(Long id, String nom, String prenom, String email, String grade, model.Departement departement) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.grade = grade;
        this.departement = departement;
    }

    public Enseignant() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public model.Departement getDepartement() {
        return departement;
    }

    public void setDepartement(model.Departement departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        return "Enseignant " + id + " : " +

                nom + " " + prenom + " ( " +email + " ) " + "Grade : "+grade + "   Département : "+ departement.getIntitule()
                ;
    }
}
