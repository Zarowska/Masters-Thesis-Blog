package com.zarowska.cirkle.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.zarowska.cirkle.api.model.User;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
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
 * MessageEvent
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class MessageEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private User author;

    private Integer count;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime lastAt;

    public MessageEvent author(User author) {
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

    public MessageEvent count(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * Get count
     * @return count
     */
    @Schema(name = "count", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public MessageEvent lastAt(OffsetDateTime lastAt) {
        this.lastAt = lastAt;
        return this;
    }

    /**
     * Get lastAt
     * @return lastAt
     */
    @Valid
    @Schema(name = "lastAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("lastAt")
    public OffsetDateTime getLastAt() {
        return lastAt;
    }

    public void setLastAt(OffsetDateTime lastAt) {
        this.lastAt = lastAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageEvent messageEvent = (MessageEvent) o;
        return Objects.equals(this.author, messageEvent.author) && Objects.equals(this.count, messageEvent.count) && Objects.equals(this.lastAt, messageEvent.lastAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, count, lastAt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MessageEvent {\n");
        sb.append("    author: ").append(toIndentedString(author)).append("\n");
        sb.append("    count: ").append(toIndentedString(count)).append("\n");
        sb.append("    lastAt: ").append(toIndentedString(lastAt)).append("\n");
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
