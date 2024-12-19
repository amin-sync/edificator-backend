package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "announcements")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement extends BaseEntity {

    @Column(name = "Message")
    private String message;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

}
