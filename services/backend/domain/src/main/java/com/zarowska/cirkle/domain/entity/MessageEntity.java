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
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "sender", nullable = false)
	private UserEntity sender;

	@ManyToOne(optional = false)
	@JoinColumn(name = "receiver", nullable = false)
	private UserEntity receiver;

	@Column(name = "text")
	private String text;

	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MessageImage> images = new ArrayList<>();

	@Column(name = "viewed_by_receiver")
	private boolean viewedByReceiver;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt = Instant.now();

	@Column(name = "updated_at", nullable = false, updatable = false)
	private Instant updatedAt;

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
