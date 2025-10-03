package com.automation.utils;

import com.automation.steps.Hooks;
import com.automation.steps.RequestSteps;
import com.tp.greeting.Greeeting;
import com.tp.greeting.Greeeting.ClientInput;
import com.tp.greeting.Greeeting.LogoutRequest;
import com.tp.greeting.GreeterGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class GrpcUtils {


    public static List<String> responses = new ArrayList<>();
    
    public static void greetRequest(){
        ClientInput clientInput = ClientInput.newBuilder().setGreeting("Good Morning").setName("Ajith").build();
        try {
            GreeterGrpc.GreeterBlockingStub blockingStub = Hooks.blockingStub;
            RequestSteps.serverOutput = blockingStub.greet(clientInput);
            System.out.println(RequestSteps.serverOutput.getMessage());
        } catch (StatusRuntimeException e) {
            System.out.println("caught exception: "+e);
            RequestSteps.serverOutput = null;
        }
    }

    public static void sendMessageWithContent(String message, String name){
        ClientInput clientInput = ClientInput.newBuilder()
                .setGreeting(message)
                .setName(name)
                .build();
        try {
            GreeterGrpc.GreeterBlockingStub blockingStub = Hooks.blockingStub;
            RequestSteps.serverOutput= blockingStub.greet(clientInput);
            System.out.println(RequestSteps.serverOutput.getMessage());
        } catch (StatusRuntimeException e) {
            RequestSteps.serverOutput = null;
        }
    }
    public static void sendMessageWithName(String message, String name){
        Greeeting.UsernameInput usernameInput = Greeeting.UsernameInput.newBuilder()
                .setMessage(message)
                .setName(name)
                .build();
        try {
            GreeterGrpc.GreeterBlockingStub blockingStub = Hooks.blockingStub;
            RequestSteps.userStatusOutput= blockingStub.userStatus(usernameInput);
//            System.out.println(RequestSteps.userStatusOutput.getMessage());
        } catch (StatusRuntimeException e) {
            RequestSteps.serverOutput = null;
        }
    }
    public static void loginWithCredentials(String username,String password){
        Greeeting.LoginInput loginInput= Greeeting.LoginInput.newBuilder().setUsername(username).setPassword(password).build();
        GreeterGrpc.GreeterBlockingStub blockingStub = Hooks.blockingStub;
        RequestSteps.loginResponse=blockingStub.loginUser(loginInput);
        ConfigManager.setConfigValue("user.token",RequestSteps.loginResponse.getToken());
        System.out.println(RequestSteps.loginResponse.getMessage());
    }
    public static void logoutWithToken(String token){
        LogoutRequest logoutRequest= LogoutRequest.newBuilder().setToken(token).build();
        GreeterGrpc.GreeterBlockingStub blockingStub = Hooks.blockingStub;
        RequestSteps.logoutResponse=blockingStub.logout(logoutRequest);
        System.out.println(RequestSteps.logoutResponse.getMessage());
    }

    public static void getStreamedGreetings(String name) {
        GreeterGrpc.GreeterBlockingStub blockingStub = Hooks.blockingStub;
        Greeeting.GreetingRequest request = Greeeting.GreetingRequest.newBuilder()
                .setName(name)
                .build();

        Iterator<Greeeting.GreetingResponse> responseIterator = blockingStub.streamGreetings(request);
        while (responseIterator.hasNext()) {
            Greeeting.GreetingResponse response = responseIterator.next();
            responses.add(response.getMessage());
        }
    }
    public static void clientStreamingMessage(List<String>message) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<Greeeting.RequestMessage>streamObserver= Hooks.newStub.clientStreaming(new StreamObserver<>() {
            @Override
            public void onNext(Greeeting.ResponseMessage responseMessage1) {
                RequestSteps.responseMessage=responseMessage1;
                System.out.println("Server response count: " + responseMessage1.getResult());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Stream error: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed by server.");
                latch.countDown();
            }
        }
        );
        System.out.println("Messages::::");
        for (String msg : message) {
            System.out.println(msg);
            streamObserver.onNext(Greeeting.RequestMessage.newBuilder().setData(msg).build());
        }
        streamObserver.onCompleted();
        latch.await();

    }

    public static boolean isResponseContainsName(String name) {
        if(!name.equals("Invalid")){
            return RequestSteps.userStatusOutput.getMessage().contains(name);
        }
        return RequestSteps.loginResponse.getMessage().contains(name);
    }


    public static void bidirectionalStreamingMessage(List<String> chatMessage) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<Greeeting.ChatMessage>streamObserver=Hooks.newStub.chat(new StreamObserver<>() {
            @Override
            public void onNext(Greeeting.ChatResponse chatResponse1) {
                System.out.println("Received from server: " + chatResponse1.getFrom() + chatResponse1.getReply());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Stream error: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed by server.");
                latch.countDown();
            }
        });
        for (String msg : chatMessage) {
            streamObserver.onNext(Greeeting.ChatMessage.newBuilder().setFrom("Alice").setMessage(msg).build());
        }
        streamObserver.onCompleted();
        latch.await();

    }
}