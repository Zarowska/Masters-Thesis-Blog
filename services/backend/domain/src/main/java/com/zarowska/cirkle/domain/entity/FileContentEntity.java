package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "files_content")
@NoArgsConstructor
@AllArgsConstructor
public class FileContentEntity {

	@Id
	private UUID id;

	@MapsId
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private FileInfoEntity info;

	@Lob
	@Column(name = "content", nullable = false)
	private byte[] content;

	public FileContentEntity(byte[] content) {
		this.content = content;
	}
}