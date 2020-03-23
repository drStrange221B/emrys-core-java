package com.company.java.concurrency.index.project;

import java.io.*;

public class IOUtil {


    public static String read(InputStream inputStream){

        StringBuilder builder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){

            System.out.println("Read method of IOUtil");


            String txt ;


            while((txt=bufferedReader.readLine())!=null) {
                builder.append(txt).append("\n");
            }
            System.out.println(builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();

    }
}
