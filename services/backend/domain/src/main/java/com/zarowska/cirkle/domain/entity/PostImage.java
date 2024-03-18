package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "post_image")
@Accessors(chain = true)
public class PostImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "post_id")
	private PostEntity post;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private FileInfoEntity image;

}