package com.neobis.onlinestore.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String message;
    private String authorities;
    private String firstName;
    private String lastName;
}