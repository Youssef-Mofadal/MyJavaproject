package controller;

import dao.DBConnection;
import model.Etudiant;
import model.Filiere;
import service.EtudiantService;
import service.FiliereService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class EtudiantController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection connection = DBConnection.getConnection();

        FiliereService filiereService = new FiliereService();
        EtudiantService etudiantService = new EtudiantService();
        etudiantService.setFiliereService(filiereService);

        filiereService.setConnection(connection);
        etudiantService.setConnection(connection);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Créer un étudiant");
            System.out.println("2. Afficher tous les étudiants");
            System.out.println("3. Obtenir un étudiant par ID");
            System.out.println("4. Mettre à jour un étudiant");
            System.out.println("5. Supprimer un étudiant");
            System.out.println("6. Quitter");

            System.out.print("Veuillez saisir votre choix (1-6): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Nom de l'étudiant : ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom de l'étudiant : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Email de l'étudiant : ");
                    String email = scanner.nextLine();
                    System.out.print("Apogée de l'étudiant : ");
                    int apogee = scanner.nextInt();

                    System.out.print("ID de la filière de l'étudiant : ");
                    long filiereId = scanner.nextLong();
                    Filiere filiere = filiereService.getFiliereById(filiereId);

                    Etudiant newEtudiant = new Etudiant();
                    newEtudiant.setNom(nom);
                    newEtudiant.setPrenom(prenom);
                    newEtudiant.setEmail(email);
                    newEtudiant.setApogee(apogee);
                    newEtudiant.setFiliere(filiere);

                    etudiantService.createEtudiant(newEtudiant);
                    System.out.println("Étudiant créé avec succès !");
                    break;

                case 2:
                    List<Etudiant> etudiants = etudiantService.getAllEtudiants();
                    for (Etudiant etudiant : etudiants) {
                        System.out.println(etudiant.getId() + ". " + etudiant.getNom() + " " + etudiant.getPrenom());
                    }
                    break;

                case 3:
                    System.out.print("Veuillez saisir l'ID de l'étudiant : ");
                    long etudiantId = scanner.nextLong();
                    Etudiant etudiantById = etudiantService.getEtudiantById(etudiantId);
                    if (etudiantById != null) {
                        System.out.println("Étudiant trouvé : " + etudiantById.getNom() + " " + etudiantById.getPrenom());
                    } else {
                        System.out.println("Aucun étudiant trouvé avec l'ID spécifié.");
                    }
                    break;

                case 4:
                    System.out.print("Veuillez saisir l'ID de l'étudiant à mettre à jour : ");
                    long updateEtudiantId = scanner.nextLong();
                    scanner.nextLine();

                    Etudiant existingEtudiant = etudiantService.getEtudiantById(updateEtudiantId);
                    if (existingEtudiant != null) {
                        System.out.print("Nouveau nom de l'étudiant : ");
                        String newNom = scanner.nextLine();
                        System.out.print("Nouveau prénom de l'étudiant : ");
                        String newPrenom = scanner.nextLine();
                        System.out.print("Nouvel email de l'étudiant : ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Nouvel apogée de l'étudiant : ");
                        int newApogee = scanner.nextInt();

                        System.out.print("ID de la nouvelle filière de l'étudiant : ");
                        long newFiliereId = scanner.nextLong();
                        Filiere newFiliere = filiereService.getFiliereById(newFiliereId);

                        existingEtudiant.setNom(newNom);
                        existingEtudiant.setPrenom(newPrenom);
                        existingEtudiant.setEmail(newEmail);
                        existingEtudiant.setApogee(newApogee);
                        existingEtudiant.setFiliere(newFiliere);

                        etudiantService.updateEtudiant(existingEtudiant);
                        System.out.println("Étudiant mis à jour avec succès !");
                    } else {
                        System.out.println("Aucun étudiant trouvé avec l'ID spécifié.");
                    }
                    break;

                case 5:
                    System.out.print("Veuillez saisir l'ID de l'étudiant à supprimer : ");
                    long deleteEtudiantId = scanner.nextLong();

                    etudiantService.deleteEtudiant(deleteEtudiantId);
                    System.out.println("Étudiant supprimé avec succès !");
                    break;

                case 6:
                    System.out.println("Au revoir !");
                    System.exit(0);

                default:
                    System.out.println("Choix non valide. Veuillez saisir un nombre entre 1 et 6.");
            }
        }
    }
}

