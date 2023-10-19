package br.com.spa.demoparkapi.controller;

import br.com.spa.demoparkapi.dto.UserCreateDTO;
import br.com.spa.demoparkapi.dto.UserPasswordDTO;
import br.com.spa.demoparkapi.dto.UserResponseDTO;
import br.com.spa.demoparkapi.dto.mapper.UserMapper;
import br.com.spa.demoparkapi.entity.User;
import br.com.spa.demoparkapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserCreateDTO createDTO){
      User user = this.userService.create(UserMapper.toUser(createDTO));
       return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserDTO(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> findByUsername(@PathVariable("username") String username){
        User user = this.userService.findByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<User> users = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> findById(@PathVariable("id") Long id, @RequestBody UserPasswordDTO dto){
       User user = this.userService.updatePassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }
}
