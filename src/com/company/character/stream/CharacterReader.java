package com.company.character.stream;

import java.io.*;

public class CharacterReader {

    public static void bufferReader(){

        StringBuilder stringBuilder = new StringBuilder();

        String inputFile = new File(System.getProperty("user.dir")) + "/src/resources/reader.txt";
        String outputFile = new File(System.getProperty("user.dir")) + "/src/resources/writer.txt";

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile))))
        {
            String txt = "";
            while((txt=bufferedReader.readLine())!=null )
            {
                stringBuilder.append(txt).append("\n");

                System.out.println(stringBuilder.toString());

                bufferedWriter.write(stringBuilder.toString());
            }

        }catch ( IOException ex)
        {
            ex.printStackTrace();
        }
    }


}
