package controller;

import dao.DBConnection;
import model.Departement;
import service.DepartementService;
import service.EnseignantService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class DepartementController {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection connection = DBConnection.getConnection();
        EnseignantService enseignantService = new EnseignantService();
        DepartementService departementService = new DepartementService(enseignantService);

        enseignantService.setConnection(connection);
        departementService.setConnection(connection);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Créer un département");
            System.out.println("2. Afficher tous les départements");
            System.out.println("3. Obtenir un département par ID");
            System.out.println("4. Mettre à jour un département");
            System.out.println("5. Supprimer un département");
            System.out.println("6. Quitter");

            System.out.print("Veuillez saisir votre choix (1-6): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Intitulé du département : ");
                    String intitule = scanner.nextLine();

                    Departement newDepartement = new Departement();
                    newDepartement.setIntitule(intitule);

                    departementService.createDepartement(newDepartement);
                    System.out.println("Département créé avec succès !");
                    break;

                case 2:
                    List<Departement> departements = departementService.getAllDepartements();
                    for (Departement departement : departements) {
                        System.out.println(departement.getId() + ". " + departement.getIntitule());
                    }
                    break;

                case 3:
                    System.out.print("Veuillez saisir l'ID du département : ");
                    long departementId = scanner.nextLong();
                    Departement departementById = departementService.getDepartementById(departementId);
                    if (departementById != null) {
                        System.out.println("Département trouvé : " + departementById.getIntitule());
                    } else {
                        System.out.println("Aucun département trouvé avec l'ID spécifié.");
                    }
                    break;

                case 4:
                    System.out.print("Veuillez saisir l'ID du département à mettre à jour : ");
                    long updateDepartementId = scanner.nextLong();
                    scanner.nextLine(); // Pour consommer la nouvelle ligne restante après le nextLong()

                    Departement existingDepartement = departementService.getDepartementById(updateDepartementId);
                    if (existingDepartement != null) {
                        System.out.print("Nouvel intitulé du département : ");
                        String newIntitule = scanner.nextLine();
                        existingDepartement.setIntitule(newIntitule);

                        departementService.updateDepartement(existingDepartement);
                        System.out.println("Département mis à jour avec succès !");
                    } else {
                        System.out.println("Aucun département trouvé avec l'ID spécifié.");
                    }
                    break;

                case 5:
                    System.out.print("Veuillez saisir l'ID du département à supprimer : ");
                    long deleteDepartementId = scanner.nextLong();

                    departementService.deleteDepartement(deleteDepartementId);
                    System.out.println("Département supprimé avec succès !");
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
