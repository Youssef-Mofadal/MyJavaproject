package service;

import model.*;
import model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NoteService {

    private Connection connection;
    private FiliereService filiereService;
    private EtudiantService etudiantService;
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setFiliereService(FiliereService filiereService) {
        this.filiereService = filiereService;
    }

    public void setEtudiantService(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    public Note createNote(Note note) {
        String query = "INSERT INTO Note (note, etudiant_id, filiere_id) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setFloat(1, note.getNote());
            preparedStatement.setLong(2, note.getEtudiant().getId());
            preparedStatement.setLong(3, note.getFiliere().getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de la note a échoué, aucune ligne n'a été modifiée.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                } else {
                    throw new SQLException("La création de la note a échoué, aucun ID généré.");
                }
            }

            return note;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de la note", e);
        }
    }

    public List<Note> getNotesByEtudiantId(Long etudiantId) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM Note WHERE etudiant_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, etudiantId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    notes.add(extractNoteFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des notes par ID de l'étudiant", e);
        }

        return notes;
    }

    public List<Note> getNotesByFiliereId(Long filiereId) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM Note WHERE filiere_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, filiereId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    notes.add(extractNoteFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des notes par ID de la filière", e);
        }

        return notes;
    }

    private Note extractNoteFromResultSet(ResultSet resultSet) throws SQLException {
        Note note = new Note();
        note.setNote(resultSet.getFloat("note"));

        // Récupérer l'étudiant associé
        long etudiantId = resultSet.getLong("etudiant_id");
        Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);
        note.setEtudiant(etudiant);

        // Récupérer la filière associée
        long filiereId = resultSet.getLong("filiere_id");
        Filiere filiere = filiereService.getFiliereById(filiereId);
        note.setFiliere(filiere);

        return note;
    }



}

