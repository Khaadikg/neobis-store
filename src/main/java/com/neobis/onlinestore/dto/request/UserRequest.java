package com.neobis.onlinestore.dto.request;

import com.neobis.onlinestore.model.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserRequest {
    private String username;
    private String password;
}
