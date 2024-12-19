package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "quizzes")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private String duration;

    @Column(name = "no_of_questions")
    private String noOfQuestions;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;
}
