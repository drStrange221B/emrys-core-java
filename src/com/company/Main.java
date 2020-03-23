package com.company;

import com.company.character.stream.CharacterReader;
import com.company.input.stream.BufferStream;

import java.io.File;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {


        // find the current path location of the present file.
        System.out.println(new File("").getAbsolutePath());
        System.out.println(new File(System.getProperty("user.dir"))+ "/src/resources");

        //navigate folder in java by .getParent()or .getParentfile(). on new File(System.getProperty("user.dir")

        System.out.println(new File(System.getProperty("user.dir")).getParentFile());
        System.out.println(new File(System.getProperty("user.dir")).getParentFile().getParentFile());


//        BufferStream.fileInputStream();
//
//        BufferStream.bufferedInputStream();


        //find the file encoding in the JVM
        System.out.println(System.getProperty("file.encoding"));
        //or
        System.out.println(Charset.defaultCharset());

        CharacterReader.bufferReader();



    }
}
