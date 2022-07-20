package com.test.soderberg.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.test.soderberg.beans.NewUser;
import com.test.soderberg.beans.Register;
import com.test.soderberg.beans.Users;
import com.test.soderberg.responses.ReqresResponse;
import com.test.soderberg.utils.Utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HTTPRequestsManager implements IHTTPRequestsManager{

	private RequestSpecification httpRequest;
	private ObjectMapper mapper;
	private ObjectNode node;
	private ReqresResponse rqResponse;

	private static final String ENDPOINT_USERS = "/users";
	private static final String ENDPOINT_REGISTER = "/register";

	public HTTPRequestsManager() {
		RestAssured.baseURI = Utils.getProperty("url_api");
		httpRequest = RestAssured.given();
		httpRequest.log().all().header("Content-type", "application/json");
		mapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		node = mapper.createObjectNode();
	}

	/**
	 * Method to check that api service is up and running
	 * @return the http response code
	 */
	public Integer checkServiceStatus() {
		return httpRequest.get(ENDPOINT_USERS).getStatusCode();
	}

    /**
     * Execute a get HTTP request to get all the list of users for a given response
	 * result page
	 * @param page the number of the page to gather results
	 * @return the ReqresResponse object with the response ingformation
	 * and the deserialized json object
     */
    public ReqresResponse<Users> getUsersList(Integer page) {
		Response response = httpRequest
				.queryParam("page", page)
				.get(ENDPOINT_USERS);
		setReqresResponse(response, Users.class);
    	return rqResponse;
    }

	/**
	 * Execute a get HTTP request to get the information for an specific user given its id
	 * @param id the id for the user to be collected
	 * @return the ReqresResponse object with the response ingformation
	 * and the deserialized json object
	 */
	public ReqresResponse<Users> getUser(Integer id) {
		Response response = httpRequest
				.get(ENDPOINT_USERS + "/" + id);
		setReqresResponse(response, Users.class);
		return rqResponse;
	}

	/**
	 * Execute a post HTTP request to create a new user
	 * @param name the name of the user
	 * @param job the job of the user
	 * @return the RReqresResponse object with the response ingformation
	 * and the deserialized json object
	 */
	public ReqresResponse<NewUser> createUser(String name, String job) {
		node.put("name", name);
		node.put("job", job);
		Response response = httpRequest
				.body(node.toPrettyString())
				.post(ENDPOINT_USERS);
		setReqresResponse(response, NewUser.class);
		return rqResponse;
	}

	/**
	 * Execute a post HTTP request to unsuccessful user registration
	 * @param mail the email address for the user to be registered
	 * @return the ReqresResponse object with the response information
	 * and the deserialized json object
	 */
	public ReqresResponse<Register> register(String mail) {
		node.put("email", mail);
		Response response = httpRequest
				.body(node.toPrettyString())
				.post(ENDPOINT_REGISTER);
		setReqresResponse(response, Register.class);
		return rqResponse;
	}

	/**
	 * Populate the ReqresResponse object from the information got from http response
	 * @param response The http response
	 * @param clazz the java type class for the deserialized json object
	 */
	private <T> void setReqresResponse(Response response, Class<T> clazz){
		rqResponse = new ReqresResponse<T>();
		try {
			rqResponse.setBodyJson(
					mapper.readValue(response.body().print(), clazz));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		rqResponse.setResponse(response);
	}
}