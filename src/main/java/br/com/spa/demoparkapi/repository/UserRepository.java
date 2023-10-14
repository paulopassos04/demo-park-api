package br.com.spa.demoparkapi.repository;

import br.com.spa.demoparkapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    public User findByUsername(@Param("username") String username);
}
