package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}

