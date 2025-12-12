package br.com.board.persistence.entity;

import java.time.OffsetDateTime;

public class CardEntity {
    private Long id;
    private String title;
    private String description;
    private OffsetDateTime createdAt;
    private Long columnId;

    //Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public Long getColumnId() { return columnId; }

    //Setters
    public void setId(Long id) { 
        this.id = id; 
    }

    public void setTitle(String title) { 
        this.title = title;
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public void setCreatedAt(OffsetDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public void setColumnId(Long columnId) { 
        this.columnId = columnId; 
    }
}