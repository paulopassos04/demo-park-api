package br.com.spa.demoparkapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPasswordDTO {

    @NotBlank
    @Size(min = 6, max = 6)
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 6)
    private String newPassword;
    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmPassword;
}
