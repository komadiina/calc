package org.etfbl.tks;

import org.etfbl.tks.exceptions.NumberNotInAreaException;
import org.etfbl.tks.exceptions.NotSupportedOperationException;

/**
 * An extended calculator class, implementing advanced mathematical operations, such as factorial calculation,
 * exponentiation, calculating the Armstrong and perfect-number properties of accumulator value. For additional
 * information, refer to the <code>Calculator</code> class, linked below.
 *
 * @author Ognjen Komadina, ind. 1163/20
 * @since 2024-11-30
 * @see Calculator
 */
public class CalculatorAdvanced extends Calculator {

    /**
     * The method performs dual responsibility, calculating the factorial value of <code>currentValue</code> (if the
     * value of <code>action</code> is equal to <code>'!'</code>, <code>0x21</code>), or exponentiation (if the provided
     * value of <code>action</code> is between <code>[0x30, 0x39]</code>).
     *
     * @param action The advanced mathematical calculation to be performed (factorial calculation or exponentiation).
     * @throws NotSupportedOperationException If the value of <code>action</code> is not in the range of
     * <code>[0x30, 0x39]</code> or is not equal to the ASCII value of '<code>!</code>' (<code>0x21</code>)
     * @throws NumberNotInAreaException Calculating the factorial value of <code>currentValue</code> is allowed only if
     * the floor-value of it is in the range of <code>[0, 10]</code>, otherwise this exception is thrown.
     */
    public void calculateAdvanced(char action)
        throws NotSupportedOperationException, NumberNotInAreaException
    {
        if (this.getCurrentValue() == null || this.getCurrentValue().isNaN() || this.getCurrentValue().isInfinite()) {
            this.setCurrentValue(0.0);
        }

        if (action == '!') {
            if (getCurrentValue() < 0.0 || getCurrentValue() > 10.0)
                throw new NumberNotInAreaException(
                        "Expected a non-negative integer in range [0, 10], received: " + getCurrentValue().intValue());
            int product = 1;
            for (int i = 1; i <= getCurrentValue().intValue(); i++) {
                product *= i;
            }
            setCurrentValue((double)product);
        } else if (action >= 0x30 && action <= 0x39) {
            int exponentValue = (int)action - 0x30;
            setCurrentValue((double)exponent(getCurrentValue().intValue(), exponentValue));
        } else throw new NotSupportedOperationException("Invalid action: " + action);
    }

    /**
     * Calculates whether the <code>currentValue</code> accumulator value satisfies the following characteristics (based
     * on the <code>value</code> symbol):
     *
     * <ul>
     *     <li><i>Armstrong's number: returns <code>true</code> for an N-digit number, if the sum of it's N-powered
     *     digits is equal to the number itself.</i></li>
     *     <li><i>Perfect number: returns <code>true</code> if the number is equal to the sum of it's common divisors.</i></li>
     * </ul>
     *
     * @param value The characteristic symbol, <code>A</code> for Armstrong condition, and <code>P</code> for the perfect
     *              number characteristic.
     * @return Boolean logic value, indicating whether the <code>currentValue</code> satisfies the specified characteristic.
     * @throws NotSupportedOperationException If any ASCII symbol for <code>value</code> other than <code>A</code> or
     * <code>P</code> is passed.
     * @throws NumberNotInAreaException If the <code>currentValue</code> mathematical floor value is less than 1 (i.e.
     * it's integer representation).
     */
    public Boolean hasCharacteristic(char value)
            throws NotSupportedOperationException, NumberNotInAreaException {
        if (getCurrentValue().intValue() < 1) {
            throw new NumberNotInAreaException(
                    "Expected floor value greater than 1, received: " + getCurrentValue().intValue());
        }

        if (value == 'A') {
            int len = String.format("%d", getCurrentValue().intValue()).length(),
                temp = getCurrentValue().intValue(),
                digit, sum = 0;

            do {
                digit = temp % 10;
                temp /= 10;
                sum += exponent(digit, len);
            } while (temp > 0);

            return sum == getCurrentValue().intValue();
        } else if (value == 'P') {
            int sum = 1;

            for (int i = 2; i <= getCurrentValue().intValue() / 2; i++) {
                if (getCurrentValue().intValue() % i == 0) {
                    sum += i;
                }
            }
            return sum == getCurrentValue().intValue();
        } else throw new NotSupportedOperationException();
    }

    /* [public] for unit-testing purposes */

    /**
     * Raises the <code>value</code> parameter to the <code>exponent</code> value, through repeated multiplication. For
     * zero-exponent (i.e. <code>a^0</code>) cases, the return value is equal to the mathematical rules, <code>0</code>.
     *
     * @param value The value to be exponentiated (base).
     * @param exponent The exponent value.
     * @return Mathematical result of <code>value</code>'s exponentiation to the power of <code>exponent</code>.
     */
    public int exponent(int value, int exponent) {
        if (exponent == 0) return 1;

        int originalValue = value;
        while (exponent > 1) {
            value *= originalValue;
            exponent--;
        }

        return value;
    }
}
