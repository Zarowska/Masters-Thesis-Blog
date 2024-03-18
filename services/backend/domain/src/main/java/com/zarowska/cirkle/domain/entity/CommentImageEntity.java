package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment_images")
public class CommentImageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "comment_id")
	private CommentEntity comment;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private FileInfoEntity image;

}