package com.bank.recipebook.service;

import com.bank.recipebook.dto.user.CreateUserRequest;
import com.bank.recipebook.dto.user.UpdateUserRequest;
import com.bank.recipebook.dto.user.UserResponse;
import com.bank.recipebook.exception.ResourceAlreadyExistsException;
import com.bank.recipebook.exception.ResourceNotFoundException;
import com.bank.recipebook.model.User;
import com.bank.recipebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse findById(Long id) {
        User user = getUserById(id);
        return toResponse(user);
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException(
                    "User with email " + request.email() + " already exists"
            );
        }

        User user = User.builder()
                .email(request.email())
                .passwordHash(request.password())
                .username(request.username())
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {

        User user = getUserById(id);

        if (!user.getEmail().equals(request.email())
                && userRepository.existsByEmail(request.email())) {

            throw new ResourceAlreadyExistsException(
                    "User with email " + request.email() + " already exists"
            );
        }

        user.setEmail(request.email());
        user.setUsername(request.username());

        return toResponse(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with id " + id + " not found"
                        )
                );
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}