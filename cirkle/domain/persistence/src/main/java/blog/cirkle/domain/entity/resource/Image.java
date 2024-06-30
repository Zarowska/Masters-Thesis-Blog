package blog.cirkle.domain.entity.resource;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "images")
@NoArgsConstructor
@DiscriminatorValue("IMAGE")
public class Image extends Resource {
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "image_file_id", nullable = false)
	private ImageFile imageFile;

	public Image(ImageFile imageFile) {
		this.imageFile = imageFile;
	}
}