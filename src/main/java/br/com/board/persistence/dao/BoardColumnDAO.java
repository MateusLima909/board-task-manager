package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.board.persistence.config.ConnectionConfig;
import br.com.board.persistence.entity.BoardColumnEntity;

public class BoardColumnDAO {
    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARD_COLUMNS (name, order_number, kind, board_id) VALUES (?, ?, ?, ?);";
        
        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getOrderNumber());
            statement.setString(3, entity.getKind().name());
            statement.setLong(4, entity.getBoardId());
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
