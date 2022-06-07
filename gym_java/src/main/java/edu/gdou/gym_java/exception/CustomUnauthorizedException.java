package edu.gdou.gym_java.exception;

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String msg) {
        super(msg);
    }

    public CustomUnauthorizedException() {
        super();
    }
}