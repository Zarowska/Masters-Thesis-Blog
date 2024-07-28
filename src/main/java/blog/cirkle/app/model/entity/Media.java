package blog.cirkle.app.model.entity;

public interface Media {
	java.util.Set<Image> getImages();

	String getTextContent();

	void setImages(java.util.Set<Image> images);

	void setTextContent(String textContent);
}
