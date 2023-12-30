package service;


import model.Enseignant;
import model.Filiere;
import model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ModuleService {


    private Connection connetion;

    private FiliereService filiereService;
    private EnseignantService enseignantService;

    public Connection getConnection() {
        return connetion;
    }

    public void setConnetion(Connection connetion) {
        this.connetion = connetion;
    }


    public void setFiliereService(FiliereService filiereService) {
        this.filiereService = filiereService;
    }


    public void setEnseignantService(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    public Module createModule(Module module) {
        String query = "INSERT INTO Module (intitule, filiere_id, professeur_id) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, module.getIntitule());
            preparedStatement.setLong(2, module.getFiliere().getId());
            preparedStatement.setLong(3, module.getProfesseur().getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création du module a échoué, aucune ligne n'a été modifiée.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    module.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La création du module a échoué, aucun ID généré.");
                }
            }

            return module;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création du module", e);
        }
    }

    public Module getModuleById(Long moduleId) {
        String query = "SELECT * FROM Module WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, moduleId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractModuleFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération du module par ID", e);
        }

        return null;
    }

    public List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT * FROM Module";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                modules.add(extractModuleFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de tous les modules", e);
        }

        return modules;
    }

    public void updateModule(Module module) {
        String query = "UPDATE Module SET intitule = ?, filiere_id = ?, professeur_id = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, module.getIntitule());
            preparedStatement.setLong(2, module.getFiliere().getId());
            preparedStatement.setLong(3, module.getProfesseur().getId());
            preparedStatement.setLong(4, module.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La mise à jour du module a échoué, aucune ligne n'a été modifiée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du module", e);
        }
    }

    public void deleteModule(Long moduleId) {
        String query = "DELETE FROM Module WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, moduleId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La suppression du module a échoué, aucune ligne n'a été supprimée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression du module", e);
        }
    }

    private Module extractModuleFromResultSet(ResultSet resultSet) throws SQLException {
        Module module = new Module();
        module.setId(resultSet.getLong("id"));
        module.setIntitule(resultSet.getString("intitule"));

        long filiereId = resultSet.getLong("filiere_id");
        Filiere filiere = filiereService.getFiliereById(filiereId);
        module.setFiliere(filiere);

        long professeurId = resultSet.getLong("professeur_id");
        Enseignant professeur = enseignantService.getEnseignantById(professeurId);
        module.setProfesseur(professeur);

        return module;
    }



}






