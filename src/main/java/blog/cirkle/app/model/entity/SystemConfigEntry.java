package blog.cirkle.app.model.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "system_config")
@NoArgsConstructor
public class SystemConfigEntry {
	public static final String ADMIN_USER_ID = "admin.user.id";
	public static final String DEFAULT_IMAGE_PROFILE = "default.image.profile";
	public static final String DEFAULT_IMAGE_COVER = "default.image.cover";

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "key", unique = true, nullable = false)
	private String key;

	@Column(name = "value")
	private String value;

	public SystemConfigEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}
}