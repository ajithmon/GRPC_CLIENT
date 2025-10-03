package com.tp.interceptors;

import io.grpc.*;

public class ClientAuthenticator implements ClientInterceptor {

    private final String authToken = "Bearer secret-token";

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {

        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(Metadata.Key.of("authorization",Metadata.ASCII_STRING_MARSHALLER),authToken);
                super.start(responseListener, headers);
            }
        };
    }
}
