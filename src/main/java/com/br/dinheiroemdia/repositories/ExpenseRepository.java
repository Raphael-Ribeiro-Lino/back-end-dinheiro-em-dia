package com.br.dinheiroemdia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.dinheiroemdia.entities.ExpenseEntity;
import com.br.dinheiroemdia.entities.UserEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long>{

	@Query("SELECT e FROM ExpenseEntity e WHERE e.budget.user = :user AND e.id = :id")
	Optional<ExpenseEntity> findByIdAndUser(Long id, UserEntity user);

}
