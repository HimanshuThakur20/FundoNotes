package com.bridgelabz.fundoo.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotesDTO {
    private Long id;
    private String title;
    private String content;
    private long userId;
    private boolean archived;
    private boolean pinned;
    private boolean trash;
}
