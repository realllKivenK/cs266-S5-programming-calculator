package easy.tuto.easycalculator;


import static easy.tuto.easycalculator.Constants.RESULT;
import static easy.tuto.easycalculator.Constants.SOLUTION;
import static easy.tuto.easycalculator.Constants.TABLE_NAME;
import static easy.tuto.easycalculator.Constants.TIME;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EventsData events;
    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot, buttonPower;

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
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String formattedDate = sdf.format(calendar.getTime());

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
                double powResult = Math.pow(baseValue, exponentValue);

                String resultString = String.valueOf(powResult);

                // Set the result in solutionTv
                solutionTv.setText(baseNumber + "^" + dataToCalculate);

                // Set the result in resultTv
                resultTv.setText(resultString);

                addEvent(baseNumber+"^"+dataToCalculate,resultString,formattedDate);
                resetPowerState();

                return;
            }

            solutionTv.setText(dataToCalculate);
            String finalResult = getResult(dataToCalculate);

            if (!finalResult.equals("Err")) {
                resultTv.setText(finalResult);
            }
            addEvent(dataToCalculate,finalResult,formattedDate);
            return;
        }

        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else if (buttonText.equals("POWER")) {
            powerClicked = true;
            baseNumber = dataToCalculate;
            dataToCalculate = ""; // Clear the display after pressing the power button
        }else if (buttonText.equals("Binary")) {
            String binaryResult = MathFunction.decimalToBinary(dataToCalculate);
            resultTv.setText(binaryResult);
            addEvent(dataToCalculate,binaryResult,formattedDate);
            resetPowerState();  // Assuming binary conversion does not involve exponentiation
            return;
        }else if (buttonText.equals("Octal")) {
            String octalResult = MathFunction.decimalToOctal(dataToCalculate);
            resultTv.setText(octalResult);
            addEvent(dataToCalculate,octalResult,formattedDate);
            resetPowerState();  // Assuming binary conversion does not involve exponentiation
            return;
        }else if (buttonText.equals("Hexadecimal")) {
            String hexResult = MathFunction.decimalToHexadecimal(dataToCalculate);
            resultTv.setText(hexResult);
            addEvent(dataToCalculate,hexResult,formattedDate);
            resetPowerState();  // Assuming binary conversion does not involve exponentiation
            return;
        }
        else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);
        resultTv.setText(dataToCalculate);




    }



    String getResult(String data) {
        try {
            if (data.contains("^")) {
                // Handle exponentiation directly in Java
                String[] parts = data.split("\\^");
                double baseValue = Double.parseDouble(parts[0]);
                double exponentValue = Double.parseDouble(parts[1]);
                double powResult = Double.parseDouble(MathFunction.power(baseValue, exponentValue));

                String resultString = String.valueOf(powResult);

                // Set the result in solutionTv
                solutionTv.setText(data);

                // Set the result in resultTv
                resultTv.setText(resultString);

                return resultString;
            } else {
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
            }
        } catch (Exception e) {
            return "Err";
        }
    }

    private void resetPowerState() {
        powerClicked = false;
        baseNumber = "";
    }
    private void addEvent(String solution,String result ,String formattedDate) {

        events = new EventsData(MainActivity.this);
        try {
            SQLiteDatabase db = events.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TIME, formattedDate);  // เพิ่มค่าเวลาปัจจุบัน
            values.put(SOLUTION, solution);
            values.put(RESULT, result);
            db.insert(TABLE_NAME, null, values);
        } finally {
            events.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.view_record) {
            startActivity(new Intent(this, ListActivity.class));
            return false;
        }
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected
}
