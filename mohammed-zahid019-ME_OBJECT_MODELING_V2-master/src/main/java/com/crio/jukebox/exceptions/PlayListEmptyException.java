package com.crio.jukebox.exceptions;

public class PlayListEmptyException extends RuntimeException {
    public PlayListEmptyException() {
        super();
    }

    public PlayListEmptyException(String message) {
        super(message);
    }
}