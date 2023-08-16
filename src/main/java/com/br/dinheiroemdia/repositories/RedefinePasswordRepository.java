package com.br.dinheiroemdia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.RedefinePasswordEntity;
import com.br.dinheiroemdia.entities.UserEntity;

public interface RedefinePasswordRepository extends JpaRepository<RedefinePasswordEntity, String>{

	Optional<RedefinePasswordEntity> findByUser(UserEntity userFound);

	Optional<RedefinePasswordEntity> findByHash(String hash);

}
