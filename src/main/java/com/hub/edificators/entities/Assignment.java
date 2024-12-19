package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "assignments")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment extends BaseEntity {

    @Column(name = "Title")
    private String title;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "dueDate")
    private String dueDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

}
