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
 * ApiInfo
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class ApiInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String version;

    private String buildDate;

    private String buildNum;

    private String environment;

    public ApiInfo version(String version) {
        this.version = version;
        return this;
    }

    /**
     * Get version
     * @return version
     */
    @Schema(name = "version", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ApiInfo buildDate(String buildDate) {
        this.buildDate = buildDate;
        return this;
    }

    /**
     * Get buildDate
     * @return buildDate
     */
    @Schema(name = "buildDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("buildDate")
    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public ApiInfo buildNum(String buildNum) {
        this.buildNum = buildNum;
        return this;
    }

    /**
     * Get buildNum
     * @return buildNum
     */
    @Schema(name = "buildNum", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("buildNum")
    public String getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(String buildNum) {
        this.buildNum = buildNum;
    }

    public ApiInfo environment(String environment) {
        this.environment = environment;
        return this;
    }

    /**
     * Get environment
     * @return environment
     */
    @Schema(name = "environment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("environment")
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiInfo apiInfo = (ApiInfo) o;
        return Objects.equals(this.version, apiInfo.version) && Objects.equals(this.buildDate, apiInfo.buildDate) && Objects.equals(this.buildNum, apiInfo.buildNum) && Objects.equals(this.environment, apiInfo.environment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, buildDate, buildNum, environment);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiInfo {\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    buildDate: ").append(toIndentedString(buildDate)).append("\n");
        sb.append("    buildNum: ").append(toIndentedString(buildNum)).append("\n");
        sb.append("    environment: ").append(toIndentedString(environment)).append("\n");
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
