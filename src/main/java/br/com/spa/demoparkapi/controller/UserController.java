package br.com.spa.demoparkapi.controller;

import br.com.spa.demoparkapi.dto.user.UserCreateDTO;
import br.com.spa.demoparkapi.dto.user.UserMapper;
import br.com.spa.demoparkapi.dto.user.UserPasswordDTO;
import br.com.spa.demoparkapi.dto.user.UserResponseDTO;
import br.com.spa.demoparkapi.entity.User;
import br.com.spa.demoparkapi.exception.ErrorMessage;
import br.com.spa.demoparkapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Users", description = "Contem todas as operações CRUD")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

        @Autowired
        private UserService userService;

        @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário", responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
        @PostMapping
        public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserCreateDTO createDTO) {
                User user = this.userService.create(UserMapper.toUser(createDTO));
                return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserDTO(user));
        }

        @Operation(summary = "Recuperar um usuário pelo username", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CLIENT", security = @SecurityRequirement(name = "security"), responses = {
                @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
        @GetMapping("/{username}")
        @PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENT') AND #username == authentication.principal.username")
        public ResponseEntity<UserResponseDTO> findByUsername(@PathVariable("username") String username) {
                User user = this.userService.findByUsername(username);
                return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toUserDTO(user));
        }

        @Operation(summary = "Listar todos os usuários", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CLIENT", security = @SecurityRequirement(name = "security"), responses = {
                @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
                @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
        @GetMapping
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<List<UserResponseDTO>> findAll() {
                List<User> users = this.userService.findAll();
                return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDTO(users));
        }

        @Operation(summary = "Atualizar senha", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CLIENT", security = @SecurityRequirement(name = "security"), responses = {
                @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                @ApiResponse(responseCode = "400", description = "Senha não confere", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })

        @PatchMapping("/{id}")
        @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT') AND (#id == authentication.principal.id)")
        public ResponseEntity<Void> updatePassword(@PathVariable("id") Long id,
                                                   @Valid @RequestBody UserPasswordDTO dto) {
                User user = this.userService.updatePassword(id, dto.getCurrentPassword(), dto.getNewPassword(),
                        dto.getConfirmPassword());
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Deletar usuário", description = "Deletar usuário usando o id", responses = {
                @ApiResponse(responseCode = "204", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@Valid @PathVariable("id") Long id) {
                this.userService.delete(id);
                return ResponseEntity.noContent().build();
        }

}
