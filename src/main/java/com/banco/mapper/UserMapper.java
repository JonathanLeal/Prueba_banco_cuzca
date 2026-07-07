package com.banco.mapper;

import com.banco.dto.CreateUserRequestDTO;
import com.banco.dto.UserResponseDTO;
import com.banco.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(CreateUserRequestDTO dto) {

       return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

    }

    public static UserResponseDTO toDTO(User user) {

        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }

}