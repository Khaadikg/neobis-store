package com.neobis.onlinestore.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter @Setter
@Embeddable @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String name;
    private String surname;
    private Short age;
    private String image;
    private String phoneNumber;
    private String address;
}
