package br.com.spa.demoparkapi.dto.client;

import br.com.spa.demoparkapi.entity.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDTO clientCreateDTO){
        return new ModelMapper().map(clientCreateDTO, Client.class);
    }

    public static ClientResponseDTO toClientDTO(Client client){
        return new ModelMapper().map(client, ClientResponseDTO.class);
    }
    
}
