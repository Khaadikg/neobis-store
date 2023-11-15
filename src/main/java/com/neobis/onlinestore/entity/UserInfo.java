package com.neobis.onlinestore.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


@Embeddable
@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Length(min = 2, message = "name length must be 2 digits or more")
    private String name;
    @Length(min = 2, max = 124, message = "Surname length must be between 2 and 124")
    private String surname;

    @Range(min = 12, max = 150, message = "Age should be between 12 an 150")
    private Short age;
    private String image;
    @Email(message = "Email should be valid")
    private String email;
    private String phoneNumber;
    private String address;
}
