package migzmigzmigz.com.calculator;

import android.content.Context;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Calculator {

    private static final String TAG = Calculator.class.getSimpleName();

    private Memory mMemory;
    private TextView mOutput;

    private List<String> mExpression;

    private boolean shouldClear = false;

    public Calculator(Context context, TextView output) {
        mExpression = new ArrayList<>();
        mExpression.add("0");

        mOutput = output;
        mOutput.setText("0");

        mMemory = Memory.getInstance(context);
    }

    public void onNumberClick(String operand) {
        if (!isOperand(operand)) {
            throw new IllegalArgumentException(operand + " is not a valid operand.");
        }

        if (shouldClear) {
            shouldClear = false;
            mExpression.clear();
            mExpression.add(operand);
        } else {
            int indexOfLast = mExpression.size() - 1;
            String last = mExpression.get(indexOfLast);
            BigDecimal op;

            if (isOperand(last)) {
                mExpression.remove(indexOfLast);
                op = new BigDecimal(last + operand);
            } else {
                op = new BigDecimal(operand);
            }

            mExpression.add(op.toPlainString());
        }

        mOutput.setText(toString());
    }

    /**
     * It is assumed that the given operator parameter is a valid operator
     *
     * @param operator
     */
    public void onOperatorClick(String operator) {
        shouldClear = false; // don't clear mExpression after this operation

        int indexOfLast = mExpression.size() - 1;
        String last = mExpression.get(indexOfLast);

        if (isOperator(last)) {
            mExpression.remove(indexOfLast);
        }

        mExpression.add(operator);
        mOutput.setText(toString());
    }

    public void onToggleNegate() {
        int indexOfLast = mExpression.size() - 1;
        String last = mExpression.get(indexOfLast);

        if (isOperand(last)) {
            mExpression.remove(indexOfLast);

            if (last.startsWith("-")) {
                mExpression.add(last.substring(1));
            } else {
                mExpression.add("-" + last);
            }

            mOutput.setText(toString());
        }
    }

    public void onDotClick() {
        int indexOfLast = mExpression.size() - 1;
        String last = mExpression.get(indexOfLast);

        if (isOperator(last)) {
            mExpression.add("0.");

            mOutput.setText(toString());
        } else if (!last.contains(".")) {
            mExpression.remove(indexOfLast);
            mExpression.add(last + ".");

            mOutput.setText(toString());
        }
    }

    public void onEvaluate() {
        int size = mExpression.size();
        int indexOfLast = size - 1;
        String last = mExpression.get(indexOfLast);

        if (size >= 3 && isOperand(last)) {
            try {
                BigDecimal result = Parser.with(mExpression).evaluate();
                long resultAsLong = result.longValue();

                mExpression.clear();
                if (resultAsLong == result.doubleValue()) {
                    mExpression.add(String.valueOf(resultAsLong));
                } else {
                    mExpression.add(result.toPlainString());
                }
            } catch (ArithmeticException e) {
                mExpression.clear();
                mExpression.add("Error");
            }

            shouldClear = true;
            mOutput.setText(toString());
        }
    }

    public String toString() {
        // for single terms that is negative
        if (mExpression.size() == 1 && mExpression.get(0).startsWith("-")) {
            return mExpression.get(0);
        }

        StringBuilder output = new StringBuilder();
        boolean first = true;
        int size = mExpression.size();

        for (int i = 0; i < size; i++) {
            String element = mExpression.get(i);

            if (first) {
                first = false;
            } else {
                output.append("");
            }


            if (isOperand(element) && element.startsWith("-")) {
                output.append("(")
                        .append(element)
                        .append(")");
            } else {
                output.append(element);
            }
        }

        return output.toString();
    }

    public static boolean isOperand(String subject) {
        return subject != null && subject.matches("\\-?\\d+\\.?\\d*");
    }

    public static boolean isOperator(String subject) {
        return subject != null && subject.matches("[\\+\\-/\\*]");
    }

    public void onBackspace() {
        int indexOfLast = mExpression.size() - 1;
        String last = mExpression.get(indexOfLast);

        mExpression.remove(indexOfLast);

        if (!last.equals("Error") && last.length() > 1) {
            mExpression.add(last.substring(0, last.length() - 1));
        }

        if (mExpression.isEmpty()) {
            mExpression.add("0");
        }

        mOutput.setText(toString());
    }

    public void onClearAll() {
        shouldClear = false;

        mExpression.clear();
        mExpression.add("0");

        mOutput.setText("0");
    }

    public void onMemoryStore() {
        String last = mExpression.get(mExpression.size() - 1);

        if (isOperand(last) && !"0".equals(last) && !"Error".equals(last)) {
            mMemory.store(last);
        }
    }

    public void onMemoryRecall() {
        String next = mMemory.recall();

        if (next != null) {
            int indexOfLast = mExpression.size() - 1;
            String last = mExpression.get(indexOfLast);

            if (mExpression.size() == 1 && last.equals("0") ||
                    isOperand(last)) {
                mExpression.remove(indexOfLast);
            }

            mExpression.add(next);
            mOutput.setText(toString());
        }
    }

    public void onMemoryClear() {
        mMemory.clear();
    }

}
