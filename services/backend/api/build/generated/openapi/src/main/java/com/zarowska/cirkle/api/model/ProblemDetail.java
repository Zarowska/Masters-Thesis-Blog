package com.zarowska.cirkle.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
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
 * ProblemDetail
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class ProblemDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private URI type;

    private String title;

    private Integer status;

    private String detail;

    private URI instance;

    private Object properties;

    public ProblemDetail type(URI type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     * @return type
     */
    @Valid
    @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("type")
    public URI getType() {
        return type;
    }

    public void setType(URI type) {
        this.type = type;
    }

    public ProblemDetail title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get title
     * @return title
     */
    @Schema(name = "title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProblemDetail status(Integer status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     * @return status
     */
    @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ProblemDetail detail(String detail) {
        this.detail = detail;
        return this;
    }

    /**
     * Get detail
     * @return detail
     */
    @Schema(name = "detail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ProblemDetail instance(URI instance) {
        this.instance = instance;
        return this;
    }

    /**
     * Get instance
     * @return instance
     */
    @Valid
    @Schema(name = "instance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("instance")
    public URI getInstance() {
        return instance;
    }

    public void setInstance(URI instance) {
        this.instance = instance;
    }

    public ProblemDetail properties(Object properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get properties
     * @return properties
     */
    @Schema(name = "properties", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("properties")
    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProblemDetail problemDetail = (ProblemDetail) o;
        return Objects.equals(this.type, problemDetail.type) && Objects.equals(this.title, problemDetail.title) && Objects.equals(this.status, problemDetail.status) && Objects.equals(this.detail, problemDetail.detail) && Objects.equals(this.instance, problemDetail.instance) && Objects.equals(this.properties, problemDetail.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, status, detail, instance, properties);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProblemDetail {\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
        sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
        sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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
