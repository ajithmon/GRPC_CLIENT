package com.automation.steps;

import com.automation.utils.ConfigManager;
import com.tp.greeting.GreeterGrpc;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.tp.interceptors.ClientLoggingInterceptor;
import com.tp.interceptors.ClientAuthenticator;

public class Hooks {

    public ManagedChannel managedChannel;
    public static GreeterGrpc.GreeterBlockingStub blockingStub;
    public static GreeterGrpc.GreeterStub newStub;

    @Before
    public void setUp() {
        ConfigManager.initConfig();
        managedChannel = ManagedChannelBuilder.forTarget(ConfigManager.getConfigValue("api.url")).usePlaintext().build();
        Channel interceptedChannel = ClientInterceptors.intercept(managedChannel,new ClientLoggingInterceptor(),new ClientAuthenticator());
        blockingStub =GreeterGrpc.newBlockingStub(interceptedChannel);
        newStub= GreeterGrpc.newStub(interceptedChannel);
    }

    @After
    public void cleanUp(){
        if(managedChannel!=null && !managedChannel.isShutdown()){
            managedChannel.shutdown();
        }
    }
}
