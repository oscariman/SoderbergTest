package com.test.soderberg.managers;

import com.test.soderberg.beans.NewUser;
import com.test.soderberg.beans.Register;
import com.test.soderberg.beans.Users;
import com.test.soderberg.responses.ReqresResponse;
import io.restassured.response.Response;

public interface IHTTPRequestsManager {

	Integer checkServiceStatus();
	ReqresResponse<Users> getUsersList(Integer page);

	ReqresResponse<Users> getUser(Integer id);

	ReqresResponse<NewUser> createUser(String name, String job);

	ReqresResponse<Register> register(String mail);

}
