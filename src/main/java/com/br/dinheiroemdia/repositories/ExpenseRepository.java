package com.br.dinheiroemdia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long>{

}
