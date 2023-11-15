package com.neobis.onlinestore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @Length(min = 2, max = 32, message = "Surname length must be between 2 and 32")
    private String username;
    @Length(min = 8, max = 256, message = "Password must be between 8 and 256 digits")
    private String password;
    private boolean mailing;
}
