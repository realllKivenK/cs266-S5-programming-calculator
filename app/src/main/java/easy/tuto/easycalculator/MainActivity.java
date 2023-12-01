package easy.tuto.easycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot, buttonPower , button_ASCII;

    boolean powerClicked = false;
    String baseNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(R.id.button_c);
        assignId(R.id.button_open_bracket);
        assignId(R.id.button_close_bracket);
        assignId(R.id.button_divide);
        assignId(R.id.button_multiply);
        assignId(R.id.button_plus);
        assignId(R.id.button_minus);
        assignId(R.id.button_equals);
        assignId(R.id.button_0);
        assignId(R.id.button_1);
        assignId(R.id.button_2);
        assignId(R.id.button_3);
        assignId(R.id.button_4);
        assignId(R.id.button_5);
        assignId(R.id.button_6);
        assignId(R.id.button_7);
        assignId(R.id.button_8);
        assignId(R.id.button_9);
        assignId(R.id.button_ac);
        assignId(R.id.button_dot);


        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            assignId(R.id.button_power);
            assignId(R.id.button_binary);
            assignId(R.id.button_Octal);
            assignId(R.id.button_Hex);
            assignId(R.id.button_ASCII);
        }


    }

    void assignId(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            resetPowerState();
            return;
        }

        if (buttonText.equals("=")) {
            if (powerClicked && !baseNumber.isEmpty()) {
                double baseValue = Double.parseDouble(baseNumber);
                double exponentValue = Double.parseDouble(dataToCalculate);
                String powResult = MathFunction.power(baseValue, exponentValue);

                String resultString = String.valueOf(powResult);

                // Set the result in solutionTv
                solutionTv.setText(baseNumber + "^" + dataToCalculate);

                // Set the result in resultTv
                resultTv.setText(resultString);

                resetPowerState();
                return;
            }

            solutionTv.setText(dataToCalculate);
            String finalResult = getResult(dataToCalculate);

            if (!finalResult.equals("Err")) {
                resultTv.setText(finalResult);
            }
            return;
        }

        if (buttonText.equals("C")) {
            // Clear one character from the displayed expression
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }

        } else if (buttonText.equals("POWER")) {
            powerClicked = true;
            baseNumber = dataToCalculate;
            dataToCalculate = ""; // Clear the display after pressing the power button
        }

        else  if (buttonText.equals("Binary")) {
            try {
                String binaryResult = MathFunction.decimalToBinary(getResult(dataToCalculate));
                resultTv.setText(binaryResult);
                resetPowerState();
            } catch (Exception e) {
                resultTv.setText("Err");
            }
            return;
        } else if (buttonText.equals("Octal")) {
            try {
                String octalResult = MathFunction.decimalToOctal(getResult(dataToCalculate));
                resultTv.setText(octalResult);
                resetPowerState();
            } catch (Exception e) {
                resultTv.setText("Err");
            }
            return;
        } else if (buttonText.equals("Hexadecimal")) {
            try {
                String hexResult = MathFunction.decimalToHexadecimal(getResult(dataToCalculate));
                resultTv.setText(hexResult);
                resetPowerState();
            } catch (Exception e) {
                resultTv.setText("Err");
            }
            return;
        } else if (buttonText.equals("ASCIIT")){
            try {
                String a = MathFunction.convertToASCII(getResult(dataToCalculate));
                resultTv.setText(a);
            } catch (Exception e) {
                resultTv.setText("Err");
            }
        }


        else {
            dataToCalculate = dataToCalculate + buttonText;
        }


        solutionTv.setText(dataToCalculate);
        resultTv.setText(dataToCalculate);


    }

    String getResult(String data) {
        try {

                // Evaluate other expressions using JavaScript
                Context context = Context.enter();
                context.setOptimizationLevel(-1);
                Scriptable scriptable = context.initStandardObjects();
                String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
                if (finalResult.endsWith(".0")) {
                    finalResult = finalResult.replace(".0", "");
                }

                // Set the result in solutionTv
                solutionTv.setText(data);

                // Set the result in resultTv
                solutionTv.setText(data);
                resultTv.setText(finalResult);


                return finalResult;

        } catch (Exception e) {
            return "Err";
        }
    }

    private void resetPowerState() {
        powerClicked = false;
        baseNumber = "";
    }
}