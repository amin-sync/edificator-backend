package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import com.hub.edificators.enums.QuizStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "quiz_submissions")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubmission extends BaseEntity {

    @Column(name = "marks")
    private String marks;

    @Column(name="quiz_status")
    @Enumerated(value = EnumType.STRING)
    private QuizStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;
}
