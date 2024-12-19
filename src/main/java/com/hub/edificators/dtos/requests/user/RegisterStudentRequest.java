package com.hub.edificators.dtos.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterStudentRequest {
    private String email;
    private String fullName;
    private String nic;
    private String password;
    private String grade;
    private long roleId;
}
