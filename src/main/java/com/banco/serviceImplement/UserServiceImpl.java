package com.banco.serviceImplement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banco.dto.CreateUserRequestDTO;
import com.banco.dto.UserResponseDTO;
import com.banco.entity.User;
import com.banco.exception.EmailAlreadyExistsException;
import com.banco.mapper.UserMapper;
import com.banco.repository.UserRepository;
import com.banco.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO register(CreateUserRequestDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico '" + dto.getEmail() + "' ya se encuentra registrado.");
        }

        User user = UserMapper.toEntity(dto);
        
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(dto.getRole());

        User saved = repository.save(user);

        return UserMapper.toDTO(saved);
    }

    @Override
    public UserResponseDTO findById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());

    }

}