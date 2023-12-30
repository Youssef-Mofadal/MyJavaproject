package service;




import dao.DBConnection;
import model.Etudiant;
import model.Filiere;
import model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantService {

    private Connection connection;
    private FiliereService filiereService;

    public void setFiliereService(FiliereService filiereService) {
        this.filiereService = filiereService;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Etudiant createEtudiant(Etudiant etudiant) {
        String query = "INSERT INTO Etudiant (nom, prenom, email, apogee, filiere_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setString(3, etudiant.getEmail());
            preparedStatement.setInt(4, etudiant.getApogee());
            preparedStatement.setLong(5, etudiant.getFiliere().getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de l'étudiant a échoué, aucune ligne n'a été modifiée.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    etudiant.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La création de l'étudiant a échoué, aucun ID généré.");
                }
            }

            return etudiant;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de l'étudiant", e);
        }
    }

    public Etudiant getEtudiantById(Long etudiantId) {
        String query = "SELECT * FROM Etudiant WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, etudiantId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractEtudiantFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de l'étudiant par ID", e);
        }

        return null;
    }

    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        String query = "SELECT * FROM Etudiant";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                etudiants.add(extractEtudiantFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de tous les étudiants", e);
        }

        return etudiants;
    }

    public void updateEtudiant(Etudiant etudiant) {
        String query = "UPDATE Etudiant SET nom = ?, prenom = ?, email = ?, apogee = ?, filiere_id = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setString(3, etudiant.getEmail());
            preparedStatement.setInt(4, etudiant.getApogee());
            preparedStatement.setLong(5, etudiant.getFiliere().getId());
            preparedStatement.setLong(6, etudiant.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La mise à jour de l'étudiant a échoué, aucune ligne n'a été modifiée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'étudiant", e);
        }
    }

    public void deleteEtudiant(Long etudiantId) {
        String query = "DELETE FROM Etudiant WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, etudiantId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La suppression de l'étudiant a échoué, aucune ligne n'a été supprimée.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de l'étudiant", e);
        }
    }

    private Etudiant extractEtudiantFromResultSet(ResultSet resultSet) throws SQLException {
        Etudiant etudiant = new Etudiant();
        etudiant.setId(resultSet.getLong("id"));
        etudiant.setNom(resultSet.getString("nom"));
        etudiant.setPrenom(resultSet.getString("prenom"));
        etudiant.setEmail(resultSet.getString("email"));
        etudiant.setApogee(resultSet.getInt("apogee"));

        long filiereId = resultSet.getLong("filiere_id");
        Filiere filiere = filiereService.getFiliereById(filiereId);
        etudiant.setFiliere(filiere);

        ArrayList<Note> notes = (ArrayList<Note>) getNotesByEtudiantId(etudiant.getId());
        etudiant.setNotes(notes);

        return etudiant;
    }




    private List<Note> getNotesByEtudiantId(Long etudiantId) {
        return getEtudiantById(etudiantId).getNotes();
    }
}


