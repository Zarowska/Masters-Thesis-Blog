package com.zarowska.cirkle.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.zarowska.cirkle.api.model.Comment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * CommentPage
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class CommentPage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer totalPages;

    private Long totalElements;

    private Boolean first;

    private Boolean last;

    private org.springframework.data.domain.Sort sort;

    private Integer size;

    @Valid
    private List<@Valid Comment> content;

    private Integer number;

    private Integer numberOfElements;

    private Boolean empty;

    public CommentPage totalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    /**
     * Get totalPages
     * @return totalPages
     */
    @Schema(name = "totalPages", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalPages")
    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public CommentPage totalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    /**
     * Get totalElements
     * @return totalElements
     */
    @Schema(name = "totalElements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalElements")
    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public CommentPage first(Boolean first) {
        this.first = first;
        return this;
    }

    /**
     * Get first
     * @return first
     */
    @Schema(name = "first", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("first")
    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public CommentPage last(Boolean last) {
        this.last = last;
        return this;
    }

    /**
     * Get last
     * @return last
     */
    @Schema(name = "last", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("last")
    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public CommentPage sort(org.springframework.data.domain.Sort sort) {
        this.sort = sort;
        return this;
    }

    /**
     * Get sort
     * @return sort
     */
    @Valid
    @Schema(name = "sort", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("sort")
    public org.springframework.data.domain.Sort getSort() {
        return sort;
    }

    public void setSort(org.springframework.data.domain.Sort sort) {
        this.sort = sort;
    }

    public CommentPage size(Integer size) {
        this.size = size;
        return this;
    }

    /**
     * Get size
     * @return size
     */
    @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public CommentPage content(List<@Valid Comment> content) {
        this.content = content;
        return this;
    }

    public CommentPage addContentItem(Comment contentItem) {
        if (this.content == null) {
            this.content = new ArrayList<>();
        }
        this.content.add(contentItem);
        return this;
    }

    /**
     * Get content
     * @return content
     */
    @Valid
    @Schema(name = "content", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("content")
    public List<@Valid Comment> getContent() {
        return content;
    }

    public void setContent(List<@Valid Comment> content) {
        this.content = content;
    }

    public CommentPage number(Integer number) {
        this.number = number;
        return this;
    }

    /**
     * Get number
     * @return number
     */
    @Schema(name = "number", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public CommentPage numberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
        return this;
    }

    /**
     * Get numberOfElements
     * @return numberOfElements
     */
    @Schema(name = "numberOfElements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("numberOfElements")
    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public CommentPage empty(Boolean empty) {
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
        CommentPage commentPage = (CommentPage) o;
        return Objects.equals(this.totalPages, commentPage.totalPages) && Objects.equals(this.totalElements, commentPage.totalElements) && Objects.equals(this.first, commentPage.first) && Objects.equals(this.last, commentPage.last) && Objects.equals(this.sort, commentPage.sort) && Objects.equals(this.size, commentPage.size) && Objects.equals(this.content, commentPage.content) && Objects.equals(this.number, commentPage.number) && Objects.equals(this.numberOfElements, commentPage.numberOfElements) && Objects.equals(this.empty, commentPage.empty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPages, totalElements, first, last, sort, size, content, number, numberOfElements, empty);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CommentPage {\n");
        sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
        sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
        sb.append("    first: ").append(toIndentedString(first)).append("\n");
        sb.append("    last: ").append(toIndentedString(last)).append("\n");
        sb.append("    sort: ").append(toIndentedString(sort)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    content: ").append(toIndentedString(content)).append("\n");
        sb.append("    number: ").append(toIndentedString(number)).append("\n");
        sb.append("    numberOfElements: ").append(toIndentedString(numberOfElements)).append("\n");
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
