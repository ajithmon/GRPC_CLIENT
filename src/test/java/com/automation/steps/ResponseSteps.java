package com.automation.steps;

import com.automation.utils.GrpcUtils;
import com.tp.greeting.Greeeting;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static com.automation.steps.RequestSteps.*;
import static com.automation.utils.GrpcUtils.responses;

public class ResponseSteps {
    @Then("verify response message {string}")
    public void verify_response_message(String string) {
        Assert.assertNotNull(serverOutput);
        Assert.assertEquals(string,serverOutput.getMessage());
    }

    @Then("verify the response with name {string}")
    public void verifyTheResponseWithName(String name) {
        Assert.assertNotNull(userStatusOutput);
        System.out.println(userStatusOutput.getMessage());
        Assert.assertTrue(GrpcUtils.isResponseContainsName(name));
    }

    @Then("verify login response message {string}")
    public void verifyLoginResponseMessage(String message) {
        Assert.assertNotNull(loginResponse);
        Assert.assertEquals(message,loginResponse.getMessage());
    }

    @Then("verify login response message contains {string}")
    public void verifyLoginResponseMessageContains(String message) {
        Assert.assertNotNull(loginResponse);
        Assert.assertTrue(GrpcUtils.isResponseContainsName(message));
    }

    @Then("verify logout response message {string}")
    public void verifyLogoutResponseMessage(String message) {
        Assert.assertNotNull(logoutResponse);
        Assert.assertEquals(message,logoutResponse.getMessage());
    }

    @Then("the client should receive {int} greeting messages")
    public void theClientShouldReceiveGreetingMessages(int size) {
        Assert.assertEquals(size,responses.size());
    }

    @Then("the client receives a response {string}")
    public void theClientReceivesAResponse(String messageCount) {
       Assert.assertEquals(messageCount,responseMessage.getResult());
    }

    @And("the stream completes successfully")
    public void theStreamCompletesSuccessfully() {

    }
}
