package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "notes")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

}
