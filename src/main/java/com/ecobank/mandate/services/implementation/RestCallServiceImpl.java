package com.ecobank.mandate.services.implementation;

/* @author sa_oladipo created on 1/24/22 */



import com.ecobank.mandate.exception.GenericException;
import com.ecobank.mandate.constant.ResponseCodes;
import com.ecobank.mandate.services.RestCallService;
import com.ecobank.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class RestCallServiceImpl implements RestCallService {


    private final WebClient webClient;

    private final Logger log = LoggerFactory.getLogger(RestCallServiceImpl.class);


    @Override
    public <T> T get(String url, Consumer<HttpHeaders> httpHeaders){

        return getRequiredClient(url, httpHeaders, HttpMethod.GET)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->  clientResponse.bodyToMono(String.class).flatMap(errorBody-> Mono.just(new GenericException(ResponseCodes.PROCESS_ERROR, errorBody, HttpStatus.valueOf(clientResponse.rawStatusCode())))))
                .bodyToMono(new ParameterizedTypeReference<T>() {
                })
                .doOnError(error->{
                    log.info("Error occurred while making a GET request to: {}", url);
                    log.error(error.getMessage(), error);
                })
                .doOnSuccess(response-> log.info("GET request to {} was successful", url))
                .block();
    }


    public <T> T get(String url, Consumer<HttpHeaders> httpHeaders, Map<String, Object> queryParams){

        return getRequiredClient(url, httpHeaders, HttpMethod.GET, queryParams)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->  clientResponse.bodyToMono(String.class).flatMap(errorBody-> Mono.just(new GenericException(ResponseCodes.PROCESS_ERROR, errorBody, HttpStatus.valueOf(clientResponse.rawStatusCode())))))
                .bodyToMono(new ParameterizedTypeReference<T>() {
                })
                .doOnError(error->{
                    log.info("Error occurred while making a GET request to: {}", url);
                    log.error(error.getMessage(), error);
                })
                .doOnSuccess(response-> log.info("GET request to {} was successful", url))
                .block();
    }


    @Override
    public <T, V> T post(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData){

        WebClient.RequestBodySpec client = getRequiredClient(url, httpHeaders, HttpMethod.POST);
        WebClient.RequestHeadersSpec clientBody = (isFormData) ? client.body(BodyInserters.fromFormData(HttpUtils.getFormData((Map<String, Object>) requestObject)))  : client.body(Mono.just(requestObject), requestClass);

        return clientBody
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->  clientResponse.bodyToMono(String.class).flatMap(errorBody-> Mono.just(new GenericException(ResponseCodes.PROCESS_ERROR, errorBody, HttpStatus.valueOf(clientResponse.rawStatusCode())))))
                .bodyToMono(new ParameterizedTypeReference<T>() {
                })
                .doOnError(error->{
                    log.info("Error occurred while making a POST request to: {}", url);
                    log.error(error.getMessage(), error);
                })
                .doOnSuccess(response-> log.info("POST request to {} was successful", url))
                .block();
    }


    @Override
    public <T, V> T put(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData){

        WebClient.RequestBodySpec client = getRequiredClient(url, httpHeaders, HttpMethod.PUT);
        WebClient.RequestHeadersSpec clientBody = (isFormData) ? client.body(BodyInserters.fromFormData(HttpUtils.getFormData((Map<String, Object>) requestObject)))  : client.body(Mono.just(requestObject), requestClass);

        return clientBody
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->  clientResponse.bodyToMono(String.class).flatMap(errorBody-> Mono.just(new GenericException(ResponseCodes.PROCESS_ERROR, errorBody, HttpStatus.valueOf(clientResponse.rawStatusCode())))))
                .bodyToMono(new ParameterizedTypeReference<T>() {
                })
                .doOnError(error->{
                    log.info("Error occurred while making a PUT request to: {}", url);
                    log.error(error.getMessage(), error);
                })
                .doOnSuccess(response-> log.info("PUT request to {} was successful", url))
                .block();
    }


    @Override
    public <T, V> T patch(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData){

        WebClient.RequestBodySpec client = getRequiredClient(url, httpHeaders, HttpMethod.PATCH);
        WebClient.RequestHeadersSpec clientBody = (isFormData) ? client.body(BodyInserters.fromFormData(HttpUtils.getFormData((Map<String, Object>) requestObject)))  : client.body(Mono.just(requestObject), requestClass);

        return clientBody
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->  clientResponse.bodyToMono(String.class).flatMap(errorBody-> Mono.just(new GenericException(ResponseCodes.PROCESS_ERROR, errorBody, HttpStatus.valueOf(clientResponse.rawStatusCode())))))
                .bodyToMono(new ParameterizedTypeReference<T>() {
                })
                .doOnError(error->{
                    log.info("Error occurred while making a PATCH request to: {}", url);
                    log.error(error.getMessage(), error);
                })
                .doOnSuccess(response-> log.info("PATCH request to {} was successful", url))
                .block();
    }


    @Override
    public <T, V> T delete(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData){

        WebClient.RequestBodySpec client = getRequiredClient(url, httpHeaders, HttpMethod.DELETE);
        WebClient.RequestHeadersSpec clientBody = (isFormData) ? client.body(BodyInserters.fromFormData(HttpUtils.getFormData((Map<String, Object>) requestObject)))  : client.body(Mono.just(requestObject), requestClass);

        return clientBody
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->  clientResponse.bodyToMono(String.class).flatMap(errorBody-> Mono.just(new GenericException(ResponseCodes.PROCESS_ERROR, errorBody, HttpStatus.valueOf(clientResponse.rawStatusCode())))))
                .bodyToMono(new ParameterizedTypeReference<T>() {
                })
                .doOnError(error->{
                    log.info("Error occurred while making a DELETE request to: {}", url);
                    log.error(error.getMessage(), error);
                })
                .doOnSuccess(response-> log.info("DELETE request to {} was successful with response: {}", url, response))
                .block();
    }



    public <T> T getResponseConcreteEntity(Object response, Class<T> tClass, String errorMessage){

        log.info("Response: {}", response);

        if(response == null)
            throw new GenericException(ResponseCodes.PROCESS_ERROR, errorMessage, null);

        try{
            return new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(response, tClass);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GenericException(ResponseCodes.PROCESS_ERROR, "Couldn't convert response payload to valid json", null);
        }
    }



    /**
     * Returns already set-up client based on HttpMethod
     * @param url: the url
     * @param httpHeaders: the http headers
     * @param httpMethod: the http method
     * @param others: other needed objects as defined in this method
     * @return the {@link WebClient.RequestBodySpec} requestBodySpec object
     */
    private WebClient.RequestBodySpec getRequiredClient(String url, Consumer<HttpHeaders> httpHeaders, HttpMethod httpMethod, Object ... others){

        WebClient.RequestBodySpec requestBodySpec;

        log.info("REST call to {} has been initiated", url);
        switch (httpMethod){
            case GET:
                Map<String, Object> params =  null;
                boolean isParamsAvailable = false;

                if(others != null && others.length != 0){
                    Object firstParam = others[0];
                    if(firstParam instanceof Map){
                        try {
                            params = (Map<String, Object>) firstParam;
                            isParamsAvailable = true;
                        }catch (Exception e){
                            log.error("Couldn't get required params");
                            throw new GenericException(ResponseCodes.PROCESS_ERROR, "Couldn't get required params", HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                }

                MultiValueMap<String, String> finalParams = HttpUtils.getFormData(params);
                log.info("Params: {}", finalParams);
                requestBodySpec = (!isParamsAvailable)
                        ?
                        webClient.method(HttpMethod.GET).uri(url) :
                        webClient.method(HttpMethod.GET).uri(url, uriBuilder ->
                                uriBuilder.queryParams(finalParams).build()
                                );
                break;
            case POST:
                requestBodySpec = webClient.post().uri(url);
                break;
            case PUT:
                requestBodySpec = webClient.put().uri(url);
                break;
            case PATCH:
                requestBodySpec = webClient.patch().uri(url);
                break;
            case DELETE:
                requestBodySpec = webClient.method(HttpMethod.DELETE).uri(url);
                break;
            default:
                requestBodySpec = webClient.method(HttpMethod.GET).uri(url);
        }

        log.info("Using {} HTTP verb", httpMethod);

        if(httpHeaders != null)
            requestBodySpec =  requestBodySpec
                    .headers(httpHeaders);

        log.info("Setup of REST call object completed: {}", requestBodySpec);
        return requestBodySpec;
    }
}
