package com.br.dinheiroemdia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.entities.UserEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long>{

	Optional<BudgetEntity> findByIdAndUser(Long id, UserEntity user);

}
