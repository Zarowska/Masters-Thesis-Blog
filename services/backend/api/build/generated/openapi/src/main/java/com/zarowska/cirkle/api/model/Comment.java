package com.zarowska.cirkle.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.*;
import jakarta.annotation.Generated;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Comment
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private UUID postId;

    private String text;

    @Valid
    private List<@Valid URI> images;

    public Comment id(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     */
    @Valid
    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Comment postId(UUID postId) {
        this.postId = postId;
        return this;
    }

    /**
     * Get postId
     * @return postId
     */
    @Valid
    @Schema(name = "postId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("postId")
    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public Comment text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Get text
     * @return text
     */
    @Schema(name = "text", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment images(List<@Valid URI> images) {
        this.images = images;
        return this;
    }

    public Comment addImagesItem(URI imagesItem) {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        this.images.add(imagesItem);
        return this;
    }

    /**
     * Get images
     * @return images
     */
    @Valid
    @Schema(name = "images", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("images")
    public List<@Valid URI> getImages() {
        return images;
    }

    public void setImages(List<@Valid URI> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(this.id, comment.id) && Objects.equals(this.postId, comment.postId) && Objects.equals(this.text, comment.text) && Objects.equals(this.images, comment.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, text, images);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Comment {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    postId: ").append(toIndentedString(postId)).append("\n");
        sb.append("    text: ").append(toIndentedString(text)).append("\n");
        sb.append("    images: ").append(toIndentedString(images)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
