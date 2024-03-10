package com.bridgelabz.fundoo.userandnotes.modal;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("notes")
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("notes_id")
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private boolean archived;

    public Notes() {
    }

    public Notes(Long id, String title, String content, Long userId, boolean archived) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.archived = archived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
