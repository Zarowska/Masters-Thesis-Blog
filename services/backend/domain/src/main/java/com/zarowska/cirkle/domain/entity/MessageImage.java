package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "message_image")
@Accessors(chain = true)
public class MessageImage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "message_id")
	private MessageEntity message;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private FileInfoEntity image;

}
