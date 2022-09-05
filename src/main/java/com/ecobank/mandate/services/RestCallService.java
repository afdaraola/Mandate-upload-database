package com.ecobank.mandate.services;

/* @author sa_oladipo created on 1/24/22 */


import org.springframework.http.HttpHeaders;

import java.util.Map;
import java.util.function.Consumer;


public interface RestCallService {


    /**
     *
     * @param url: The url to call GET
     * @param httpHeaders: The http headers to set
     * @param <T>: The request type
     * @return the rest call response {@link T}
     */
    <T> T get(String url, Consumer<HttpHeaders> httpHeaders);


    /**
     *
     * @param url: The url to call GET
     * @param httpHeaders: The http headers to set
     * @param queryParams: the query params
     * @param <T>: The request type
     * @return the rest call response {@link T}
     */
    <T> T get(String url, Consumer<HttpHeaders> httpHeaders, Map<String, Object> queryParams);


    /**
     *
     * @param <T>: The response type
     * @param <V>: The value type
     * @param url : The url to call POST
     * @param requestObject : The request object
     * @param httpHeaders : The htt* @param isFormData: to specify if request body is form data or not
     * @param requestClass: the request class
     * @return the rest call response {@link T}
     */
    <T, V> T post(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData);


    /**
     *
     * @param <T>: The response type
     * @param <V>: The value type
     * @param url : The url to call POST
     * @param requestObject : The request object
     * @param httpHeaders : The htt* @param isFormData: to specify if request body is form data or not
     * @param requestClass: the request class
     * @return the rest call response {@link T}
     */
    <T, V> T put(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData);


    /**
     *
     * @param <T>: The response type
     * @param <V>: The value type
     * @param url : The url to call POST
     * @param requestObject : The request object
     * @param httpHeaders : The htt* @param isFormData: to specify if request body is form data or not
     * @param requestClass: the request class
     * @return the rest call response {@link T}
     */
    <T, V> T patch(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData);


    /**
     *
     * @param <T>: The response type
     * @param <V>: The value type
     * @param url : The url to call POST
     * @param requestObject : The request object
     * @param httpHeaders : The htt* @param isFormData: to specify if request body is form data or not
     * @param requestClass: the request class
     * @return the rest call response {@link T}
     */
    <T, V> T delete(String url, Consumer<HttpHeaders> httpHeaders, V requestObject, Class<V> requestClass, boolean isFormData);
}
