package com.ecobank.mandate.config;



import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


@Configuration
public class WebClientConfig {


    @Value("${apiConnectionTimeOut}")
    private static int apiConnectionTimeout;


    @Bean
    public WebClient webClient(){

        final TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, apiConnectionTimeout)
                .doOnConnected(connection -> {
                    connection.addHandler(new ReadTimeoutHandler(apiConnectionTimeout, TimeUnit.MILLISECONDS));
                    connection.addHandler(new WriteTimeoutHandler(apiConnectionTimeout, TimeUnit.MILLISECONDS));
                });

        final HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeaders(defaultHttpHeaders())
                .build();
    }


    /**
     * Sets and returns headers to be used as default
     * @return {@link Consumer<HttpHeaders>}
     */
    private Consumer<HttpHeaders> defaultHttpHeaders(){
        return httpHeaders -> {
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
        };
    }
}
