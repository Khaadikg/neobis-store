package com.neobis.onlinestore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private boolean mailing;
}
