package org.etfbl.tks;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

import org.etfbl.tks.exceptions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CalculatorAdvancedTest {
    private CalculatorAdvanced calc = new CalculatorAdvanced();

    @BeforeEach
    void setUp() {
        calc.setCurrentValue(0.0);
    }

    /* Atomic unit testing */
    @Test
    public void testFactorial()
            throws NumberNotInAreaException, NotSupportedOperationException {
        calc.setCurrentValue(5.0);
        calc.calculateAdvanced('!');
        assertThat(calc.getCurrentValue(), is(equalTo(120.0)));
    }

    @Test
    public void testExponentiation()
            throws NumberNotInAreaException, NotSupportedOperationException {
        calc.setCurrentValue(5.0);
        calc.calculateAdvanced('3');
        assertThat(calc.getCurrentValue(), is(equalTo(125.0)));
    }

    @Test
    public void testArmstrongCharacteristic()
            throws NumberNotInAreaException, NotSupportedOperationException {
        calc.setCurrentValue(153.0);
        assertThat(calc.hasCharacteristic('A'), is(equalTo(true)));
    }

    @Test
    public void testPerfectNumberCharacteristic()
            throws NumberNotInAreaException, NotSupportedOperationException {
        calc.setCurrentValue(28.0);
        assertThat(calc.hasCharacteristic('P'), is(equalTo(true)));
    }

    @Test
    public void testExponent() {
        calc.setCurrentValue(5.0);
        calc.setCurrentValue((double)calc.exponent(calc.getCurrentValue().intValue(), 2));
        assertThat(calc.getCurrentValue(), is(equalTo(25.0)));
    }

    /* Exception-handling testing */
    @Test
    public void testNumberArea() {
        calc.setCurrentValue(0.999999999);

        Exception exA = assertThrows(
                NumberNotInAreaException.class,
                () -> calc.hasCharacteristic('A')
        );

        Exception exP = assertThrows(
                NumberNotInAreaException.class,
                () -> calc.hasCharacteristic('P')
        );

        assertThat(exA, is(instanceOf(NumberNotInAreaException.class)));
        assertThat(exP, is(instanceOf(NumberNotInAreaException.class)));
    }

    @Test
    public void testUnsupportedOpCharacteristic() {
        calc.setCurrentValue(420.0);

        Exception ex = assertThrows(
                NotSupportedOperationException.class,
                () -> calc.hasCharacteristic('X')
        );

        assertThat(ex, is(instanceOf(NotSupportedOperationException.class)));
    }

    @Test
    public void testUnsupportedOpAdvanced() {
        calc.setCurrentValue(0.68);
        Exception ex1 = assertThrows(
                NotSupportedOperationException.class,
                () -> calc.calculateAdvanced('^')
        );

        Exception ex2 = assertThrows(
                NotSupportedOperationException.class,
                () -> calc.calculateAdvanced((char)0x40)
        );
        Exception ex3 = assertThrows(
                NotSupportedOperationException.class,
                () -> calc.calculateAdvanced((char)0x2f)
        );

        assertThat(ex1, is(instanceOf(NotSupportedOperationException.class)));
        assertThat(ex2, is(instanceOf(NotSupportedOperationException.class)));
        assertThat(ex3, is(instanceOf(NotSupportedOperationException.class)));
    }

    @Test
    public void testFactorialNumberAreaEx() {
        calc.setCurrentValue(-0.01);
        Exception ex1 = assertThrows(
                NumberNotInAreaException.class,
                () -> calc.calculateAdvanced('!')
        );

        calc.setCurrentValue(10.01);
        Exception ex2 = assertThrows(
                NumberNotInAreaException.class,
                () -> calc.calculateAdvanced('!')
        );

        assertThat(ex1, is(instanceOf(NumberNotInAreaException.class)));
        assertThat(ex2, is(instanceOf(NumberNotInAreaException.class)));
    }

    @Test
    public void testCharacteristicNumberAreaEx() {
        calc.setCurrentValue(0.999);
        Exception ex = assertThrows(
                NumberNotInAreaException.class,
                () -> calc.hasCharacteristic('P')
        );

        assertThat(ex, is(instanceOf(NumberNotInAreaException.class)));
    }

    /* Parameterized tests */

    private static Stream<Arguments> calcAdvancedArgs() {
        return Stream.of(
                Arguments.of(3.0, '!', 6.0),
                Arguments.of(3.01, '!', 6.0),
                Arguments.of(2.999999, '!', 2.0),
                Arguments.of(0.0, '!', 1.0),
                Arguments.of(10.0, '!', 3628800.0),
                Arguments.of(3.0, '2', 9.0),
                Arguments.of(3.01, '2', 9.0),
                Arguments.of(0.0, '0', 1.0),
                Arguments.of(-0.0, '0', 1.0),
                Arguments.of(1.0, '0', 1.0),
                Arguments.of(0.99, '0', 1.0),
                Arguments.of(5.55, '1', 5.00),
                Arguments.of(1.0, '9', 1.0),
                Arguments.of(Double.NaN, '0', 1.0),
                Arguments.of(Double.POSITIVE_INFINITY, '0', 1.0),
                Arguments.of(Double.NEGATIVE_INFINITY, '0', 1.0),
                Arguments.of(null, '!', 1.0),
                Arguments.of(null, '0', 1.0)
        );
    }

    @ParameterizedTest
    @MethodSource("calcAdvancedArgs")
    public void testCalcAdvancedParams(Double initialValue, char action, Double expectedResult)
            throws NotSupportedOperationException, NumberNotInAreaException {
        calc.setCurrentValue(initialValue);
        calc.calculateAdvanced(action);
        assertThat(calc.getCurrentValue(), is(equalTo(expectedResult)));
    }

    private static Stream<Arguments> characteristicArgsStream() {
        return Stream.of(
                Arguments.of(1.0, 'A', true),
                Arguments.of(10.0, 'A', false),
                Arguments.of(153.0, 'A', true),

                Arguments.of(1.0, 'P', true),
                Arguments.of(2.0, 'P', false),
                Arguments.of(6.0, 'P', true)
        );
    }

    @ParameterizedTest
    @MethodSource("characteristicArgsStream")
    public void testCharacteristicParameterized(Double initialValue, char action, Boolean expectedResult)
            throws NotSupportedOperationException, NumberNotInAreaException {
        calc.setCurrentValue(initialValue);
        assertThat(calc.hasCharacteristic(action), is(equalTo(expectedResult)));
    }
}
