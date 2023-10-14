package br.com.spa.demoparkapi.service;

import br.com.spa.demoparkapi.entity.User;
import br.com.spa.demoparkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User create(User user){
        return this.userRepository.save(user);
    }
}
