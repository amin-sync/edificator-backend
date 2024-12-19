package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "courses")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course extends BaseEntity {

    @Column(name = "grade")
    private String grade;

    @Column(name = "subject")
    private  String subject;

    @Column(name = "days")
    private String days;

    @Column(name = "fromTime")
    private String fromTime;

    @Column(name = "toTime")
    private String toTime;

    @Column(name = "fee")
    private String fee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

}
