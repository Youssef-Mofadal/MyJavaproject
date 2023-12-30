package controller;

import dao.DBConnection;
import model.Departement;
import model.Enseignant;
import service.DepartementService;
import service.EnseignantService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class EnseignantController {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DBConnection.getConnection();

        DepartementService departementService = new DepartementService();
        EnseignantService enseignantService = new EnseignantService(departementService);
        enseignantService.setConnection(connection);
        departementService.setConnection(connection);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Créer un enseignant");
            System.out.println("2. Afficher tous les enseignants");
            System.out.println("3. Obtenir un enseignant par ID");
            System.out.println("4. Mettre à jour un enseignant");
            System.out.println("5. Supprimer un enseignant");
            System.out.println("6. Quitter");

            System.out.print("Veuillez saisir votre choix (1-6): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Nom de l'enseignant : ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom de l'enseignant : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Email de l'enseignant : ");
                    String email = scanner.nextLine();
                    System.out.print("Grade de l'enseignant : ");
                    String grade = scanner.nextLine();

                    System.out.print("ID du département de l'enseignant : ");
                    long departementId = scanner.nextLong();
                    Departement departement = departementService.getDepartementById(departementId);

                    Enseignant newEnseignant = new Enseignant();
                    newEnseignant.setNom(nom);
                    newEnseignant.setPrenom(prenom);
                    newEnseignant.setEmail(email);
                    newEnseignant.setGrade(grade);
                    newEnseignant.setDepartement(departement);

                    enseignantService.createEnseignant(newEnseignant);
                    System.out.println("Enseignant créé avec succès !");
                    break;

                case 2:
                    List<Enseignant> enseignants = enseignantService.getAllEnseignants();
                    for (Enseignant enseignant : enseignants) {
                        System.out.println(enseignant.getId() + ". " + enseignant.getNom() + " " + enseignant.getPrenom());
                    }
                    break;

                case 3:
                    System.out.print("Veuillez saisir l'ID de l'enseignant : ");
                    long enseignantId = scanner.nextLong();
                    Enseignant enseignantById = enseignantService.getEnseignantById(enseignantId);
                    if (enseignantById != null) {
                        System.out.println("Enseignant trouvé : " + enseignantById.getNom() + " " + enseignantById.getPrenom());
                    } else {
                        System.out.println("Aucun enseignant trouvé avec l'ID spécifié.");
                    }
                    break;

                case 4:
                    System.out.print("Veuillez saisir l'ID de l'enseignant à mettre à jour : ");
                    long updateEnseignantId = scanner.nextLong();
                    scanner.nextLine();

                    Enseignant existingEnseignant = enseignantService.getEnseignantById(updateEnseignantId);
                    if (existingEnseignant != null) {
                        System.out.print("Nouveau nom de l'enseignant : ");
                        String newNom = scanner.nextLine();
                        System.out.print("Nouveau prénom de l'enseignant : ");
                        String newPrenom = scanner.nextLine();
                        System.out.print("Nouvel email de l'enseignant : ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Nouveau grade de l'enseignant : ");
                        String newGrade = scanner.nextLine();

                        System.out.print("ID du nouveau département de l'enseignant : ");
                        long newDepartementId = scanner.nextLong();
                        Departement newDepartement = departementService.getDepartementById(newDepartementId);

                        existingEnseignant.setNom(newNom);
                        existingEnseignant.setPrenom(newPrenom);
                        existingEnseignant.setEmail(newEmail);
                        existingEnseignant.setGrade(newGrade);
                        existingEnseignant.setDepartement(newDepartement);

                        enseignantService.updateEnseignant(existingEnseignant);
                        System.out.println("Enseignant mis à jour avec succès !");
                    } else {
                        System.out.println("Aucun enseignant trouvé avec l'ID spécifié.");
                    }
                    break;

                case 5:
                    System.out.print("Veuillez saisir l'ID de l'enseignant à supprimer : ");
                    long deleteEnseignantId = scanner.nextLong();

                    enseignantService.deleteEnseignant(deleteEnseignantId);
                    System.out.println("Enseignant supprimé avec succès !");
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

