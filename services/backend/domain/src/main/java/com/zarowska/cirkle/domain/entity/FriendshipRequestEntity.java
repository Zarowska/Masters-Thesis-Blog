package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "requests", uniqueConstraints = @UniqueConstraint(columnNames = {"sender", "receiver"}))
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FriendshipRequestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender", nullable = false, updatable = false)
	private UserEntity sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver", nullable = false, updatable = false)
	private UserEntity receiver;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	public FriendshipRequestEntity(UserEntity sender, UserEntity receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

	@PrePersist
	private void prePersist() {
		createdAt = Instant.now();
	}

}
