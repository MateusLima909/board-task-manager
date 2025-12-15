package br.com.board.dto;

import java.util.List;

import br.com.board.persistence.entity.BoardColumnKind;

public class BoardColumnInfoDTO {
    private Long id;
    private String name;
    private BoardColumnKind kind;
    private List<CardDetailsDTO> cards;

    public BoardColumnInfoDTO(Long id, String name, BoardColumnKind kind, List<CardDetailsDTO> cards) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.cards = cards;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public BoardColumnKind getKind() { return kind; }
    public List<CardDetailsDTO> getCards() { return cards; }
}
