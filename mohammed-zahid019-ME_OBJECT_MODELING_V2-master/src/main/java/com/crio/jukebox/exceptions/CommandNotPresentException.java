package com.crio.jukebox.exceptions;


    public class CommandNotPresentException extends RuntimeException {
    
        public CommandNotPresentException() {
            super();
        }
    
        public CommandNotPresentException(String message) {
            super(message);
        }
    }
    

