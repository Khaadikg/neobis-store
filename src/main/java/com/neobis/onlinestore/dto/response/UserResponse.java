package com.neobis.onlinestore.dto.response;

import com.neobis.onlinestore.entity.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private String username;
    private UserInfo userInfo;
}
