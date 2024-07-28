package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserProfile implements Profile {

	@Column(name = "full_name", nullable = false)
	private String name;

	@Column(name = "handle", nullable = false, unique = true)
	private String handle;

	@ManyToOne
	@JoinColumn(name = "profile_image_id")
	private Image profileImage;

	@ManyToOne
	@JoinColumn(name = "cover_photo_id")
	private Image coverPhoto;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "bio")
	private String bio;

	@Embedded
	private Address address = new Address();

}