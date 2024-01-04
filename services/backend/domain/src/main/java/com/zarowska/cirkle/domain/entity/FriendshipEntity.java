package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "user_friends", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "friend_id"})})
@Check(name = "check_user_not_friend", constraints = "user_id != friend_id")

public class FriendshipEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserEntity sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friend_id", referencedColumnName = "id", nullable = false)
	private UserEntity receiver;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public FriendshipEntity(UserEntity sender, UserEntity receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}
}
