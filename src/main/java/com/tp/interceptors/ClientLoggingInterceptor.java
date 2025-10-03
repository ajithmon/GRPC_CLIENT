package com.tp.interceptors;

import io.grpc.*;

import java.util.logging.Logger;

public class ClientLoggingInterceptor implements ClientInterceptor {
    Logger logger=Logger.getLogger(ClientLoggingInterceptor.class.getName());
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        logger.info("Client method "+methodDescriptor.getFullMethodName());
        return channel.newCall(methodDescriptor,callOptions);
    }

}
