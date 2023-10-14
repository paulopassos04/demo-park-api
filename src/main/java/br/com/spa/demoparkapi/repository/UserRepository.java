package br.com.spa.demoparkapi.repository;

import br.com.spa.demoparkapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
