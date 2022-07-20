package com.test.soderberg.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty
    Integer id;
    @JsonProperty
    String email;
    @JsonProperty
    String first_name;
    @JsonProperty
    String last_name;
    @JsonProperty
    String avatar;

}
