package com.test.soderberg.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class Users {


    /**
     * Constructor to be used to deserialize user information as we could have 2 different
     * user json objects
     * @param page
     * @param per_page
     * @param total
     * @param total_pages
     * @param data
     */
    @JsonCreator
    public Users(
            @JsonProperty("page") Integer page, @JsonProperty("per_page") Integer per_page,
            @JsonProperty("total") Integer total, @JsonProperty("total_pages") Integer total_pages,
            @JsonProperty("data") JsonNode data){
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        ObjectMapper mapper = new ObjectMapper();
        if (data instanceof ArrayNode){
            try {
                this.data = mapper.readerForListOf(User.class).readValue((ArrayNode) data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                user = mapper.readerFor(User.class).readValue((ObjectNode) data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    Integer page;
    Integer per_page;
    Integer total;
    Integer total_pages;
    List<User> data;
    User user;
}
