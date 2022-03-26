package com.example.calculator_v2;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class SimpleCalculatorHandler implements View.OnClickListener{

    private final SimpleCalculator sc;
    protected static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    protected long lastClickTime = 0;
    SimpleCalculatorHandler(SimpleCalculator simpleCalculator){
        this.sc = simpleCalculator;
    }

    @Override
    public void onClick(View view) {
        Button button = sc.findViewById(view.getId());
        switch(button.getText().toString()){
            case "C/CE":
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
                    onDoubleClick(view);
                } else {
                    onSingleClick(view);
                }
                lastClickTime = clickTime;
                break;
            case "=":
                calculateExpression();
                break;
            case "+/-":
                setExpression("-");
                break;
            default:
                setExpression(button.getText().toString());
                break;
        }
//        if(button.getText().toString()
//            .equals(sc.getResources().getString(R.string.clear)))
//        {
//            long clickTime = System.currentTimeMillis();
//            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
//                onDoubleClick(view);
//            } else {
//                onSingleClick(view);
//            }
//            lastClickTime = clickTime;
//        }
//        else if(button.getText().toString()
//            .equals(sc.getResources().getString(R.string.calc)))
//        {
//            calculateExpression();
//        }
//        else {
//            setExpression(button.getText().toString());
//        }
    }

    public void calculateExpression(){

        Expression expression = new Expression(sc.calculatorView.getText().toString());
        System.out.println(expression.calculate());
        String result = String.valueOf(expression.calculate());
        if(!result.equals("NaN") && !result.equals("Infinity")) {
            sc.calculatorView.setText(result);
            int cursorPosition = sc.calculatorView.getSelectionStart();
            int valueLength = sc.calculatorView.getText().toString().length();
            sc.calculatorView.setSelection(cursorPosition + valueLength);
        }
        else{
            Toast.makeText(sc, "Invalid data input", Toast.LENGTH_SHORT).show();
        }
    }


    protected void onSingleClick(View v){
        String currentExpression = sc.calculatorView.getText().toString();
        if(currentExpression.isEmpty())
            return;
        int cursorPosition = sc.calculatorView.getSelectionStart();
        if(cursorPosition > 0) {
            currentExpression = currentExpression.substring(0, cursorPosition - 1) +
                    currentExpression.substring(cursorPosition);
            sc.calculatorView.setText(currentExpression);
            sc.calculatorView.setSelection(cursorPosition - 1);
        }
    }

    protected void onDoubleClick(View v){
        setExpression("");
    }

    public void setExpression(String expression){
        String currentExpression = sc.calculatorView.getText().toString();
        int cursorPosition = sc.calculatorView.getSelectionStart();
        String leftExpression = currentExpression.substring(0, cursorPosition);

        if(expression.equals("")){
            sc.calculatorView.setText("");
            return;
        }

        boolean isNegative = false;
        if(!currentExpression.isEmpty() && cursorPosition > 0) {
            if (expression.equals("-") && leftExpression.charAt(cursorPosition - 1) == '-') {
                expression = "";
                isNegative = true;
                leftExpression = leftExpression.substring(0, leftExpression.length() - 1);
                sc.calculatorView.setSelection(cursorPosition - 1);
            }
        }
        String rightExpression = currentExpression.substring(cursorPosition);

        sc.calculatorView.setText(String.format("%s%s%s", leftExpression, expression, rightExpression));
        if(!isNegative)
            sc.calculatorView.setSelection(cursorPosition + expression.length());
        else
            sc.calculatorView.setSelection(cursorPosition - 1);
    }
}
