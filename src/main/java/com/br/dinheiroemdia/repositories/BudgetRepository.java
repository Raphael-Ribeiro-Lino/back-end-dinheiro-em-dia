package com.br.dinheiroemdia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.BudgetEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long>{

}
