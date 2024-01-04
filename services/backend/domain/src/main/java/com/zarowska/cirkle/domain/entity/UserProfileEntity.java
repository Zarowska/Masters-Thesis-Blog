package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserProfileEntity {

	@Column(name = "name", length = 128)
	private String name;

	@Column(name = "email", unique = true, length = 64)
	private String email;

	@Column(name = "avatar_url", unique = true, length = 64)
	private String avatarUrl;

}