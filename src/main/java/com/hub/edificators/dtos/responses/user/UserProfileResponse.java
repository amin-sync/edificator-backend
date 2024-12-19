package com.hub.edificators.dtos.responses.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfileResponse {
    private String email;
    private String fullName;
    private String profileURL;
}
