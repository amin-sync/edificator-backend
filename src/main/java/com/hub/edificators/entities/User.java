package com.hub.edificators.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.hub.edificators.commons.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "profile_pic_file_name")
    private String profilePicFileName;

    @Column(name = "nic",unique = true)
    private String nic;

    @Column(name = "password")
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public String getProfilePicURl(String host){
        return host + profilePicFileName;
    }
}
