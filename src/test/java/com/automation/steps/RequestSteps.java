package com.automation.steps;

import com.automation.utils.ConfigManager;
import com.automation.utils.GrpcUtils;
import com.automation.utils.ProtoUtils;
import com.tp.greeting.Greeeting;
import com.tp.greeting.Greeeting.ClientInput;
import com.tp.greeting.Greeeting.ServerOutput;
import com.tp.greeting.Greeeting.UsernameInput;
import com.tp.greeting.Greeeting.UserStatusOutput;
import com.tp.greeting.Greeeting.LoginResponse;
import com.tp.greeting.Greeeting.LogoutResponse;
import com.tp.greeting.Greeeting.ResponseMessage;
import com.tp.greeting.Greeeting.RequestMessage;
import com.tp.greeting.Greeeting.ChatResponse;
import com.tp.greeting.GreeterGrpc;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;

public class RequestSteps {
    GreeterGrpc.GreeterBlockingStub blockingStub = Hooks.blockingStub;
    public static ClientInput clientInput;
    public static ServerOutput serverOutput;
    public static UsernameInput usernameInput;
    public static UserStatusOutput userStatusOutput;
    public static LoginResponse loginResponse;
    public static LogoutResponse logoutResponse;
    public static ResponseMessage responseMessage;

    @Given("The grpc server is running")
    public void the_grpc_server_is_running() {
        //this is just an assumption
    }

    @When("user sends the greet message")
    public void user_sends_the_greet_message() {
        GrpcUtils.greetRequest();
    }

    @When("user sends the greet message from {string}")
    public void userSendsTheGreetMessage(String fileName) {
        ClientInput clientInput = ProtoUtils.readNameRequestFromJsonFile(fileName);
        serverOutput = blockingStub.greet(clientInput);
    }

    @When("user sends the greet message {string} and name {string}")
    public void userSendsTheGreetMessageAndName(String message, String name) {
        GrpcUtils.sendMessageWithContent(message,name);
    }

    @When("user send the greet message {string} and name {string}")
    public void userSendTheGreetMessageAndName(String message, String name) {
        GrpcUtils.sendMessageWithName(message,name);
    }

    @When("user login with username {string} and password {string}")
    public void userLoginWithUsernameAndPassword(String username, String password) {
        GrpcUtils.loginWithCredentials(username,password);
    }

    @When("user try to logout with valid token {string}")
    public void userTryToLogoutWithValidToken(String token) {
        GrpcUtils.logoutWithToken(ConfigManager.getConfigValue(token));
    }

    @When("user try to logout with invalid token {string}")
    public void userTryToLogoutWithInvalidToken(String invalidToken) {
        GrpcUtils.logoutWithToken(ConfigManager.getConfigValue(invalidToken));
    }

    @When("the client sends the name {string} to the server")
    public void theClientSendsTheNameToTheServer(String name) {
        GrpcUtils.getStreamedGreetings(name);
    }

    @When("the client sends the following messages in a stream {string}")
    public void theClientSendsTheFollowingMessagesInAStream(String streamMessage) {
        List<String> message= Arrays.asList(streamMessage.split(","));
        try {
            GrpcUtils.clientStreamingMessage(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @When("the client sends the following messages {string}")
    public void theClientSendsTheFollowingMessages(String message) {
        List<String> chatMessage= Arrays.asList(message.split(","));
        try {
            GrpcUtils.bidirectionalStreamingMessage(chatMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
