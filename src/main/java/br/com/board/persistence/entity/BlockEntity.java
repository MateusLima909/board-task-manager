package br.com.board.persistence.entity;

import java.time.OffsetDateTime;

public class BlockEntity {
    private Long id;
    private String blockedReason;
    private String unblockedReason;
    private OffsetDateTime blockedAt;
    private OffsetDateTime unblockedAt;
    private Long cardId;

    //Getters
    public Long getId() { return id; }
    public String getBlockedReason() { return blockedReason; }
    public String getUnblockedReason() { return unblockedReason; }
    public OffsetDateTime getBlockedAt() { return blockedAt; }
    public OffsetDateTime getUnblockedAt() { return unblockedAt; }
    public Long getCardId() { return cardId; }

    //Setters
    public void setId(Long id) { 
        this.id = id;
    }

    public void setBlockedReason(String blockedReason) { 
        this.blockedReason = blockedReason; 
    }

    public void setUnblockedReason(String unblockedReason) { 
        this.unblockedReason = unblockedReason;
    }

    public void setBlockedAt(OffsetDateTime blockedAt) { 
        this.blockedAt = blockedAt; 
    }

    public void setUnblockedAt(OffsetDateTime unblockedAt) { 
        this.unblockedAt = unblockedAt;
    }

    public void setCardId(Long cardId) { 
        this.cardId = cardId; 
    }
}