package br.com.board.service;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.board.dto.BoardColumnInfoDTO;
import br.com.board.dto.BoardDetailsDTO;
import br.com.board.dto.CardDetailsDTO;
import br.com.board.persistence.dao.BlockDAO;
import br.com.board.persistence.dao.BoardColumnDAO;
import br.com.board.persistence.dao.BoardDAO;
import br.com.board.persistence.dao.CardDAO;
import br.com.board.persistence.entity.BoardEntity;

public class BoardQueryService {
    private final BlockDAO blockDAO = new BlockDAO();
    private final BoardDAO boardDAO = new BoardDAO();
    private final BoardColumnDAO boardColumnDAO = new BoardColumnDAO();
    private final CardDAO cardDAO = new CardDAO();

    public BoardDetailsDTO showBoardDetails(final Long id) throws SQLException {
        BoardEntity board = boardDAO.findById(id);
        if (board == null) return null; 

        var columns = boardColumnDAO.findByBoardId(board.getId());
        var columnsDTO = new ArrayList<BoardColumnInfoDTO>();
        var isBlocked = blockDAO.isBlocked(board.getId());

        for (var column : columns) {
            var cards = cardDAO.findByColumnId(column.getId());
            var cardsDTO = new ArrayList<CardDetailsDTO>();
            
            for (var card : cards) {
                cardsDTO.add(new CardDetailsDTO(
                    card.getId(), 
                    card.getTitle(), 
                    card.getDescription(), 
                    isBlocked,
                    card.getCreatedAt()
                ));
            }

            columnsDTO.add(new BoardColumnInfoDTO(
                column.getId(), 
                column.getName(), 
                column.getKind(), 
                cardsDTO
            ));
        }

        return new BoardDetailsDTO(board.getId(), board.getName(), columnsDTO);
    }
}
