package com.example.calculator_v2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SimpleCalculator extends AppCompatActivity {

    protected EditText calculatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.simple_calculator);

        calculatorView = findViewById(R.id.display);
        calculatorView.setShowSoftInputOnFocus(false);

        SimpleCalculatorHandler simpleCalculatorHandler = new SimpleCalculatorHandler(this);

        LinearLayout calculatorButtonsLayout = findViewById(R.id.calulatorButtonsLayout);
        ArrayList<Button> listOfAllButtons = getAllButtons(calculatorButtonsLayout);


        for(Button btn : listOfAllButtons){
            btn.setOnClickListener(simpleCalculatorHandler);
        }
    }

    private ArrayList<Button> getAllButtons(ViewGroup layout){
        ArrayList<Button> buttons = new ArrayList<>();
        for(int i = 0; i < layout.getChildCount(); ++i){
            LinearLayout childLayout = (LinearLayout) layout.getChildAt(i);
            for(int j = 0; j < childLayout.getChildCount(); ++j) {
                View v = childLayout.getChildAt(j);
                if (v instanceof Button) {
                    buttons.add((Button) v);
                }
            }
        }
        return buttons;
    }
}
