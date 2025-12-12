package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.board.persistence.config.ConnectionConfig;
import br.com.board.persistence.entity.CardEntity;

public class CardDAO {
    public CardEntity insert(final CardEntity entity) throws SQLException {
        String sql = "INSERT INTO CARDS (title, description, created_at, column_id) VALUES (?, ?, ?, ?);";
        
        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setObject(3, entity.getCreatedAt());
            statement.setLong(4, entity.getColumnId());
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

