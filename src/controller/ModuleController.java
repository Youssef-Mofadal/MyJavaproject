package controller;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import dao.DBConnection;
import model.*;
import model.Module;
import service.*;
public class ModuleController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DBConnection.getConnection();
        EnseignantService enseignantService = new EnseignantService();
        DepartementService departementService = new DepartementService(enseignantService);
        FiliereService filiereService = new FiliereService();
        filiereService.setEnseignantService(enseignantService);
        filiereService.setDepartementService(departementService);
        ModuleService moduleService = new ModuleService();
        moduleService.setFiliereService(filiereService);
        moduleService.setEnseignantService(enseignantService);

        enseignantService.setConnection(connection);
        departementService.setConnection(connection);
        moduleService.setConnetion(connection);
        filiereService.setConnection(connection);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Créer un module");
            System.out.println("2. Afficher tous les modules");
            System.out.println("3. Obtenir un module par ID");
            System.out.println("4. Mettre à jour un module");
            System.out.println("5. Supprimer un module");
            System.out.println("6. Quitter");

            System.out.print("Veuillez saisir votre choix (1-6): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Intitulé du module : ");
                    String intitule = scanner.nextLine();

                    System.out.print("ID de la filière du module : ");
                    long filiereId = scanner.nextLong();
                    Filiere filiere = filiereService.getFiliereById(filiereId);

                    System.out.print("ID du professeur du module : ");
                    long professeurId = scanner.nextLong();
                    Enseignant professeur = enseignantService.getEnseignantById(professeurId);

                    Module newModule = new Module();
                    newModule.setIntitule(intitule);
                    newModule.setFiliere(filiere);
                    newModule.setProfesseur(professeur);

                    moduleService.createModule(newModule);
                    System.out.println("Module créé avec succès !");
                    break;

                case 2:
                    List<Module> modules = moduleService.getAllModules();
                    for (Module module : modules) {
                        System.out.println(module.getId() + ". " + module.getIntitule());
                    }
                    break;

                case 3:
                    System.out.print("Veuillez saisir l'ID du module : ");
                    long moduleId = scanner.nextLong();
                    Module moduleById = moduleService.getModuleById(moduleId);
                    if (moduleById != null) {
                        System.out.println("Module trouvé : " + moduleById.getIntitule());
                    } else {
                        System.out.println("Aucun module trouvé avec l'ID spécifié.");
                    }
                    break;

                case 4:
                    System.out.print("Veuillez saisir l'ID du module à mettre à jour : ");
                    long updateModuleId = scanner.nextLong();
                    scanner.nextLine();

                    Module existingModule = moduleService.getModuleById(updateModuleId);
                    if (existingModule != null) {
                        System.out.print("Nouvel intitulé du module : ");
                        String newIntitule = scanner.nextLine();

                        System.out.print("ID de la nouvelle filière du module : ");
                        long newFiliereId = scanner.nextLong();
                        Filiere newFiliere = filiereService.getFiliereById(newFiliereId);

                        System.out.print("ID du nouveau professeur du module : ");
                        long newProfesseurId = scanner.nextLong();
                        Enseignant newProfesseur = enseignantService.getEnseignantById(newProfesseurId);

                        existingModule.setIntitule(newIntitule);
                        existingModule.setFiliere(newFiliere);
                        existingModule.setProfesseur(newProfesseur);

                        moduleService.updateModule(existingModule);
                        System.out.println("Module mis à jour avec succès !");
                    } else {
                        System.out.println("Aucun module trouvé avec l'ID spécifié.");
                    }
                    break;

                case 5:
                    System.out.print("Veuillez saisir l'ID du module à supprimer : ");
                    long deleteModuleId = scanner.nextLong();

                    moduleService.deleteModule(deleteModuleId);
                    System.out.println("Module supprimé avec succès !");
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

