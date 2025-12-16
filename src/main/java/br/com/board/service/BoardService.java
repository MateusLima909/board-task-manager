package br.com.board.service;

import java.util.List;

import br.com.board.persistence.dao.BoardColumnDAO;
import br.com.board.persistence.dao.BoardDAO;
import br.com.board.persistence.entity.BoardColumnEntity;
import br.com.board.persistence.entity.BoardColumnKind;
import br.com.board.persistence.entity.BoardEntity;

public class BoardService {

    private final BoardDAO boardDAO = new BoardDAO();
    private final BoardColumnDAO boardColumnDAO = new BoardColumnDAO();

    public BoardEntity insert(final String name) throws Exception {
        var entity = new BoardEntity();
        entity.setName(name);
        boardDAO.insert(entity);
        
        BoardColumnEntity initial = new BoardColumnEntity();
        initial.setName("Inicial");
        initial.setOrderNumber(1);
        initial.setKind(BoardColumnKind.INICIAL);
        initial.setBoardId(entity.getId());
        boardColumnDAO.insert(initial);

        BoardColumnEntity kindfinal = new BoardColumnEntity();
        kindfinal.setName("Final");
        kindfinal.setOrderNumber(2);
        kindfinal.setKind(BoardColumnKind.FINAL);
        kindfinal.setBoardId(entity.getId());
        boardColumnDAO.insert(kindfinal);

        BoardColumnEntity cancellation = new BoardColumnEntity();
        cancellation.setName("Cancelamento");
        cancellation.setOrderNumber(3);
        cancellation.setKind(BoardColumnKind.CANCELAMENTO);
        cancellation.setBoardId(entity.getId());
        boardColumnDAO.insert(cancellation);

        return entity;
    }

    public BoardEntity findById(final Long id) throws Exception {
        return boardDAO.findById(id);
    }

    public List<BoardEntity> findAll() throws Exception {
        return boardDAO.findAll();
    }

    public void update(final BoardEntity entity) throws Exception {
        boardDAO.update(entity);
    }

    public boolean delete(final Long id) throws Exception {
        BoardEntity entity = boardDAO.findById(id);

        if (entity == null) {
            return false;
        }

        boardDAO.delete(id);
        return true;
    }
}
