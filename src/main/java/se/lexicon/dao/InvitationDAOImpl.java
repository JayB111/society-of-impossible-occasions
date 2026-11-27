package se.lexicon.dao;

import se.lexicon.config.DatabaseConnection;
import se.lexicon.exception.DatabaseException;
import se.lexicon.model.Invitation;
import se.lexicon.model.InvitationStatus;
import se.lexicon.model.Event;
import se.lexicon.model.Participant;
import se.lexicon.exception.EventCapacityException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Implements the invitation database operations
public class InvitationDAOImpl implements InvitationDAO {
    //Saves a new invitation with pending status
    @Override
    public Invitation save(Invitation invitation) {
        // Ensures new invitations always start as pending
        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new IllegalArgumentException(
                    "New invitations must have pending status.");
        }
        String sql = """
            INSERT INTO invitations
            (participant_id, event_id, status)
            VALUES (?, ?, ?)
            """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, invitation.getParticipant().getId());
            statement.setInt(2, invitation.getEvent().getId());
            statement.setString(3, invitation.getStatus().name());
            statement.executeUpdate();
            //Retrieves the generated invitation ID
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Invitation(
                            keys.getInt(1),
                            invitation.getEvent(),
                            invitation.getParticipant(),
                            invitation.getStatus());
                }
            }
            throw new SQLException("No invitation ID generated.");
        } catch (SQLException e) {
            throw new DatabaseException("Could not save invitation.", e);
        }
    }
    //Retrieves an invitation by ID
    @Override
    public Optional<Invitation> findById(int id) {
        String sql = "SELECT * FROM invitations WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapInvitation(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Could not find invitation.", e);
        }
    }
    //Retrieves all invitations from the database
    @Override
    public List<Invitation> findAll() {
        List<Invitation> invitations = new ArrayList<>();
        String sql = "SELECT * FROM invitations ORDER BY id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                invitations.add(mapInvitation(resultSet));
            }
            return invitations;
        } catch (SQLException e) {
            throw new DatabaseException("Could not retrieve invitations.", e);
        }
    }
    //Retrieves invitations for a specific event
    @Override
    public List<Invitation> findByEventId(int eventId) {
        List<Invitation> invitations = new ArrayList<>();
        String sql = "SELECT * FROM invitations WHERE event_id = ? ORDER BY id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    invitations.add(mapInvitation(resultSet));
                }
            }
            return invitations;
        } catch (SQLException e) {
            throw new DatabaseException("Could not retrieve event invitations.", e);
        }
    }
    //Retrieves invitations for a specific participant
    @Override
    public List<Invitation> findByParticipantId(int participantId) {
        List<Invitation> invitations = new ArrayList<>();
        String sql = "SELECT * FROM invitations WHERE participant_id = ? ORDER BY id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, participantId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    invitations.add(mapInvitation(resultSet));
                }
            }
            return invitations;
        } catch (SQLException e) {
            throw new DatabaseException("Could not retrieve participant invitations.", e);
        }
    }
    //Checks whether a participant has already been invited
    @Override
    public boolean exists(int participantId, int eventId) {
        String sql = """
            SELECT 1 FROM invitations
            WHERE participant_id = ? AND event_id = ?
            """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, participantId);
            statement.setInt(2, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not check invitation.", e);
        }
    }
    // Counts accepted invitations for a specific event
    @Override
    public int countAcceptedByEventId(int eventId) {
        String sql = """
            SELECT COUNT(*) FROM invitations
            WHERE event_id = ? AND status = 'ACCEPTED'
            """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new DatabaseException("Could not count accepted invitations.", e);
        }
    }
    //Updates invitation status and prevents event overbooking
    @Override
    public boolean updateStatus(int id, InvitationStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Invitation status is required.");
        }
        String eventSql = """
            SELECT e.id, e.capacity
            FROM events e
            JOIN invitations i ON e.id = i.event_id
            WHERE i.id = ?
            FOR UPDATE
            """;
        String countSql = """
            SELECT COUNT(*) FROM invitations
            WHERE event_id = ?
            AND status = 'ACCEPTED'
            AND id <> ?
            """;
        String updateSql = """
            UPDATE invitations
            SET status = ?
            WHERE id = ?
            """;
        try (Connection connection = DatabaseConnection.getConnection()) {
            //Starts the transaction
            connection.setAutoCommit(false);
            try {
                int eventId;
                int capacity;
                //Locks the event and retrieves its capacity
                try (PreparedStatement statement =
                             connection.prepareStatement(eventSql)) {
                    statement.setInt(1, id);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            connection.rollback();
                            return false;
                        }
                        eventId = resultSet.getInt("id");
                        capacity = resultSet.getInt("capacity");
                    }
                }
                //Checks capacity before accepting an invitation
                if (status == InvitationStatus.ACCEPTED) {
                    try (PreparedStatement statement =
                                 connection.prepareStatement(countSql)) {
                        statement.setInt(1, eventId);
                        statement.setInt(2, id);
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()
                                    && resultSet.getInt(1) >= capacity) {
                                throw new EventCapacityException(
                                        "This event is fully booked.");
                            }
                        }
                    }
                }

                //Updates the invitation status
                boolean updated;
                try (PreparedStatement statement =
                             connection.prepareStatement(updateSql)) {
                    statement.setString(1, status.name());
                    statement.setInt(2, id);
                    updated = statement.executeUpdate() > 0;
                }
                //Saves the transaction
                connection.commit();
                return updated;
            } catch (SQLException | RuntimeException e) {
                //Undoes changes if an error occurs
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
                throw e;
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Could not update invitation status.", e);
        }
    }
    //Deletes an invitation from the database
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM invitations WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Could not delete invitation.", e);
        }
    }
    //Converts a database record into an Invitation object
    private Invitation mapInvitation(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int participantId = resultSet.getInt("participant_id");
        int eventId = resultSet.getInt("event_id");
        InvitationStatus status = InvitationStatus.valueOf(
                resultSet.getString("status"));
        ParticipantDAO participantDAO = new ParticipantDAOImpl();
        EventDAO eventDAO = new EventDAOImpl();
        Participant participant = participantDAO.findById(participantId)
                .orElseThrow();
        Event event = eventDAO.findById(eventId)
                .orElseThrow();
        return new Invitation(id, event, participant, status);
    }
}