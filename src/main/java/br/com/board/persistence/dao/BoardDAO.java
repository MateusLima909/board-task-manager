package br.com.board.persistence.dao;

import br.com.board.persistence.config.ConnectionConfig;
import br.com.board.persistence.entity.BoardEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS (name) VALUES (?);";
        
        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, entity.getName());
            statement.executeUpdate();
            
            try(var generatedKeys = statement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        }
        return entity;
    }

    public List<BoardEntity> findAll() throws SQLException {
        String sql = "SELECT id, name FROM BOARDS";
        
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            var boards = new ArrayList<BoardEntity>();
            
            while (resultSet.next()) {
                BoardEntity entity = new BoardEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                boards.add(entity);
            }
            
            return boards; 
        }
    }

    public BoardEntity findById(final Long id) throws SQLException {
        String sql = "SELECT id, name FROM BOARDS WHERE id = ?;";

        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1, id);
            
            try(var resultSet =  statement.executeQuery()) {
                if(resultSet.next()) {
                    BoardEntity entity = new BoardEntity();
                    entity.setId(resultSet.getLong("id"));
                    entity.setName(resultSet.getString("name"));
                    return entity;
                }
            }
        }
        return null;
    }

    public void update(final BoardEntity entity) throws SQLException {
        String sql = "UPDATE BOARDS SET name = ? WHERE id = ?;";
        
        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1,  entity.getName());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        }   
    }

    public void delete(final Long id) throws SQLException {
     String sql = "DELETE FROM BOARDS WHERE id = ?;";
        
        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1, id);
            statement.executeUpdate();
        }   
    }
}