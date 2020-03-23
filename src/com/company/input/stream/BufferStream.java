package com.company.input.stream;

import java.io.*;

public class BufferStream {

    public static void fileInputStream(){

        String inputFile = new File(System.getProperty("user.dir"))+ "/src/resources/image.png";

        String outputFile = new File(System.getProperty("user.dir")) + "/src/resources/fileinputstream_image.png";

//        File file = new File(outputFile);


        try(FileInputStream fileInputStream = new FileInputStream(inputFile);
            FileOutputStream fileOutputStream  = new FileOutputStream(outputFile))
        {
            int input=0;
            while((input=fileInputStream.read())!=-1){

                System.out.println(input);
                fileOutputStream.write(input);

            }
        }catch (FileNotFoundException e)
        {

        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void bufferedInputStream(){

        String inputFile = new File(System.getProperty("user.dir")) + "/src/resources/image.png";

        String outputFile = new File(System.getProperty("user.dir")) + "/src/resources/bufferstream_image.png";

        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(inputFile));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile))){

            byte[] bytes =new byte[200];

            int numBytesRead;

            while((numBytesRead=bufferedInputStream.read(bytes))!=-1){

                System.out.println(numBytesRead);
                bufferedOutputStream.write(bytes,0,numBytesRead);
            }
        }catch(FileNotFoundException ex)
        {

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
