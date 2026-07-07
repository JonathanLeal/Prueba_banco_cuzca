package com.banco.serviceImplement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.dto.CreateSpaceRequestDTO;
import com.banco.dto.SpaceResponseDTO;
import com.banco.entity.Space;
import com.banco.exception.ResourceNotFoundException;
import com.banco.mapper.SpaceMapper;
import com.banco.repository.SpaceRepository;
import com.banco.service.SpaceService;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository repository;

    public SpaceServiceImpl(SpaceRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public SpaceResponseDTO create(CreateSpaceRequestDTO dto) {
        Space space = SpaceMapper.toEntity(dto);
        space.setActive(true);
        Space saved = repository.save(space);
        return SpaceMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SpaceResponseDTO findById(Long id) {
        Space space = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio de coworking no encontrado con el ID: " + id));
        return SpaceMapper.toDTO(space);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpaceResponseDTO> findAll() {
        return repository.findByActiveTrue()
                .stream()
                .map(SpaceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SpaceResponseDTO update(Long id, CreateSpaceRequestDTO dto) {
        Space space = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado para actualizar con el ID: " + id));

        space.setName(dto.getName());
        space.setType(dto.getType());
        space.setCapacity(dto.getCapacity());
        space.setLocation(dto.getLocation());
        space.setHourlyRate(dto.getHourlyRate());

        return SpaceMapper.toDTO(repository.save(space));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Space space = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado para eliminar con el ID: " + id));

        space.setActive(false);
        repository.save(space);
    }
}