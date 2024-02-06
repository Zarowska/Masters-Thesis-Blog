package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String text;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt = Instant.now();

	@Column(name = "updated_at", nullable = false, updatable = false)
	private Instant updatedAt;

	@ManyToOne(optional = false)
	@JoinColumn(name = "author_id", nullable = false)
	private UserEntity author;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentImageEntity> images = new ArrayList<>();

	@PrePersist
	private void prePersist() {
		createdAt = Instant.now();
		updatedAt = createdAt;
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = Instant.now();
	}

}
