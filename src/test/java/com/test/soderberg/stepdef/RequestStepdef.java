package com.test.soderberg.stepdef;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import com.test.soderberg.beans.*;
import com.test.soderberg.factory.Factory;
import com.test.soderberg.managers.IHTTPRequestsManager;
import com.test.soderberg.responses.ReqresResponse;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RequestStepdef {

	private Users users;
	private User listRequestUser;
	private User singleRequestUser;
	private NewUser newUser;
	private Register register;
	private IHTTPRequestsManager request;
	private ReqresResponse<Users> response;
	private ReqresResponse<NewUser> responseNewUser;
	private ReqresResponse<Register> responseRegister;
	
	@Before
	public void setUp() {
		request = Factory.getHTTPRequestManager();
	}
	
	@Given("system is up and running")
	public void prepareScenario() {
		// As scenarios are so simples we check just the system is up and running to have something
		// to do in "Given"
		assertThat(request.checkServiceStatus()).isEqualTo(200);
	}

	@When("we make a request to get the list of users for the result page {int}")
	public void performGetUsersListRequest(Integer page) {
		response = request.getUsersList(page);
		users = response.getBodyJson();
	}

	@Then("we check we have a valid response and the list of users")
	public void checkGetUsersListResponse() {
		System.out.println(users);
		assertThat(response.getResponse().getStatusCode()).isEqualTo(200);
		assertThat(users.getPage()).isEqualTo(2);
		assertThat(users.getPer_page())
				.isEqualTo(users.getData().size());
	}

	@When("get the user with id {int}")
	public void getUserFromUsersListResponse(Integer id) {
		listRequestUser = users.getData().stream().filter(user -> user.getId().equals(id)).collect(Collectors.toList()).get(0);
	}

	@When("we make a request to get a user with user id {int}")
	public void performGetUserRequest(Integer id) {
		response = request.getUser(id);
		singleRequestUser = response.getBodyJson().getUser();
	}

	@Then("check user field values matching the expected")
	public void checkUser() {
		User user = listRequestUser!=null ? listRequestUser : singleRequestUser;
		System.out.println(user);
		assertThat(response.getResponse().getStatusCode()).isEqualTo(200);
		assertThat(user.getId()).isEqualTo(11);
		assertThat(user.getEmail()).isEqualTo("george.edwards@reqres.in");
		assertThat(user.getFirst_name()).isEqualTo("George");
		assertThat(user.getLast_name()).isEqualTo("Edwards");
		assertThat(user.getAvatar()).isEqualTo("https://reqres.in/img/faces/11-image.jpg");
	}

	@When("create a new user with name {string} and job {string}")
	public void performCreateUserRequest(String name, String job) {
		responseNewUser = request.createUser(name, job);
		newUser = responseNewUser.getBodyJson();
	}

	@Then("check user has been properly created")
	public void checkNewUser() {
		System.out.println(newUser);
		assertThat(responseNewUser.getResponse().getStatusCode()).isEqualTo(201);
		assertThat(newUser.getId()).isNotNull().isNotEmpty().isNotEqualTo("");
		assertThat(newUser.getName()).isEqualTo("Peter");
		assertThat(newUser.getJob()).isEqualTo("Sales");
		assertThat(newUser.getCreatedAt()).isNotNull().isNotEmpty().isNotEqualTo("");
	}

	@When("unsuccessful register a user with email {string}")
	public void performRegisterUserRequest(String mail) {
		responseRegister = request.register(mail);
		register = responseRegister.getBodyJson();
	}

	@Then("check an error is returned")
	public void checkUnsuccessfulRegisterUser() {
		System.out.println(register);
		assertThat(responseRegister.getResponse().getStatusCode()).isEqualTo(400);
		assertThat(register.getError()).isEqualTo("Missing password");
	}

	@Then("check user field values are the same among the 2 responses")
	public void compareUser() {
		User user = listRequestUser!=null ? listRequestUser : singleRequestUser;
		System.out.println(listRequestUser);
		assertThat(listRequestUser.getId()).isEqualTo(singleRequestUser.getId());
		assertThat(listRequestUser.getEmail()).isEqualTo(singleRequestUser.getEmail());
		assertThat(listRequestUser.getFirst_name()).isEqualTo(singleRequestUser.getFirst_name());
		assertThat(listRequestUser.getLast_name()).isEqualTo(singleRequestUser.getLast_name());
		assertThat(listRequestUser.getAvatar()).isEqualTo(singleRequestUser.getAvatar());
	}
}
