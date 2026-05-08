package com.enterprise.eams.usermodule.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message="Email is required")
    @Email(message="Invalid email format")
    private String email;
    @NotBlank(message="Password is required")
    private String password;

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}