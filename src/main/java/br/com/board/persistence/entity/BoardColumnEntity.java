package br.com.board.persistence.entity;

public class BoardColumnEntity {
    private Long id;
    private String name;
    private int orderNumber;
    private BoardColumnKind kind;
    private Long boardId;

    //Getters
    public Long getId() { return id; }
    public String getName() { return name;}
    public int getOrderNumber() { return orderNumber; }
    public BoardColumnKind getKind() { return kind; }
    public Long getBoardId() { return boardId; }

    //Setters
    public void setId(Long id) { 
        this.id = id;
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber; 
    }

    public void setKind(BoardColumnKind kind) { 
        this.kind = kind; 
    }

    public void setBoardId(Long boardId) { 
        this.boardId = boardId; 
    }
}