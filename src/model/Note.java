package model;

public class Note {

    private float note;
    private model.Etudiant etudiant;
    private model.Filiere filiere;

    public Note(float note, model.Etudiant etudiant, model.Filiere filiere) {
        this.note = note;
        this.etudiant = etudiant;
        this.filiere = filiere;
    }

    public Note() {

    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public model.Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(model.Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public model.Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(model.Filiere filiere) {
        this.filiere = filiere;
    }
}