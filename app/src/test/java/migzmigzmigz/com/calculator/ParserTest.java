package migzmigzmigz.com.calculator;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testFluent () {
        List<String> infix = Arrays.asList("12", "+", "3", "-", "-5");
    }

    @Test
    public void testInfixToPostfix () {
        List<String> infix = Arrays.asList("12", "+", "3");
        List<String> postfix = Parser.infixToPostfix(infix);
        assertTrue(Arrays.asList("12", "3", "+").equals(postfix));
    }

    @Test
    public void testComparePrecedence () {
        assertEquals(0, Parser.comparePrecedence("*", "*"));
        assertEquals(0, Parser.comparePrecedence("*", "/"));
        assertEquals(1, Parser.comparePrecedence("*", "+"));
        assertEquals(1, Parser.comparePrecedence("*", "-"));
        assertEquals(0, Parser.comparePrecedence("/", "*"));
        assertEquals(0, Parser.comparePrecedence("/", "/"));
        assertEquals(1, Parser.comparePrecedence("/", "+"));
        assertEquals(1, Parser.comparePrecedence("/", "-"));
        assertEquals(-1, Parser.comparePrecedence("+", "*"));
        assertEquals(-1, Parser.comparePrecedence("+", "/"));
        assertEquals(0, Parser.comparePrecedence("+", "+"));
        assertEquals(0, Parser.comparePrecedence("+", "-"));
        assertEquals(-1, Parser.comparePrecedence("-", "*"));
        assertEquals(-1, Parser.comparePrecedence("-", "/"));
        assertEquals(0, Parser.comparePrecedence("-", "+"));
        assertEquals(0, Parser.comparePrecedence("-", "-"));
    }

    @Test
    public void testEvaluationAdditionWithCorrectInputs() {
        assertEquals(2.0, Parser.evaluate(1, "+", 1), 0);
        assertEquals(-2.0, Parser.evaluate(-1.0, "+", -1.0), 0);
        assertEquals(77, Parser.evaluate(70, "+", 7), 0);
    }

    @Test
    public void testEvaluationSubtractionWithCorrectInputs() {
        assertEquals(23, Parser.evaluate(50, "-", 27), 0);
        assertEquals(-23, Parser.evaluate(27, "-", 50), 0);
        assertEquals(30, Parser.evaluate(30, "-", 0), 0);
    }

    @Test
    public void testEvaluationMultiplicationWithCorrectInputs() {
        assertEquals(6, Parser.evaluate(2, "*", 3), 0);
        assertEquals(-6, Parser.evaluate(-2, "*", 3), 0);
        assertEquals(1350, Parser.evaluate(50, "*", 27), 0);
    }

    @Test
    public void testEvaluationDivisionWithCorrectInputs() {
        assertEquals(3, Parser.evaluate(9, "/", 3), 0);
        assertEquals(8, Parser.evaluate(80, "/", 10), 0);
        assertEquals(-8, Parser.evaluate(80, "/", -10), 0);
        assertEquals(Double.NaN, Parser.evaluate(0, "/", 0), 0);
        assertEquals(Double.POSITIVE_INFINITY, Parser.evaluate(1, "/", 0), 0);
        assertEquals(Double.NEGATIVE_INFINITY, Parser.evaluate(-1, "/", 0), 0);
    }

}
