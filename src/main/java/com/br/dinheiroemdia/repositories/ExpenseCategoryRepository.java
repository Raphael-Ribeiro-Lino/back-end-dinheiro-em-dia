package com.br.dinheiroemdia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.ExpenseCategoryEntity;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Long>{

}
