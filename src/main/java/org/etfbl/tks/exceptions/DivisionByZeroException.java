package org.etfbl.tks.exceptions;

/**
 * Used for signaling zero-division exceptions.
 * @see org.etfbl.tks.Calculator
 */
public class DivisionByZeroException extends Exception {
    public DivisionByZeroException(String message) { super(message); }
    public DivisionByZeroException() { }
}
