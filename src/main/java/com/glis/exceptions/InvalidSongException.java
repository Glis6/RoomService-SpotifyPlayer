package com.glis.exceptions;

/**
 * @author Glis
 */
public class InvalidSongException extends Exception {
    /**
     * {@inheritDoc}
     */
    public InvalidSongException() {
    }

    /**
     * {@inheritDoc}
     */
    public InvalidSongException(String message) {
        super(message);
    }
}
