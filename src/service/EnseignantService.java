package service;

import model.Enseignant;
import model.Departement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantService {

    private service.DepartementService departementService;
    private Connection connection;

    public EnseignantService(service.DepartementService departementService) {
        this.departementService = departementService;
    }

    public EnseignantService() {}

    public service.DepartementService getDepartementService() {
        return departementService;
    }

    Connection getConnection(){
        return connection;
    }

    public void setConnection(Connection connection){
        this.connection=connection;
    }
    public Enseignant createEnseignant(Enseignant enseignant) {
        String query = "INSERT INTO Enseignant (nom, prenom, email, grade, departement_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, enseignant.getNom());
            preparedStatement.setString(2, enseignant.getPrenom());
            preparedStatement.setString(3, enseignant.getEmail());
            preparedStatement.setString(4, enseignant.getGrade());
            preparedStatement.setLong(5, enseignant.getDepartement().getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de l'enseignant a échoué, aucune ligne n'a été modifiée.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    enseignant.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La création de l'enseignant a échoué, aucun ID généré.");
                }
            }
            return enseignant;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de l'enseignant", e);
        }
    }

    public Enseignant getEnseignantById(Long enseignantId) {
        String query = "SELECT * FROM Enseignant WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, enseignantId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractEnseignantFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de l'enseignant par ID", e);
        }

        return null;
    }

    public List<Enseignant> getAllEnseignants() {
        List<Enseignant> enseignants = new ArrayList<>();
        String query = "SELECT * FROM Enseignant";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                enseignants.add(extractEnseignantFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de tous les enseignants", e);
        }

        return enseignants;
    }

    public void updateEnseignant(Enseignant enseignant) {
        String query = "UPDATE Enseignant SET nom = ?, prenom = ?, email = ?, grade = ?, departement_id = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, enseignant.getNom());
            preparedStatement.setString(2, enseignant.getPrenom());
            preparedStatement.setString(3, enseignant.getEmail());
            preparedStatement.setString(4, enseignant.getGrade());
            preparedStatement.setLong(5, enseignant.getDepartement().getId());
            preparedStatement.setLong(6, enseignant.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La mise à jour de l'enseignant a échoué, aucune ligne n'a été modifiée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'enseignant", e);
        }
    }

    public void deleteEnseignant(Long enseignantId) {
        String query = "DELETE FROM Enseignant WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, enseignantId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La suppression de l'enseignant a échoué, aucune ligne n'a été supprimée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de l'enseignant", e);
        }
    }

    private Enseignant extractEnseignantFromResultSet(ResultSet resultSet) throws SQLException {
        Enseignant enseignant = new Enseignant();
        enseignant.setId(resultSet.getLong("id"));
        enseignant.setNom(resultSet.getString("nom"));
        enseignant.setPrenom(resultSet.getString("prenom"));
        enseignant.setEmail(resultSet.getString("email"));
        enseignant.setGrade(resultSet.getString("grade"));

        long departementId = resultSet.getLong("departement_id");
        Departement departement = departementService.getDepartementById(departementId);
        enseignant.setDepartement(departement);

        return enseignant;
    }
}

