package com.utility;

public class FileDecodingException extends Exception {
    FileDecodingException() {
        super("File was damaged");
    }
}
