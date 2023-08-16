package com.br.dinheiroemdia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.LayoutEmailEntity;

public interface LayoutEmailRepository extends JpaRepository<LayoutEmailEntity, Long>{

	Optional<LayoutEmailEntity> findByName(String name);

}
