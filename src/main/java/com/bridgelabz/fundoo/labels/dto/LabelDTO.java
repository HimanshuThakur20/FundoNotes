package com.bridgelabz.fundoo.labels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabelDTO {
    private Long id;
    private String name;
    private Long userId;
    private Long noteId;
//    private LocalDateTime createDate;
//    private LocalDateTime modifiedDate;

}
