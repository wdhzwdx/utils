package com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileIO1 {
    private static InputStream inputStream = null;

    public static class FileInstanse{
        private  FileInstanse(){}
        public static InputStream getInputStream() throws FileNotFoundException {
            inputStream  = new FileInputStream(new File("D://db.txt"));
            return inputStream;
        }
    }


    public static void main(String[] args) throws Exception {
        
    }

    
}
