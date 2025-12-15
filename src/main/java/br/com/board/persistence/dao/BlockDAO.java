package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.board.persistence.config.ConnectionConfig;
import br.com.board.persistence.entity.BlockEntity;

public class BlockDAO {
    public BlockEntity insert(final BlockEntity entity) throws SQLException {
        String sql = "INSERT INTO BLOCKS (blocked_reason, blocked_at, card_id) VALUES (?, ?, ?);";
        
        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, entity.getBlockedReason());
            statement.setObject(2, entity.getBlockedAt());
            statement.setLong(3, entity.getCardId());
            statement.executeUpdate();
            
            try(var generatedKeys = statement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        }
        return entity;
    }

    public boolean isBlocked(final Long cardId) throws SQLException {
        String sql = "SELECT id FROM BLOCKS WHERE card_id = ? AND unblocked_at IS NULL";

        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, cardId);
            
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void unblock(final Long cardId, final String reason) throws SQLException {
        String sql = "UPDATE BLOCKS SET unblocked_at = ?, unblocked_reason = ? WHERE card_id = ? AND unblocked_at IS NULL";
        
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setObject(1, java.time.OffsetDateTime.now()); // Data de agora
            statement.setString(2, reason);
            statement.setLong(3, cardId);
            
            statement.executeUpdate();
        }
    }
}

