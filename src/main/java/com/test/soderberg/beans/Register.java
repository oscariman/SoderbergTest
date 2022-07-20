package com.test.soderberg.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Register {

    @JsonProperty
    String error;

}
