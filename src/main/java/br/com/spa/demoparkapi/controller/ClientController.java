package br.com.spa.demoparkapi.controller;

import br.com.spa.demoparkapi.dto.client.ClientCreateDTO;
import br.com.spa.demoparkapi.dto.client.ClientMapper;
import br.com.spa.demoparkapi.dto.client.ClientResponseDTO;
import br.com.spa.demoparkapi.entity.Client;
import br.com.spa.demoparkapi.jwt.JwtUserDetails;
import br.com.spa.demoparkapi.service.ClientService;
import br.com.spa.demoparkapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Client", description = "Contem todas as operações CRUD")
@RestController
@RequestMapping(value = "/api/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<ClientResponseDTO> addClient(@RequestBody @Valid ClientCreateDTO clientCreateDTO,
                                                       @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(clientCreateDTO);
        client.setUser(userService.findById(userDetails.getId()));
        clientService.create(client);
        return ResponseEntity.status(201).body(ClientMapper.toClientDTO(client));
    }


}
