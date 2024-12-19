package com.hub.edificators.commons;

import com.hub.edificators.enums.Flag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "flag")
    @Enumerated(value = EnumType.STRING)
    protected Flag flag;

    @Column(name = "created_on")
    protected Date createdOn;

    @Column(name = "updated_on")
    protected Date updatedOn;

    @Column(name = "created_by")
    protected String createdBy;

    @Column(name = "updated_by")
    protected String updatedBy;

    @PrePersist
    protected void onCreate() {
        createdOn = new Date();
        createdBy = "system";
        updatedOn = new Date();
        updatedBy = "system";
        flag = Flag.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        createdOn = new Date();
        createdBy = "system";
        updatedOn = new Date();
        updatedBy = "system";
    }
//
//    public void getUsername() {
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails) principal).getUsername();
//            } else {
//                username = principal.toString();
//            }
//        }
//    }
}
