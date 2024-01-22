package br.com.spa.demoparkapi.repository;

import br.com.spa.demoparkapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
