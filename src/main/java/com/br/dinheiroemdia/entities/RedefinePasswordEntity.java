package com.br.dinheiroemdia.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_redefine_passwords")
@Getter
@Setter
public class RedefinePasswordEntity {

	@Id
	@Column(name = "hash")
	private String hash;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private UserEntity user;

	@Column(name = "expiration_time")
	private LocalDateTime expirationTime;
}
