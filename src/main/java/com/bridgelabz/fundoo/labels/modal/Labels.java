package com.bridgelabz.fundoo.labels.modal;

import com.bridgelabz.fundoo.notes.modal.Notes;
import com.bridgelabz.fundoo.user.modal.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("label")
public class Labels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("label_id")
    private Long id;
    private String name;

    @Column("create_date")
    private LocalDateTime createDate;

    @Column("modified_date")
    private LocalDateTime modifiedDate;
    private Long userId;
    @JsonIgnore
    private Users users;
    @JsonIgnore
    private List<Notes> notes;

    public Labels() {

    }

    public Labels(Long id, String name, LocalDateTime createDate, LocalDateTime modifiedDate, Long userId, Users users, List<Notes> notes) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.userId = userId;
        this.users = users;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }
    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
