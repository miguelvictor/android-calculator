package migzmigzmigz.com.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView output = (TextView) findViewById(R.id.output);
        calculator = new Calculator(this, output);
    }

    public void onNumberClick (View view) {
        Button button = (Button) view;
        String number = String.valueOf(button.getText());
        calculator.onNumberClick(number);
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        String operator = String.valueOf(button.getText());
        calculator.onOperatorClick(operator);
    }

    public void onToggleNegate (View view) {
        calculator.onToggleNegate();
    }

    public void onEvaluate(View view) {
        calculator.onEvaluate();
    }

    public void onDotClick(View view) {
        calculator.onDotClick();
    }

    public void onBackspace(View view) {
        calculator.onBackspace();
    }

    public void onClearAll(View view) {
        calculator.onClearAll();
    }

    public void onMemoryRecall(View view) {
        calculator.onMemoryRecall();
    }

    public void onMemoryClear(View view) {
        calculator.onMemoryClear();
    }

    public void onMemoryStore(View view) {
        calculator.onMemoryStore();
    }
}
