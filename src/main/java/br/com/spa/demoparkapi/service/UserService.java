package br.com.spa.demoparkapi.service;

import br.com.spa.demoparkapi.entity.User;
import br.com.spa.demoparkapi.exception.EntityNotFoundException;
import br.com.spa.demoparkapi.exception.PasswordInvalidException;
import br.com.spa.demoparkapi.exception.UserNameUniqueViolationException;
import br.com.spa.demoparkapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserNameUniqueViolationException(
                    String.format("Username '%s' já cadastrado", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> user = Optional.ofNullable(this.userRepository.findByUsername(username));

        return user.orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário username '%s' não encontrado", username)));
    }

    @Transactional(readOnly = true)
    public User.Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException(String.format("Nova senha não confere com confirmação de senha."));
        }

        User user = findById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidException(String.format("Sua senha não confere"));
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

}
