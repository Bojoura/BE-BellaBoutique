package com.bella.BellaBoutique.controller;

import com.bella.BellaBoutique.DTO.UserDto;
import com.bella.BellaBoutique.mappers.UserDtoMapper;
import com.bella.BellaBoutique.model.users.User;
import com.bella.BellaBoutique.model.users.UserPhoto;
import com.bella.BellaBoutique.service.PhotoService;
import com.bella.BellaBoutique.service.UserService;
import com.bella.BellaBoutique.util.UriBuilderUtil;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PhotoService photoService;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

//    @GetMapping(value = "/{email}")
//    public ResponseEntity<Map<String, Object>> getUser(@PathVariable("email") String email) {
//        User user = userService.getUserByEmail(email);
//        String photoUrl = null;
//
//        if (user.getUserPhoto() != null) {
//            photoUrl = "/users/" + user.getId() + "/photo";
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("user", user);
//        response.put("photoUrl", photoUrl);
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        UserDto userDto = userDtoMapper.fromUser(user);

        if (user.getUserPhoto() != null) {
            userDto.setPhotoUrl("/images/" + user.getUserPhoto().getFileName());
        }

        return ResponseEntity.ok(userDto);
    }


    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getUserPhoto(@PathVariable("id") Long id, HttpServletRequest request){
        Resource resource = userService.getPhotoFromUser(id);
        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername,"ROLE_USER");
        URI location = UriBuilderUtil.buildUriBasedOnStringId(String.valueOf(newUsername), "/{username}");
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MultipartFile photo) {

        try {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            if (name != null) user.setUsername(name);
            if (email != null) user.setEmail(email);

            if (photo != null && !photo.isEmpty()) {
                if (user.getUserPhoto() != null) {
                    photoService.deleteOldPhoto(user.getUserPhoto());
                }

                UserPhoto userPhoto = photoService.storeFile(photo);

                userService.assignPhotoToUser(userPhoto.getFileName(), user.getId());
            }

            UserDto updatedUserDto = userDtoMapper.toDto(user);

            if (user.getUserPhoto() != null) {
                updatedUserDto.setPhotoUrl("/users/" + user.getId() + "/photo");
            }

            return ResponseEntity.ok(updatedUserDto);

        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
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
