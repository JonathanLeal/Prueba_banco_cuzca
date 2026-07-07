package com.banco.mapper;

import com.banco.dto.CreateSpaceRequestDTO;
import com.banco.dto.SpaceResponseDTO;
import com.banco.entity.Space;

public class SpaceMapper {

    private SpaceMapper() {
    }

    public static Space toEntity(CreateSpaceRequestDTO dto) {

        return Space.builder()
                .name(dto.getName())
                .type(dto.getType())
                .capacity(dto.getCapacity())
                .location(dto.getLocation())
                .hourlyRate(dto.getHourlyRate())
                .build();

    }

    public static SpaceResponseDTO toDTO(Space space) {

        return SpaceResponseDTO.builder()
                .id(space.getId())
                .name(space.getName())
                .type(space.getType())
                .capacity(space.getCapacity())
                .location(space.getLocation())
                .hourlyRate(space.getHourlyRate())
                .active(space.getActive())
                .build();

    }

}