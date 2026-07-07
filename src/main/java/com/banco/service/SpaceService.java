package com.banco.service;

import java.util.List;

import com.banco.dto.CreateSpaceRequestDTO;
import com.banco.dto.SpaceResponseDTO;

public interface SpaceService {

    SpaceResponseDTO create(CreateSpaceRequestDTO dto);

    SpaceResponseDTO findById(Long id);

    List<SpaceResponseDTO> findAll();

    SpaceResponseDTO update(Long id, CreateSpaceRequestDTO dto);

    void delete(Long id);
}