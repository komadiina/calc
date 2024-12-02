package org.etfbl.tks;

import org.etfbl.tks.exceptions.DivisionByZeroException;
import org.etfbl.tks.exceptions.NotSupportedOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    private Calculator calc = new Calculator();

    @BeforeEach
    void setUp() throws Exception {
        assertNotNull(calc);
        calc.setCurrentValue(0.0);
    }

    /* Atomic, branch coverage testing */
    @Test
    public void testCalculateAdd()
            throws DivisionByZeroException, NotSupportedOperationException {
        calc.calculate(1.0, '+');
        assertThat(calc.getCurrentValue(), is(equalTo(1.0)));
    }

    @Test
    public void testCalculateSubtract()
            throws DivisionByZeroException, NotSupportedOperationException {
        calc.calculate(1.0, '-');
        assertThat(calc.getCurrentValue(), is(equalTo(-1.0)));
    }

    @Test
    public void testCalculateMultiply()
            throws DivisionByZeroException, NotSupportedOperationException {
        calc.calculate(1.0, '+');
        calc.calculate(3.333333333, '*');
        assertThat(calc.getCurrentValue(), is(equalTo(3.333333333)));
    }

    @Test
    public void testCalculateDivide()
            throws DivisionByZeroException, NotSupportedOperationException {
        calc.calculate(1.0, '+');
        calc.calculate(2.0, '/');
        assertThat(calc.getCurrentValue(), is(equalTo(0.5)));
    }

    @Test
    public void testCalculateUnsupportedOp() {
        Exception ex = assertThrows(
                NotSupportedOperationException.class,
                () -> calc.calculate(1.0, '^')
        );
        assertThat(ex, is(instanceOf(NotSupportedOperationException.class)));
    }

    @Test
    public void testCalculateZeroDivision() {
        Exception ex = assertThrows(
                DivisionByZeroException.class,
                () -> calc.calculate(0.0, '/')
        );
        assertThat(ex, is(instanceOf(DivisionByZeroException.class)));
    }

    @Test
    public void testCalculateNullOperand()
            throws DivisionByZeroException, NotSupportedOperationException {
        calc.calculate(null, '+');
        assertThat(calc.getCurrentValue(), is(equalTo(0.0)));
    }

    /* Parametrized testing */
    private static Stream<Arguments> calcArgsStream() {
        return Stream.of(
                Arguments.of(5.0, 1.0, '+', 6.0),
                Arguments.of(-2.5, 2.0, '-', -4.5),
                Arguments.of(-1.0, -1.0, '-', 0.0),
                Arguments.of(9.99, 0.01, '+', 10.0),
                Arguments.of(10.0, 2.55, '*', 25.5),
                Arguments.of(5.4, 2.0, '/', 2.7),
                Arguments.of(1.0, null, '+', 1.0),
                Arguments.of(1.0, null, '-', 1.0),
                Arguments.of(null, null, '-', 0.0),
                Arguments.of(4.94065645841246544e-324, 0.0, '*', 0.0),
                Arguments.of(4.94065645841246544e-324, 1.0, '/', 4.94065645841246544e-324)
        );
    }

    @ParameterizedTest
    @MethodSource("calcArgsStream")
    public void testCalculateParameterized(
            Double startingValue, Double value, char operator, Double expectedResult
    ) throws NotSupportedOperationException, DivisionByZeroException {
        calc.setCurrentValue(startingValue);
        calc.calculate(value, operator);
        assertThat(calc.getCurrentValue(), is(equalTo(expectedResult)));
    }

    private static Stream<Arguments> constructorArgsStream() {
        return Stream.of(
          Arguments.of(1.0, 1.0),
          Arguments.of(-1.0, -1.0),
          Arguments.of(null, 0.0),
          Arguments.of(0.00000001, 0.00000001)
        );
    }

    @Test
    public void testCalculator() {
        this.calc = new Calculator();
        assertThat(calc.getCurrentValue(), is(equalTo(0.0)));
    }

    @ParameterizedTest
    @MethodSource("constructorArgsStream")
    public void testCalculatorParameterized(Double startingValue, Double expectedValue) {
        this.calc = new Calculator(startingValue);
        assertThat(calc.getCurrentValue(), is(equalTo(expectedValue)));
    }
}
