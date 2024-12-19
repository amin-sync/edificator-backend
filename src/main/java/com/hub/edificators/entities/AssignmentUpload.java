package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import com.hub.edificators.enums.AssignmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "assignment_uploads")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentUpload extends BaseEntity {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private AssignmentStatus status;

    @Column(name="marks")
    private String marks;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "assignment_id",referencedColumnName = "id")
    private Assignment assignment;

}
