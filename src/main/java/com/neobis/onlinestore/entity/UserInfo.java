package com.neobis.onlinestore.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter @Setter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @NotNull(message = "Name cannot be null")
    private String name;
    @Size(min = 2, max = 124, message = "Surname length must be between 2 and 124")
    private String surname;
    @Min(value = 12, message = "Age should not be less than 12")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Short age;
    private String image;
    @Email(message = "Email should be valid")
    private String email;
    private String phoneNumber;
    private String address;
}
