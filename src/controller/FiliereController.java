package controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import dao.DBConnection;
import model.*;
import service.*;
public class FiliereController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection connection= DBConnection.getConnection();
        EnseignantService enseignantService = new EnseignantService();
        DepartementService departementService = new DepartementService(enseignantService);
        FiliereService filiereService = new FiliereService();
        filiereService.setEnseignantService(enseignantService);
        filiereService.setDepartementService(departementService);

        enseignantService.setConnection(connection);
        departementService.setConnection(connection);
        filiereService.setConnection(connection);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Créer une filière");
            System.out.println("2. Afficher toutes les filières");
            System.out.println("3. Obtenir une filière par ID");
            System.out.println("4. Mettre à jour une filière");
            System.out.println("5. Supprimer une filière");
            System.out.println("6. Quitter");

            System.out.print("Veuillez saisir votre choix (1-6): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Intitulé de la filière : ");
                    String intitule = scanner.nextLine();

                    System.out.print("ID du responsable de la filière : ");
                    long responsableId = scanner.nextLong();
                    Enseignant responsable = enseignantService.getEnseignantById(responsableId);

                    System.out.print("ID du département de la filière : ");
                    long departementId = scanner.nextLong();
                    Departement departement = departementService.getDepartementById(departementId);

                    Filiere newFiliere = new Filiere();
                    newFiliere.setIntitule(intitule);
                    newFiliere.setResponsable(responsable);
                    newFiliere.setDepartement(departement);

                    filiereService.createFiliere(newFiliere);
                    System.out.println("Filière créée avec succès !");
                    break;

                case 2:
                    List<Filiere> filieres = filiereService.getAllFilieres();
                    for (Filiere filiere : filieres) {
                        System.out.println(filiere.getId() + ". " + filiere.getIntitule());
                    }
                    break;

                case 3:
                    System.out.print("Veuillez saisir l'ID de la filière : ");
                    long filiereId = scanner.nextLong();
                    Filiere filiereById = filiereService.getFiliereById(filiereId);
                    if (filiereById != null) {
                        System.out.println("Filière trouvée : " + filiereById.getIntitule());
                    } else {
                        System.out.println("Aucune filière trouvée avec l'ID spécifié.");
                    }
                    break;

                case 4:
                    System.out.print("Veuillez saisir l'ID de la filière à mettre à jour : ");
                    long updateFiliereId = scanner.nextLong();
                    scanner.nextLine(); // Pour consommer la nouvelle ligne restante après le nextLong()

                    Filiere existingFiliere = filiereService.getFiliereById(updateFiliereId);
                    if (existingFiliere != null) {
                        System.out.print("Nouvel intitulé de la filière : ");
                        String newIntitule = scanner.nextLine();

                        System.out.print("ID du nouveau responsable de la filière : ");
                        long newResponsableId = scanner.nextLong();
                        Enseignant newResponsable = enseignantService.getEnseignantById(newResponsableId);

                        System.out.print("ID du nouveau département de la filière : ");
                        long newDepartementId = scanner.nextLong();
                        Departement newDepartement = departementService.getDepartementById(newDepartementId);

                        existingFiliere.setIntitule(newIntitule);
                        existingFiliere.setResponsable(newResponsable);
                        existingFiliere.setDepartement(newDepartement);

                        filiereService.updateFiliere(existingFiliere);
                        System.out.println("Filière mise à jour avec succès !");
                    } else {
                        System.out.println("Aucune filière trouvée avec l'ID spécifié.");
                    }
                    break;

                case 5:
                    System.out.print("Veuillez saisir l'ID de la filière à supprimer : ");
                    long deleteFiliereId = scanner.nextLong();

                    filiereService.deleteFiliere(deleteFiliereId);
                    System.out.println("Filière supprimée avec succès !");
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

