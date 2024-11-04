package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.DTO.UserDto;
import com.bella.BellaBoutique.exception.RecordNotFoundException;
import com.bella.BellaBoutique.exception.UserNotFoundException;
import com.bella.BellaBoutique.exception.UsernameAlreadyExistsException;
import com.bella.BellaBoutique.mappers.UserDtoMapper;
import com.bella.BellaBoutique.model.users.Authority;
import com.bella.BellaBoutique.model.users.User;
import com.bella.BellaBoutique.repository.UserRepository;
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
    private final UserDtoMapper userDtoMapper;

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(userDtoMapper.fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return userDtoMapper.fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public String createUser(UserDto userDto) {
        if (userRepository.existsById(userDto.getUsername()))
            throw new UsernameAlreadyExistsException("This username already exists, please choose another one.");
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

    public void updateUserPassword(String username, UserDto newUser) {
        if (!userRepository.existsById(username))
            throw new RecordNotFoundException("User not found with username: " + username);
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
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

}
