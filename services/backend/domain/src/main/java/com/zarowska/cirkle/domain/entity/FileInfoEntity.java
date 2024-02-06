package com.zarowska.cirkle.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;

@Getter
@Setter
@Entity
@Table(name = "files_info")
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "owner_id", nullable = false)
	private UserEntity owner;

	@Column(name = "uploaded_at", nullable = false, updatable = false)
	private Instant uploadedAt = Instant.now();

	@Column(name = "media_type", nullable = false)
	private MediaType mediaType;

	@Column(name = "size", nullable = false)
	private Integer size;

	@Column(name = "width")
	private Integer width;

	@Column(name = "height")
	private Integer height;

	@Column(name = "original_name")
	private String originalName;

	@OneToOne(mappedBy = "info", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_content_id")
	private FileContentEntity content;

	public FileInfoEntity(UserEntity owner, MediaType mediaType, Integer size, String originalName,
			FileContentEntity content) {
		this.owner = owner;
		this.mediaType = mediaType;
		this.size = size;
		this.originalName = originalName;
		this.content = content;
	}

	public FileInfoEntity(UserEntity owner, MediaType mediaType, Integer size, Integer width, Integer height,
			String originalName, FileContentEntity content) {
		this.owner = owner;
		this.mediaType = mediaType;
		this.size = size;
		this.width = width;
		this.height = height;
		this.originalName = originalName;
		this.content = content;
	}
}