package com.bella.BellaBoutique.mappers;

import com.bella.BellaBoutique.DTO.UserDto;
import com.bella.BellaBoutique.model.users.Authority;
import com.bella.BellaBoutique.model.users.User;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class UserDtoMapper {

    public UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        if (!user.getAuthorities().isEmpty()) {
            dto.authority = user.getAuthorities().stream()
                    .map(Authority::getAuthority)
                    .collect(Collectors.toSet());
        }
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
