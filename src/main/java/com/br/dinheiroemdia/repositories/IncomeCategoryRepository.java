package com.br.dinheiroemdia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.dinheiroemdia.entities.IncomeCategoryEntity;
import com.br.dinheiroemdia.entities.IncomeEntity;
import com.br.dinheiroemdia.entities.UserEntity;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long>{

    @Query("SELECT ic FROM IncomeCategoryEntity ic WHERE ic.income.budget.user = :user")
	Page<IncomeCategoryEntity> findAllByUser(Pageable pagination, UserEntity user);

    @Query("SELECT ic FROM IncomeCategoryEntity ic WHERE ic.income.budget.user = :user AND ic.id = :id")
	Optional<IncomeCategoryEntity> findByIdAndUser(Long id, UserEntity user);

    @Query("SELECT ic FROM IncomeCategoryEntity ic WHERE ic.income.budget.user = :user AND ic.income = :income")
	List<IncomeCategoryEntity> finByIncomeAndUser(IncomeEntity income, UserEntity user);

}
