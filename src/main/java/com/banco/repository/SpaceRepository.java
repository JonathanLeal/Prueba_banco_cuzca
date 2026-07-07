package com.banco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.entity.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {
	List<Space> findByActiveTrue();
}