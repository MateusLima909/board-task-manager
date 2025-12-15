package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.board.persistence.config.ConnectionConfig;
import br.com.board.persistence.entity.BoardColumnEntity;
import br.com.board.persistence.entity.BoardColumnKind;

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

    public BoardColumnEntity findById(final Long id) throws SQLException {
        String sql = "SELECT id, name, order_number, kind, board_id FROM BOARD_COLUMNS WHERE id = ?";
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    var entity = new BoardColumnEntity();
                    entity.setId(rs.getLong("id"));
                    entity.setName(rs.getString("name"));
                    entity.setOrderNumber(rs.getInt("order_number"));
                    entity.setKind(BoardColumnKind.valueOf(rs.getString("kind")));
                    entity.setBoardId(rs.getLong("board_id"));
                    return entity;
                }
            }
        }
        return null;
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardId) throws SQLException {
        String sql = "SELECT id, name, order_number, kind, board_id FROM BOARD_COLUMNS WHERE board_id = ? ORDER BY order_number";
        
        try(Connection connection = ConnectionConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1, boardId);
            
            try(var resultSet = statement.executeQuery()) {
                var columns = new java.util.ArrayList<BoardColumnEntity>();
                while(resultSet.next()) {
                    BoardColumnEntity entity = new BoardColumnEntity();
                    entity.setId(resultSet.getLong("id"));
                    entity.setName(resultSet.getString("name"));
                    entity.setOrderNumber(resultSet.getInt("order_number"));
                    entity.setKind(br.com.board.persistence.entity.BoardColumnKind.valueOf(resultSet.getString("kind")));
                    entity.setBoardId(resultSet.getLong("board_id"));
                    columns.add(entity);
                }
                return columns;
            }
        }
    }
}
