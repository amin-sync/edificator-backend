package com.hub.edificators.dtos.responses.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ListStudentsResponse {
    private long id;
    private String fullName;
    private String profileURL;
}
