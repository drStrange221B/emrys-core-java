package com.company.java.concurrency.index.project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpConnect {

    public static String downloading(String sourceUrl) throws  IOException,InterruptedException {

        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2).build();


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(sourceUrl))
                .build();

        HttpResponse response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.body().toString());
        return response.body().toString();


    }
}
