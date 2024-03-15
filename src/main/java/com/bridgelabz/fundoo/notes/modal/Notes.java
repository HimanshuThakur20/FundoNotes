package com.bridgelabz.fundoo.notes.modal;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("notes")
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("notes_id")
    private Long id;
    private String title;
    private String content;
    @Column("user_id")
    private long userId;
    private boolean archived;
    private boolean pinned;
    private boolean trash;

    public boolean getTrash() {
        return  this.trash;
    }
}
