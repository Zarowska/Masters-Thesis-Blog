package com.zarowska.cirkle.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.zarowska.cirkle.api.model.FriendshipRequest;
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
 * FriendshipRequestList
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-06T01:58:39.202110500+01:00[Europe/Warsaw]")
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class FriendshipRequestList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private List<@Valid FriendshipRequest> items;

    public FriendshipRequestList items(List<@Valid FriendshipRequest> items) {
        this.items = items;
        return this;
    }

    public FriendshipRequestList addItemsItem(FriendshipRequest itemsItem) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(itemsItem);
        return this;
    }

    /**
     * Get items
     * @return items
     */
    @Valid
    @Schema(name = "items", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("items")
    public List<@Valid FriendshipRequest> getItems() {
        return items;
    }

    public void setItems(List<@Valid FriendshipRequest> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FriendshipRequestList friendshipRequestList = (FriendshipRequestList) o;
        return Objects.equals(this.items, friendshipRequestList.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FriendshipRequestList {\n");
        sb.append("    items: ").append(toIndentedString(items)).append("\n");
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
