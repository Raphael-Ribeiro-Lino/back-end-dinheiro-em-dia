package com.br.dinheiroemdia.repositories;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.entities.UserEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long>{

	Optional<BudgetEntity> findByIdAndUser(Long id, UserEntity user);

	Optional<BudgetEntity> findByYearMonthAndUser(YearMonth yearMonth, UserEntity userByToken);

	Page<BudgetEntity> findAllByUser(Pageable pagination, UserEntity userByToken);

}
