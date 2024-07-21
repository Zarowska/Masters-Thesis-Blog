package blog.cirkle.domain.model.request;

public interface CreateResourceRequest {
	String getText();

	java.util.List<java.util.UUID> getImages();
}
