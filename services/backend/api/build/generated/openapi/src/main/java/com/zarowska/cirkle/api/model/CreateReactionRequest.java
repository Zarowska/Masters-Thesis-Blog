package com.zarowska.cirkle.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * CreateReactionRequest
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class CreateReactionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer reactionType;

    public CreateReactionRequest reactionType(Integer reactionType) {
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
        CreateReactionRequest createReactionRequest = (CreateReactionRequest) o;
        return Objects.equals(this.reactionType, createReactionRequest.reactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reactionType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateReactionRequest {\n");
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
