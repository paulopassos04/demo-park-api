package br.com.spa.demoparkapi.controller;

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
    public ResponseEntity<User> addUser(@RequestBody User user){
       user = this.userService.create(user);
       return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable("username") String username){
        User user = this.userService.findByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id, @RequestBody User user){
        user = this.userService.updatePassword(id, user.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
