package com.bella.BellaBoutique.controller;

import com.bella.BellaBoutique.DTO.UserDto;
import com.bella.BellaBoutique.exception.UnauthorizedAccessException;
import com.bella.BellaBoutique.model.users.User;
import com.bella.BellaBoutique.service.UserService;
import com.bella.BellaBoutique.util.UriBuilderUtil;
import jakarta.validation.Valid;
import com.bella.BellaBoutique.model.users.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User optionalUser = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(optionalUser);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
//        Optional<User> user = userService.getUserById(id);
//        return user.map(value -> new ResponseEntity<>(convertToDto(value), HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername,"ROLE_USER");
        URI location = UriBuilderUtil.buildUriBasedOnStringId(String.valueOf(newUsername), "/{username}");
        return ResponseEntity.created(location).build();
    }

//    @PutMapping("/{username}")
//    public ResponseEntity<UserDto> updateUser(@PathVariable String username, @Valid @RequestBody UserDto userDto) {
//        User userDetails = convertToEntity(userDto);
//        User updatedUser = userService.updateUser(username, userDetails);
//        return new ResponseEntity<>(convertToDto(updatedUser), HttpStatus.OK);
//    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUserPassword(@PathVariable("username") String username, @RequestBody UserDto dto) {
        String currentUsername = userService.getCurrentLoggedInUsername();
        if (!currentUsername.equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to update this user.");
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(encryptedPassword);
        }
        userService.updateUserPassword(username, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UserDto convertToDto(User user) {
        Set<String> authoritySet = user.getAuthorities().stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toSet());
        return new UserDto(user.getUsername(), user.getPassword(), user.getEmail(), authoritySet);
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}
