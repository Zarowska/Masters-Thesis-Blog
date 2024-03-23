package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "message_events")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MessageEventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "author", nullable = false)
	private UserEntity author;

	@Column(name = "count", nullable = false, updatable = false)
	Integer count;

	@Column(name = "last_at", nullable = false, updatable = false)
	private Instant lastAt;
	@PreUpdate
	private void preUpdate() {
		lastAt = Instant.now();
	}

	@PrePersist
	private void prePersist() {
		lastAt = Instant.now();
		count = 0;
	}

}
