package migzmigzmigz.com.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public final class Parser {

    private List<String> infix;

    private Parser(List<String> infix) {
        this.infix = infix;
    }

    public BigDecimal evaluate() throws ArithmeticException {
        List<String> postfix = infixToPostfix(infix);
        return evaluatePostfix(postfix);
    }

    public static Parser with(List<String> infix) {
        return new Parser(infix);
    }

    public static BigDecimal evaluatePostfix(List<String> postfixExpression) throws ArithmeticException {
        if (postfixExpression.isEmpty()) {
            throw new IllegalStateException("There's nothing to onEvaluate. Postfix is empty.");
        }

        int size = postfixExpression.size();
        Stack<BigDecimal> operands = new Stack<>();

        for (int i = 0; i < size; i++) {
            String token = postfixExpression.get(i);

            if (Calculator.isOperand(token)) {
                operands.push(new BigDecimal(token));
            } else {
                BigDecimal a = operands.pop();
                BigDecimal b = operands.pop();
                operands.push(evaluate(b, token, a));
            }
        }

        return operands.pop();
    }

    public static List<String> infixToPostfix(List<String> infixExpression) {
        Stack<String> operators = new Stack<>();
        List<String> postfix = new ArrayList<>();

        int size = infixExpression.size();

        for (int i = 0; i < size; i++) {
            String token = infixExpression.get(i);

            if (Calculator.isOperand(token)) {
                postfix.add(token);
            } else {
                while (!operators.isEmpty() && comparePrecedence(operators.peek(), token) != -1) {
                    postfix.add(operators.pop());
                }

                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            postfix.add(operators.pop());
        }

        return postfix;
    }

    public static int comparePrecedence(String op1, String op2) {
        if (("*".equals(op1) || "/".equals(op1)) &&
                ("+".equals(op2) || "-".equals(op2))) {
            return 1;
        } else if (("+".equals(op1) || "-".equals(op1)) &&
                ("*".equals(op2) || "/".equals(op2))) {
            return -1;
        } else {
            return 0;
        }
    }

    public static BigDecimal evaluate(BigDecimal a, String operator, BigDecimal b) throws ArithmeticException{
        if (operator == null) {
            throw new NullPointerException("Operator must not be null");
        }

        if ("+".equals(operator))
            return a.add(b);

        if ("-".equals(operator))
            return a.subtract(b);

        if ("*".equals(operator))
            return a.multiply(b);

        if ("/".equals(operator))
            return a.divide(b, 4, BigDecimal.ROUND_CEILING);

        return BigDecimal.ZERO;
    }

}
