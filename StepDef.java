package org.stepdef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.base.AddAddress_Input_Pojo;
import org.base.AddAddress_Output_Pojo;
import org.base.BaseClass;
import org.base.DeleteAddress_Input_Pojo;
import org.base.EndPoints;
import org.base.Login_Output_Pojo;
import org.base.UpdateAddress_Input_Pojo;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StepDef  extends BaseClass{

	static Response response ;
	static String logtoken;
	static int address_id;


	@When("user should add header {string} and {string}")
	public void user_should_add_header_and(String key, String value) {
		getHeater(key, value);
	}

	@When("user should add request body for login")
	public void user_should_add_request_body_for_login() throws Exception {
		basicAuthentication(getPropertyValue("username"), getPropertyValue("password"));
	}

	@When("user should send post request for login")
	public void user_should_send_post_request_for_login() {
		response = methodeType("POST", EndPoints.LOGIN);
		getStatusCode(response);
		Login_Output_Pojo login_Output_Pojo = response.as(Login_Output_Pojo.class);
		logtoken = login_Output_Pojo.getData().getLogtoken();
		System.out.println(logtoken);
		System.out.println(login_Output_Pojo.getMessage());

	}

	@Then("user should verify statusCode for  matched with {int}")
	public void user_should_verify_statusCode_for_matched_with(Integer expected) {
		Assert.assertEquals(response.getStatusCode(),expected );

	}

	@Then("user should verify response body for login {string}")
	public void user_should_verify_response_body_for_login(String expected) {
		Assert.assertEquals(response.getBody().jsonPath().get("message"), expected);
	}

	@When("user should add header {string} ,{string} and add bearer authentication to authorize endpoint")
	public void user_should_add_header_and_add_bearer_authentication_to_authorize_endpoint(String key, String value) {
		List<Header> headers = new ArrayList<Header>();
		Header header = new Header(key, value);
		Header header2 = new Header("Authorization", "Bearer " +logtoken);
		headers.add(header);
		headers.add(header2);

		Headers headers2 = new Headers(headers);
		getHeaders(headers2);



	}

	@When("user should add request body for addUserAddress")
	public void user_should_add_request_body_for_addUserAddress() throws JsonProcessingException {
		AddAddress_Input_Pojo addAddress_Input_Pojo = new AddAddress_Input_Pojo("Balamurugan", "I", "8754248226", "White Building", 1, 31, 91,
				"607106", "Custom colony", "home");
		ObjectMapper mapper = new ObjectMapper();
		String reqBody = mapper.writeValueAsString(addAddress_Input_Pojo);
		body(reqBody);

	}
	@When("user should send post request for addUserAddress")
	public void user_should_send_post_request_for_addUserAddress() {
		response = methodeType("POST", EndPoints.ADD_ADDRESS);
		getStatusCode(response);
		address_id = response.getBody().jsonPath().get("address_id");	
		System.out.println(address_id);


	}

	@Then("user should verify response body for addUserAddress {string}")
	public void user_should_verify_response_body_for_addUserAddress(String expected) {
		String  actual = response.getBody().jsonPath().get("message");
		System.out.println(actual);
		Assert.assertEquals(actual, expected);
	}

	@When("user should add request body for updateUserAddress")
	public void user_should_add_request_body_for_updateUserAddress() throws Throwable {
		UpdateAddress_Input_Pojo updateAddress_Input_Pojo =new UpdateAddress_Input_Pojo(String.valueOf(address_id), "Raji", "B", "8124590560", "Ranga Building", "Tamil Nadu", "Panruti", "India", "607106", "selam Main Road", "Home"); 
		ObjectMapper mapper = new ObjectMapper();
		String reqBody = mapper.writeValueAsString(updateAddress_Input_Pojo);
		body(reqBody);		

	}

	@When("user should send put request for updateUserAddress")
	public void user_should_send_put_request_for_updateUserAddress() {
		response = methodeType("PUT", EndPoints.UPDATE_ADDRESS);
		getStatusCode(response);
		String message = response.getBody().jsonPath().get("message");
		System.out.println(message);
	}

	@When("user should verify response body for updateUserAddress {string}")
	public void user_should_verify_response_body_for_updateUserAddress(String expected) {
		Assert.assertEquals(response.getBody().jsonPath().get("message"), expected);




	}

	@When("user should send get request for getUserAddress")
	public void user_should_send_get_request_for_getUserAddress() {
		 response = methodeType("GET", EndPoints.GET_ADDRESS);
		 getStatusCode(response);
		 Object message = response.getBody().jsonPath().get("message");
		 System.out.println(message);
		}

	@When("user should verify response body for getAddress {string}")
	public void user_should_verify_response_body_for_getAddress(String expected) {
		Assert.assertEquals( response.getBody().jsonPath().get("message"), expected);
			}
@When("user should add request body for daleteUserAddress")
	public void user_should_add_request_body_for_daleteUserAddress() throws Exception {
	DeleteAddress_Input_Pojo deleteAddress_Input_Pojo = new DeleteAddress_Input_Pojo(String.valueOf(address_id));
	ObjectMapper mapper = new ObjectMapper();
	String reqBody = mapper.writeValueAsString(deleteAddress_Input_Pojo);
	body(reqBody);

}

	@When("user should send delete request for deleteUserAddress")
	public void user_should_send_delete_request_for_deleteUserAddress() {
		response = methodeType("DELETE", EndPoints.DELETE_ADDRESS);
		getStatusCode(response);
		Object message = response.getBody().jsonPath().get("message");
		System.out.println(message);

	
	}

	@Then("user should verify response body for deleteAddress {string}")
	public void user_should_verify_response_body_for_deleteAddress(String exp) {
		Assert.assertEquals(response.getBody().jsonPath().get("message"), exp);

	
	}
	@When("user should send post request for logoutUserAddress")
	public void user_should_send_post_request_for_logoutUserAddress() {
		response = methodeType("POST", EndPoints.LOGOUT);
		getStatusCode(response);
		Object message = response.getBody().jsonPath().get("message");
		System.out.println(message);

	
	}

	@Then("user should verify response body for logoutuserAddress {string}")
	public void user_should_verify_response_body_for_logoutuserAddress(String exp) {
		Assert.assertEquals(response.getBody().jsonPath().get("message"), exp);

	}








}