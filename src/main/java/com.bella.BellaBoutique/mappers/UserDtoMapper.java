package com.bella.BellaBoutique.mappers;

import com.bella.BellaBoutique.DTO.UserDto;
import com.bella.BellaBoutique.model.users.Authority;
import com.bella.BellaBoutique.model.users.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Component
@Service
public class UserDtoMapper {

    public UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        if (!user.getAuthorities().isEmpty()) {
            dto.setAuthority(user.getAuthorities().stream()
                    .map(Authority::getAuthority)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setAuthority(user.getAuthorities().stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toSet()));
        return dto;
    }

    public User toUser(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        return user;
    }
}
