package br.com.spa.demoparkapi.dto.mapper;

import br.com.spa.demoparkapi.dto.UserCreateDTO;
import br.com.spa.demoparkapi.dto.UserResponseDTO;
import br.com.spa.demoparkapi.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

public class UserMapper {

    public static User toUser(UserCreateDTO createDTO){
        return new ModelMapper().map(createDTO, User.class);
    }

    public static UserResponseDTO toUserDTO(User user){
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListDTO(List<User> users){
        
    }

}
