package com.crio.jukebox.exceptions;

public class SongNotAvailableException extends RuntimeException {
    public SongNotAvailableException() {
        super();
    }

    public SongNotAvailableException(String message) {
        super(message);
    }
}
