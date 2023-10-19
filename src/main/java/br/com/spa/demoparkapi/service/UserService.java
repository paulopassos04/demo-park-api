package br.com.spa.demoparkapi.service;

import br.com.spa.demoparkapi.entity.User;
import br.com.spa.demoparkapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User create(User user){
        return this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username){
        Optional<User> user = Optional.ofNullable(this.userRepository.findByUsername(username));

        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword){
        if (!newPassword.equals(confirmPassword)){
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }
       User user = findById(id);
        if (!user.getPassword().equals(currentPassword)){
            throw new RuntimeException("Sua senha não confere");
        }
       user.setPassword(newPassword);
        return user;
    }
}
