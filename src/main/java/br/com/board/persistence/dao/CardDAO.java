package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public CardEntity findById(final Long id) throws SQLException {
        String sql = "SELECT id, title, description, column_id, created_at FROM CARDS WHERE id = ?";
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    var entity = new CardEntity();
                    entity.setId(rs.getLong("id"));
                    entity.setTitle(rs.getString("title"));
                    entity.setDescription(rs.getString("description"));
                    entity.setColumnId(rs.getLong("column_id"));
                    entity.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
                    return entity;
                }
            }
        }
        return null;
    }

    public List<CardEntity> findByColumnId(Long columnId) throws SQLException {
        var cards = new ArrayList<CardEntity>();
        String sql = "SELECT id, title, description, column_id, created_at FROM CARDS WHERE column_id = ?";
        
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, columnId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    var entity = new CardEntity();
                    entity.setId(rs.getLong("id"));
                    entity.setTitle(rs.getString("title"));
                    entity.setDescription(rs.getString("description"));
                    entity.setColumnId(rs.getLong("column_id"));
                    entity.setCreatedAt(rs.getObject("created_at", java.time.OffsetDateTime.class));
                    cards.add(entity);
                }
            }
        }
        return cards;
    }

    public void updateColumn(final Long cardId, final Long newColumnId) throws SQLException {
        String sql = "UPDATE CARDS SET column_id = ? WHERE id = ?";
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, newColumnId);
            statement.setLong(2, cardId);
            statement.executeUpdate();
        }
    }
}

