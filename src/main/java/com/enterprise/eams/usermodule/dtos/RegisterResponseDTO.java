package com.enterprise.eams.usermodule.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
}