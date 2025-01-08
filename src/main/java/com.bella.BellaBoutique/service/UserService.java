package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.DTO.UserDto;
import com.bella.BellaBoutique.exception.RecordNotFoundException;
import com.bella.BellaBoutique.exception.UserNotFoundException;
import com.bella.BellaBoutique.exception.UsernameAlreadyExistsException;
import com.bella.BellaBoutique.mappers.UserDtoMapper;
import com.bella.BellaBoutique.model.users.Authority;
import com.bella.BellaBoutique.model.users.User;
import com.bella.BellaBoutique.model.users.UserPhoto;
import com.bella.BellaBoutique.repository.FileUploadRepository;
import com.bella.BellaBoutique.repository.UserRepository;
import org.springframework.core.io.Resource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileUploadRepository uploadRepository;
    private final UserDtoMapper userDtoMapper;
    private final PhotoService photoService;

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(userDtoMapper.fromUser(user));
        }
        return collection;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Gebruiker met " + email + "kan niet gevonden worden." ));
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public String createUser(UserDto userDto) {
        if (userRepository.existsById(userDto.getUsername()))
            throw new UsernameAlreadyExistsException("Gebruikersnaam is niet beschikbaar, kies opnieuw.");
        User newUser = userRepository.save(userDtoMapper.toUser(userDto));
        return newUser.getUsername();
    }

    public User updateUser(String username, User userDetails) {
        User user = (User) userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setAuthorities(userDetails.getAuthorities());
        user.setReviews(userDetails.getReviews());
        user.setProducts(userDetails.getProducts());
        user.setOrders(userDetails.getOrders());

        return userRepository.save(user);
    }

    @Transactional
    public Resource getPhotoFromUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User with number " + id + " not found.");
        }
        UserPhoto photo = optionalUser.get().getUserPhoto();
        if (photo == null) {
            throw new RecordNotFoundException("User " + id + " had no photo.");
        }
        try {
            return photoService.downLoadFile(photo.getFileName());
        } catch (RuntimeException e) {
            throw new RecordNotFoundException("Error fetching user photo: " + e.getMessage());
        }
    }


    @Transactional
    public User assignPhotoToUser(String filename, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<UserPhoto> optionalPhoto = uploadRepository.findByFileName(filename);

        if (optionalUser.isPresent() && optionalPhoto.isPresent()) {
            UserPhoto photo = optionalPhoto.get();
            User user = optionalUser.get();
            user.setUserPhoto(photo);
            return userRepository.save(user);
        } else {
            throw new RecordNotFoundException("foto niet gevonden");
        }
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        return user.getAuthorities();
    }

    public void addAuthority(String username, String authorityName) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Authority authority = new Authority(authorityName);
        user.addAuthority(authority);
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public String getCurrentLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getName())) {
            throw new UserNotFoundException("User not authenticated");
        }
        return authentication.getName();
    }

    public UserDto saveAndConvertToDto(User user) {
        userRepository.save(user);
        UserDto userDto = userDtoMapper.fromUser(user);
        return userDto;
    }
}
