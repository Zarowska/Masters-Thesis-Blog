package blog.cirkle.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class GroupProfile implements Profile {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "handle", nullable = false, unique = true)
	private String handle;

	@ManyToOne
	@JoinColumn(name = "profile_image_id")
	private Image profileImage;

	@ManyToOne
	@JoinColumn(name = "cover_photo_id")
	private Image coverPhoto;
}