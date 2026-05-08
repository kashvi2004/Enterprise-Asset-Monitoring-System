package com.enterprise.eams.usermodule.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    public void setEmail(String email){
        this.email=email==null?null:email.trim();
    }
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"
    )
    private String password;
    

}
