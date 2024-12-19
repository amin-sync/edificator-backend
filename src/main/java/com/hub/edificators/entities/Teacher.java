package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "teachers")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends BaseEntity {

    @Column(name = "expertise")
    private String expertise;

    @Column(name="resumeURL")
    private String resume;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
