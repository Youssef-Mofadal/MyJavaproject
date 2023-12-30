package service;

import model.Departement;
import model.Enseignant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementService {

    private EnseignantService enseignantService;
    private Connection connection;


    public DepartementService(){}

    public DepartementService(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    public void setEnseignantService(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }


    Connection getConnection(){
        return connection;
    }

    public void setConnection(Connection connection){
        this.connection=connection;
    }

    public Departement createDepartement(Departement departement) {
        String query;
        if (departement.getResponsable() != null) {
            query = "INSERT INTO Departement (intitule, responsable_id) VALUES (?, ?)";
        } else {
            query = "INSERT INTO Departement (intitule) VALUES (?)";
        }

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, departement.getIntitule());

            if (departement.getResponsable() != null) {
                preparedStatement.setLong(2, departement.getResponsable().getId());
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création du département a échoué, aucune ligne n'a été modifiée.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    departement.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La création du département a échoué, aucun ID généré.");
                }
            }

            return departement;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création du département", e);
        }
    }

    public Departement getDepartementById(Long departementId) {
        String query = "SELECT * FROM Departement WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, departementId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractDepartementFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération du département par ID", e);
        }

        return null;
    }

    public List<Departement> getAllDepartements() {
        List<Departement> departements = new ArrayList<>();
        String query = "SELECT * FROM Departement";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                departements.add(extractDepartementFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de tous les départements", e);
        }

        return departements;
    }

    public void updateDepartement(Departement departement) {
        String query = "UPDATE Departement SET intitule = ?, responsable_id = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, departement.getIntitule());
            preparedStatement.setLong(2, departement.getResponsable().getId());
            preparedStatement.setLong(3, departement.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La mise à jour du département a échoué, aucune ligne n'a été modifiée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du département", e);
        }
    }

    public void deleteDepartement(Long departementId) {
        String query = "DELETE FROM Departement WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, departementId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La suppression du département a échoué, aucune ligne n'a été supprimée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression du département", e);
        }
    }

    private Departement extractDepartementFromResultSet(ResultSet resultSet) throws SQLException {
        Departement departement = new Departement();
        departement.setId(resultSet.getLong("id"));
        departement.setIntitule(resultSet.getString("intitule"));

        long responsableId = resultSet.getLong("responsable_id");
        Enseignant responsable = enseignantService.getEnseignantById(responsableId);
        departement.setResponsable(responsable);

        return departement;
    }
}

