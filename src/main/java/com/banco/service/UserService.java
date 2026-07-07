package com.banco.service;

import java.util.List;

import com.banco.dto.CreateUserRequestDTO;
import com.banco.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO register(CreateUserRequestDTO dto);

    UserResponseDTO findById(Long id);

    List<UserResponseDTO> findAll();

}