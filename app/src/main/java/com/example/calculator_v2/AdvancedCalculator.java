package com.example.calculator_v2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdvancedCalculator extends AppCompatActivity {

    protected EditText calculatorView;

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        getSupportActionBar().hide();
        setContentView(R.layout.advanced_calculator);

        calculatorView = findViewById(R.id.display);
        calculatorView.setShowSoftInputOnFocus(false);

        AdvancedCalculatorHandler advancedCalculatorHandler = new AdvancedCalculatorHandler(this);

        LinearLayout advCalculatorButtonsLayout = findViewById(R.id.calculatorButtonsLayout);
        int orientation = getResources().getConfiguration().orientation;
        System.out.println(orientation);
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            ArrayList<Button> advCalculatorButtonsPort = getAllButtonsPortrait(advCalculatorButtonsLayout);

            for (Button btn : advCalculatorButtonsPort) {
                btn.setOnClickListener(advancedCalculatorHandler);
            }
        }
        else{
            ArrayList<Button> advCalculatorButtonsLand = getAllButtonsLandscape(advCalculatorButtonsLayout);

            for (Button btn : advCalculatorButtonsLand) {
                btn.setOnClickListener(advancedCalculatorHandler);
            }
        }
    }

    private ArrayList<Button> getAllButtonsPortrait(ViewGroup layout){
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

    private ArrayList<Button> getAllButtonsLandscape(ViewGroup layout){
        ArrayList<Button> buttons = new ArrayList<>();
        for(int i = 0; i < layout.getChildCount(); ++i){
            LinearLayout childLayout = (LinearLayout) layout.getChildAt(i);
            for(int j = 0; j < childLayout.getChildCount(); ++j) {
                LinearLayout innerChildLayout = (LinearLayout) childLayout.getChildAt(j);
                for(int k = 0; k < innerChildLayout.getChildCount(); ++k){
                    View v = innerChildLayout.getChildAt(k);
                    if (v instanceof Button) {
                        buttons.add((Button) v);
                    }
                }
            }
        }
        return buttons;
    }
}
