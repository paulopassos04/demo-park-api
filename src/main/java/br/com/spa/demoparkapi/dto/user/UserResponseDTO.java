package br.com.spa.demoparkapi.dto.user;

import br.com.spa.demoparkapi.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponseDTO {

    private Long id;
    private String username;
    private String role;

    public UserResponseDTO(){}

    public UserResponseDTO(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static class UserMapper {

        public static User toUser(UserCreateDTO createDTO) {
            return new ModelMapper().map(createDTO, User.class);
        }

        public static UserResponseDTO toUserDTO(User user) {
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
            return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
        }

    }
}
