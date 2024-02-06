package com.zarowska.cirkle.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * PageableObject
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-07T02:22:46.805014800+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class PageableObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNumber;

    private Integer pageSize;

    private Boolean paged;

    private Boolean unpaged;

    private org.springframework.data.domain.Sort sort;

    private Long offset;

    public PageableObject pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    /**
     * Get pageNumber
     * @return pageNumber
     */
    @Schema(name = "pageNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("pageNumber")
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageableObject pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Get pageSize
     * @return pageSize
     */
    @Schema(name = "pageSize", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageableObject paged(Boolean paged) {
        this.paged = paged;
        return this;
    }

    /**
     * Get paged
     * @return paged
     */
    @Schema(name = "paged", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("paged")
    public Boolean getPaged() {
        return paged;
    }

    public void setPaged(Boolean paged) {
        this.paged = paged;
    }

    public PageableObject unpaged(Boolean unpaged) {
        this.unpaged = unpaged;
        return this;
    }

    /**
     * Get unpaged
     * @return unpaged
     */
    @Schema(name = "unpaged", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("unpaged")
    public Boolean getUnpaged() {
        return unpaged;
    }

    public void setUnpaged(Boolean unpaged) {
        this.unpaged = unpaged;
    }

    public PageableObject sort(org.springframework.data.domain.Sort sort) {
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

    public PageableObject offset(Long offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Get offset
     * @return offset
     */
    @Schema(name = "offset", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("offset")
    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageableObject pageableObject = (PageableObject) o;
        return Objects.equals(this.pageNumber, pageableObject.pageNumber) && Objects.equals(this.pageSize, pageableObject.pageSize) && Objects.equals(this.paged, pageableObject.paged) && Objects.equals(this.unpaged, pageableObject.unpaged) && Objects.equals(this.sort, pageableObject.sort) && Objects.equals(this.offset, pageableObject.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize, paged, unpaged, sort, offset);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PageableObject {\n");
        sb.append("    pageNumber: ").append(toIndentedString(pageNumber)).append("\n");
        sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
        sb.append("    paged: ").append(toIndentedString(paged)).append("\n");
        sb.append("    unpaged: ").append(toIndentedString(unpaged)).append("\n");
        sb.append("    sort: ").append(toIndentedString(sort)).append("\n");
        sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
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
