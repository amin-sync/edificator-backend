package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "quiz_answers")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswer extends BaseEntity {

    @Column(name = "answer")
    private String answer;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_question_id", referencedColumnName = "id")
    private QuizQuestion quizQuestion;

}

