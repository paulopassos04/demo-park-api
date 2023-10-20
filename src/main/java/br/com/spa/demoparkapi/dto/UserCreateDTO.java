package br.com.spa.demoparkapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateDTO {

    @NotBlank
    @Email(message = "formato do e-mail está inválido", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" )
    private String username;
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

}
