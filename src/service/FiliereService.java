package service;

import model.Departement;
import model.Enseignant;
import model.Filiere;
import model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FiliereService {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private EnseignantService enseignantService;
    private DepartementService departementService;


    public void setEnseignantService(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    public void setDepartementService(DepartementService departementService) {
        this.departementService = departementService;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Filiere createFiliere(Filiere filiere) {
        String query = "INSERT INTO Filiere (intitule, responsable_id, departement_id) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, filiere.getIntitule());
            preparedStatement.setLong(2, filiere.getResponsable().getId());
            preparedStatement.setLong(3, filiere.getDepartement().getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de la filière a échoué, aucune ligne n'a été modifiée.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    filiere.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La création de la filière a échoué, aucun ID généré.");
                }
            }

            return filiere;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de la filière", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Filiere getFiliereById(Long filiereId) {
        String query = "SELECT * FROM Filiere WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, filiereId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractFiliereFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de la filière par ID", e);
        }

        return null;
    }

    public List<Filiere> getAllFilieres() {
        List<Filiere> filieres = new ArrayList<>();
        String query = "SELECT * FROM Filiere";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                filieres.add(extractFiliereFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de toutes les filières", e);
        }

        return filieres;
    }

    public void updateFiliere(Filiere filiere) {
        String query = "UPDATE Filiere SET intitule = ?, responsable_id = ?, departement_id = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, filiere.getIntitule());
            preparedStatement.setLong(2, filiere.getResponsable().getId());
            preparedStatement.setLong(3, filiere.getDepartement().getId());
            preparedStatement.setLong(4, filiere.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La mise à jour de la filière a échoué, aucune ligne n'a été modifiée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de la filière", e);
        }
    }

    public void deleteFiliere(Long filiereId) {
        String query = "DELETE FROM Filiere WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, filiereId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La suppression de la filière a échoué, aucune ligne n'a été supprimée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de la filière", e);
        }
    }

    private Filiere extractFiliereFromResultSet(ResultSet resultSet) throws SQLException {
        Filiere filiere = new Filiere();
        filiere.setId(resultSet.getLong("id"));
        filiere.setIntitule(resultSet.getString("intitule"));

        long responsableId = resultSet.getLong("responsable_id");
        Enseignant responsable = enseignantService.getEnseignantById(responsableId);
        filiere.setResponsable(responsable);

        long departementId = resultSet.getLong("departement_id");
        Departement departement = departementService.getDepartementById(departementId);
        filiere.setDepartement(departement);

        ArrayList<Module> modules = (ArrayList<Module>) getModulesByFiliereId(filiere.getId());
        filiere.setModules(modules);

        return filiere;
    }






    private List<Module> getModulesByFiliereId(Long filiereId) {
        return getFiliereById(filiereId).getModules();
    }
}