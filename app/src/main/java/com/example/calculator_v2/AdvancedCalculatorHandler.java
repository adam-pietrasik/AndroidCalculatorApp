package com.example.calculator_v2;

import net.objecthunter.exp4j.*;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdvancedCalculatorHandler implements View.OnClickListener {

    AdvancedCalculator ac;
    protected static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds
    protected long lastClickTime = 0;
    AdvancedCalculatorHandler(AdvancedCalculator ac) {
        this.ac = ac;
    }

    @Override
    public void onClick(View view){
        Button button = ac.findViewById(view.getId());
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
            case "()":
                checkForBrackets();
                break;
            default:
                setExpression(button.getText().toString());
                break;
        }
    }

    private void checkForBrackets(){

        int leftBracketCount = 0;
        int rightBracketCount = 0;
        int cursorPosition = ac.calculatorView.getSelectionStart();
        for(int i = 0; i < cursorPosition; ++i){
            if (ac.calculatorView.getText().charAt(i) == '(')
                leftBracketCount++;
            if (ac.calculatorView.getText().charAt(i) == ')')
                rightBracketCount++;
        }

        if(leftBracketCount > rightBracketCount)
            setExpression(")");
        if(leftBracketCount < rightBracketCount || leftBracketCount == rightBracketCount)
            setExpression("(");
    }

    protected void onSingleClick(View v){
        String currentExpression = ac.calculatorView.getText().toString();
        if(currentExpression.isEmpty())
            return;
        int cursorPosition = ac.calculatorView.getSelectionStart();
        if(cursorPosition > 0) {
            currentExpression = currentExpression.substring(0, cursorPosition - 1) +
                    currentExpression.substring(cursorPosition);
            ac.calculatorView.setText(currentExpression);
            ac.calculatorView.setSelection(cursorPosition - 1);
        }
    }

    protected void onDoubleClick(View v){
        setExpression("");
    }

    public void calculateExpression(){

        Double result = null;
        try {
            Expression expression = new ExpressionBuilder(ac.calculatorView.getText().toString())
                .build();
            result = expression.evaluate();
        }
        catch(ArithmeticException | IllegalArgumentException e){
            Toast.makeText(ac, "Invalid data input", Toast.LENGTH_SHORT).show();
        }
        if(result != null) {
            ac.calculatorView.setText(String.valueOf(result));
            int cursorPosition = ac.calculatorView.getSelectionStart();
            int valueLength = ac.calculatorView.getText().toString().length();
            ac.calculatorView.setSelection(cursorPosition + valueLength);
        }
    }

    public void setExpression(String expression){
        String currentExpression = ac.calculatorView.getText().toString();
        int cursorPosition = ac.calculatorView.getSelectionStart();
        String leftExpression = currentExpression.substring(0, cursorPosition);

        if(expression.equals("")){
            ac.calculatorView.setText("");
            return;
        }

        boolean isNegative = false;
        if(!currentExpression.isEmpty() && cursorPosition > 0) {
            if (expression.equals("-") && leftExpression.charAt(cursorPosition - 1) == '-') {
                expression = "";
                isNegative = true;
                leftExpression = leftExpression.substring(0, leftExpression.length() - 1);
                ac.calculatorView.setSelection(cursorPosition - 1);
            }
        }
        String rightExpression = currentExpression.substring(cursorPosition);

        ac.calculatorView.setText(String.format("%s%s%s", leftExpression, expression, rightExpression));
        if(!isNegative)
            ac.calculatorView.setSelection(cursorPosition + expression.length());
        else
            ac.calculatorView.setSelection(cursorPosition - 1);
    }
}
