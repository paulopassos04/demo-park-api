package br.com.spa.demoparkapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateDTO {

    private String username;
    private String password;

}
