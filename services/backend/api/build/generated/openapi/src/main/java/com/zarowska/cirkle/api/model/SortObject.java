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
 * SortObject
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class SortObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean sorted;

    private Boolean unsorted;

    private Boolean empty;

    public SortObject sorted(Boolean sorted) {
        this.sorted = sorted;
        return this;
    }

    /**
     * Get sorted
     * @return sorted
     */
    @Schema(name = "sorted", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("sorted")
    public Boolean getSorted() {
        return sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    public SortObject unsorted(Boolean unsorted) {
        this.unsorted = unsorted;
        return this;
    }

    /**
     * Get unsorted
     * @return unsorted
     */
    @Schema(name = "unsorted", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("unsorted")
    public Boolean getUnsorted() {
        return unsorted;
    }

    public void setUnsorted(Boolean unsorted) {
        this.unsorted = unsorted;
    }

    public SortObject empty(Boolean empty) {
        this.empty = empty;
        return this;
    }

    /**
     * Get empty
     * @return empty
     */
    @Schema(name = "empty", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("empty")
    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SortObject sortObject = (SortObject) o;
        return Objects.equals(this.sorted, sortObject.sorted) && Objects.equals(this.unsorted, sortObject.unsorted) && Objects.equals(this.empty, sortObject.empty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sorted, unsorted, empty);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SortObject {\n");
        sb.append("    sorted: ").append(toIndentedString(sorted)).append("\n");
        sb.append("    unsorted: ").append(toIndentedString(unsorted)).append("\n");
        sb.append("    empty: ").append(toIndentedString(empty)).append("\n");
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
