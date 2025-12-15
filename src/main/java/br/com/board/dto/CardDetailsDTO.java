package br.com.board.dto;

import java.time.OffsetDateTime;

public class CardDetailsDTO {
    private Long id;
    private String title;
    private String description;
    private OffsetDateTime createdAt;

    public CardDetailsDTO(Long id, String title, String description, OffsetDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
