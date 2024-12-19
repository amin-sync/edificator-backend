package com.hub.edificators.dtos.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdatePasswordRequest {
    private long userId;
    private String newPassword;
    private String oldPassword;
}
