package com.test.soderberg.responses;

import io.restassured.response.Response;
import lombok.Data;

@Data
public class ReqresResponse<T> {

    private T bodyJson;
    private Response response;
}
