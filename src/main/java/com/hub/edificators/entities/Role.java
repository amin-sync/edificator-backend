package com.hub.edificators.entities;

import com.hub.edificators.commons.BaseEntity;
import com.hub.edificators.enums.AccessLevel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    @Column(name = "name")
    @Enumerated(value = EnumType.STRING)
    private AccessLevel name;

}
