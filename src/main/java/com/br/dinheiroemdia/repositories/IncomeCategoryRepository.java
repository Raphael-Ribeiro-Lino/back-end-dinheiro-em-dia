package com.br.dinheiroemdia.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.dinheiroemdia.entities.IncomeCategoryEntity;
import com.br.dinheiroemdia.entities.UserEntity;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategoryEntity, Long>{

    @Query("SELECT ic FROM IncomeCategoryEntity ic WHERE ic.income.budget.user = :user")
	Page<IncomeCategoryEntity> findAllByUser(Pageable pagination, UserEntity user);

}
