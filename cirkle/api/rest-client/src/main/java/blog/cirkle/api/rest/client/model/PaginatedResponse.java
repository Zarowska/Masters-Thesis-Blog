package blog.cirkle.api.rest.client.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class PaginatedResponse<T> {

	@SerializedName("content")
	private List<T> content;

	@SerializedName("totalElements")
	private long totalElements;

	@SerializedName("totalPages")
	private int totalPages;

	@SerializedName("number")
	private int number;
}
