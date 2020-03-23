package com.company.java.restcall;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.net.http.HttpClient.*;

public class Java11HttpClient {


    //default
    // private final HttpClient httpClient = HttpClient.newHttpClient();

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(Version.HTTP_2)
            .build();

    //Synchronous Get Request

    public static void sentSynchronousGet() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
//                .uri(URI.create("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json"))
                  .uri(URI.create("http://google.com"))
                .build();

        HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        HttpHeaders headers = response.headers();

//        print response headers
        headers.map().forEach((k,v)->System.out.println("key = " + k + " : Value =" + v));

        //print status code
        System.out.println("Status code: " + response.statusCode());

        //print response body
        System.out.println("Response body : " + response.body());


    }



    //Asynchronous Get Request

    public static String sendAsynchronousGet() throws IOException, InterruptedException, ExecutionException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://google.com"))
                .build();

        CompletableFuture<HttpResponse<String>> response =
                httpClient.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        return response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);


    }


}
