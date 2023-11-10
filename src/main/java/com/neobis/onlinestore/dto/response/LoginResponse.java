package com.neobis.onlinestore.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
//    private String message;
    private String jwt;
    private String authorities;
    private String username;
}
