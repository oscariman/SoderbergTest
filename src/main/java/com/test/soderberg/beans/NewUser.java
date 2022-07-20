package com.test.soderberg.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewUser {

    @JsonProperty
    String id;
    @JsonProperty
    String job;
    @JsonProperty
    String name;
    @JsonProperty
    String createdAt;

}
