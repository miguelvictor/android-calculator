package migzmigzmigz.com.calculator;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void test1 () {
        List<String> infix = Arrays.asList("12", "*", "3", "+", "5");
        System.out.println(Parser.with(infix).evaluate());
    }

}
