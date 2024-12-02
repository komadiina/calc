package org.etfbl.tks.exceptions;

/**
 * Used for enforcing number value boundaries (for example, exponentiation requires the value to be in range
 * <code>[0, 10]</code>)
 * @see org.etfbl.tks.CalculatorAdvanced
 */
public class NumberNotInAreaException extends Exception {
    public NumberNotInAreaException(String message) { super(message); }
    public NumberNotInAreaException() {}
}
