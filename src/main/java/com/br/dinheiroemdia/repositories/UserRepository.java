package com.br.dinheiroemdia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.enums.ProfileEnum;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByProfile(ProfileEnum admin);

	Optional<UserEntity> findByEmail(String email);

}
