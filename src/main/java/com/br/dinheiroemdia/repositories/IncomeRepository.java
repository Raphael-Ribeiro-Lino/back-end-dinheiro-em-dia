package com.br.dinheiroemdia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.dinheiroemdia.entities.IncomeEntity;
import com.br.dinheiroemdia.entities.UserEntity;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long>{

	@Query("SELECT i FROM IncomeEntity i WHERE i.budget.user = :user AND i.id = :id")
	Optional<IncomeEntity> findByIdAndUser(Long id, UserEntity user);

}
