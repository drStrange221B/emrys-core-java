package com.company;


import com.company.java.restcall.Java11HttpClient;

public class Main {

    public static void main(String[] args) throws Exception {

        Java11HttpClient.sentSynchronousGet();

        System.out.println(Java11HttpClient.sendAsynchronousGet());

    }
}
