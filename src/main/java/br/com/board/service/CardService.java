package br.com.board.service;

import java.sql.SQLException;

import br.com.board.persistence.dao.BoardColumnDAO;
import br.com.board.persistence.dao.CardDAO;
import br.com.board.persistence.dao.BlockDAO;
import br.com.board.persistence.entity.CardEntity;
import br.com.board.persistence.entity.BlockEntity;
import br.com.board.persistence.entity.BoardColumnKind;

import java.time.OffsetDateTime;

public class CardService {
    
    private final CardDAO cardDAO = new CardDAO();
    private final BlockDAO blockDAO = new BlockDAO();
    private final BoardColumnDAO boardColumnDAO = new BoardColumnDAO();

    public CardEntity insert(final Long boardId, final String title, final String description) throws Exception {
        var columns = boardColumnDAO.findByBoardId(boardId);

        Long initialColumnId = null;
        for (var column : columns) {
            if (column.getKind() == BoardColumnKind.INICIAL) {
                initialColumnId = column.getId();
                break;
            }
        }

        var entity = new CardEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setCreatedAt(OffsetDateTime.now());
        entity.setColumnId(initialColumnId);
        return cardDAO.insert(entity);
    }

    public void delete(final Long id) throws SQLException {
        cardDAO.delete(id);
    }

    public void update(final Long id, final String title, final String description) throws SQLException {
        var card = cardDAO.findById(id);
        if (card == null) throw new RuntimeException("Card não encontrado");

        card.setTitle(title);
        card.setDescription(description);
        cardDAO.update(card);
    }

    public void block(final Long cardId, final String reason) throws SQLException {
        var card = cardDAO.findById(cardId);
        if (card == null) throw new RuntimeException("Card não encontrado");

        var block = new BlockEntity();
        block.setCardId(cardId);
        block.setBlockedReason(reason);
        block.setBlockedAt(OffsetDateTime.now());

        blockDAO.insert(block);
    }

    public void unblock(final Long cardId, final String reason) throws SQLException {
        if (!blockDAO.isBlocked(cardId)) {
            throw new RuntimeException("Este card não está bloqueado!");
        }

        blockDAO.unblock(cardId, reason);
    }

    public void moveToNextColumn(final Long cardId) throws SQLException {
        var card = cardDAO.findById(cardId);
        if (card == null) throw new RuntimeException("Card não encontrado");

        if (blockDAO.isBlocked(cardId)) {
            throw new RuntimeException("Card está bloqueado e não pode ser movido!");
            
        }

        var currentColumn = boardColumnDAO.findById(card.getColumnId()); 
        
        var columns = boardColumnDAO.findByBoardId(currentColumn.getBoardId());
        
        int currentIndex = -1;
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getId().equals(currentColumn.getId())) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex + 1 >= columns.size()) {
            throw new RuntimeException("O Card já está na última coluna!");
        }

        var nextColumn = columns.get(currentIndex + 1);
        cardDAO.updateColumn(cardId, nextColumn.getId());
    }
}
