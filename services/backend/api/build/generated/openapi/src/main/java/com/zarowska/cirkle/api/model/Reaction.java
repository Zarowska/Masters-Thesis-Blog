package com.zarowska.cirkle.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.zarowska.cirkle.api.model.User;
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
 * Reaction
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class Reaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private User author;

    private Integer reactionType;

    public Reaction id(UUID id) {
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

    public Reaction author(User author) {
        this.author = author;
        return this;
    }

    /**
     * Get author
     * @return author
     */
    @Valid
    @Schema(name = "author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("author")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Reaction reactionType(Integer reactionType) {
        this.reactionType = reactionType;
        return this;
    }

    /**
     * Get reactionType
     * @return reactionType
     */
    @Schema(name = "reactionType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("reactionType")
    public Integer getReactionType() {
        return reactionType;
    }

    public void setReactionType(Integer reactionType) {
        this.reactionType = reactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reaction reaction = (Reaction) o;
        return Objects.equals(this.id, reaction.id) && Objects.equals(this.author, reaction.author) && Objects.equals(this.reactionType, reaction.reactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, reactionType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Reaction {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    author: ").append(toIndentedString(author)).append("\n");
        sb.append("    reactionType: ").append(toIndentedString(reactionType)).append("\n");
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
