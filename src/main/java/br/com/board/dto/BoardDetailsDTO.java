package br.com.board.dto;

import java.util.List;

public class BoardDetailsDTO {
    private Long id;
    private String name;
    private List<BoardColumnInfoDTO> columns; 

    public BoardDetailsDTO(Long id, String name, List<BoardColumnInfoDTO> columns) {
        this.id = id;
        this.name = name;
        this.columns = columns;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<BoardColumnInfoDTO> getColumns() { return columns; }
}
