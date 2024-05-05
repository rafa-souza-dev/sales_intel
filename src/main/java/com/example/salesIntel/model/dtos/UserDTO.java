package com.example.salesIntel.model.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UserDTO {
    @Size(max = 255, min = 3)
    @Pattern(regexp = " ")
    private String fullName;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$")
    private String password;

    @NotNull
    @Positive
    private Long establishmentId;
}
