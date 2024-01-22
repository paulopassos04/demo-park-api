package br.com.spa.demoparkapi.service;

import br.com.spa.demoparkapi.entity.Client;
import br.com.spa.demoparkapi.exception.CpfUniqueViolationException;
import br.com.spa.demoparkapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client create(Client client){
        try {
            return this.clientRepository.save(client);
        }catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", client.getCpf()));
        }
    }

}
