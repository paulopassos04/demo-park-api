package br.com.spa.demoparkapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDTO {

    private Long id;
    private String username;
    private String role;

}
