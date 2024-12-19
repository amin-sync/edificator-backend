package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "quiz_questions")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestion extends BaseEntity {

    @Column(name = "text")
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="quiz_id",referencedColumnName = "id")
    private Quiz quiz;

}
