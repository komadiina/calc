package org.etfbl.tks;

import org.etfbl.tks.exceptions.*;
import org.etfbl.tks.exceptions.NotSupportedOperationException;

import java.io.FileInputStream;

/**
 * A simple, base calculator class, with an accumulator-like member, 'currentValue'.
 *
 * @author Ognjen Komadina, ind. 1163/20
 * @since 2024-11-30
 */
public class Calculator {
    private Double currentValue = 0.0;

    /**
     * Creates a new instance of <code>Calculator</code> class, with the <code>currentValue</code> field set to
     * <code>0.0</code>.
     */
    public Calculator() { this.currentValue = 0.0; }

    /**
     * Creates a new instance of <code>Calculator</code> class with the <code>currentValue</code> field set to the
     * value equal to <code>value</code> parameter. If the <code>parameter</code> is <b>null</b>, a value of <b>zero</b>
     * will be used instead, similar to the parameter-less (default) constructor.
     *
     * @param value The value to be assigned for the accumulator.
     */
    public Calculator(Double value) {
        if (value != null)
            this.currentValue = value;
        else this.currentValue = 0.0;
    }

    /**
     * Returns the current value of accumulator (currentValue).
     *
     * @return The value of currentValue.
     */
    public Double getCurrentValue() {
        return currentValue;
    }

    /**
     * Updates the value of accumulator with the input parameter's one.
     *
     * @param currentValue The new updated value of acumulator.
     */
    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }


    /**
     * Performs a basic calculator operation, such as addition, subtraction, multiplication or division. The result of
     * this operation is not returned as a function return value, but the internal accumulator is updated with the
     * aforementioned.
     *
     * @param value Value to be used as a second operand in the specified arithmetic operation.
     * @param operator The arithmetic operation indicator
     *                 (<code>+</code>, <code>-</code>, <code>*</code>, or <code>/</code>).
     * @throws DivisionByZeroException If the specified char operators division and the supplied operand is equal to zero.
     * @throws NotSupportedOperationException If any symbol other than <code>+</code>, <code>-</code>, <code>*</code>
     *                                        or <code>/</code> is passed as a parameter value of <code>operator</code>.
     */
    public void calculate(Double value, char operator)
            throws DivisionByZeroException, NotSupportedOperationException
    {
        /* should signal some kind of ArithmeticException? */
        if (value == null) { value = 0.0; }

        /* reassure that the calculator doesn't break, since a double wrapper is being used */
        if (this.currentValue == null) { this.currentValue = 0.0; }

        if (operator == '+') {
            currentValue += value;
        } else if (operator == '-') {
            currentValue -= value;
        } else if (operator == '*') {
            currentValue *= value;
        } else if (operator == '/') {
            if (value == 0.0)
                throw new DivisionByZeroException("Arithmetic exception - division by zero.");
            currentValue /= value;
        } else throw new NotSupportedOperationException("Invalid operand: " + operator);
    }
}
