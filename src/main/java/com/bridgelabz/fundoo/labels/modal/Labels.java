package com.bridgelabz.fundoo.labels.modal;

import com.bridgelabz.fundoo.notes.modal.Notes;
import com.bridgelabz.fundoo.user.modal.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("labels")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Labels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("label_id")
    private Long id;
    private String name;
    @Column("user_id")
    private Long userId;
    @Column("note_id")
    private Long noteId;

//
//    @Column("create_date")
//    private LocalDateTime createDate;
//    @Column("modified_date")
//    private LocalDateTime modifiedDate;

//
//    @PrePersist
//    protected void onCreate() {
//        createDate = LocalDateTime.now();
//        modifiedDate = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        modifiedDate = LocalDateTime.now();
//    }
}
