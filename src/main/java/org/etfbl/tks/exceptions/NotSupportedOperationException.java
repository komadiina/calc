package org.etfbl.tks.exceptions;

/**
 * Indicates invalid operations (such as exponentiation - <code>^</code> in <code>Calculator</code> class).
 * @see org.etfbl.tks.Calculator
 * @see org.etfbl.tks.CalculatorAdvanced
 */
public class NotSupportedOperationException extends Exception {
    public NotSupportedOperationException(String message) { super(message); }
    public NotSupportedOperationException() { }
}
