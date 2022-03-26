package com.example.calculator_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout mainMenuLayout = findViewById(R.id.MainMenuLayout);
        ArrayList<Button> mainMenuButtons = findButtonsInLayout(mainMenuLayout);

        for(Button button : mainMenuButtons){
            button.setOnClickListener(this);
        }
    }

    private ArrayList<Button> findButtonsInLayout(ViewGroup layout){
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

    private void openNewActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
    private void exitCalculatorApp(){
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.simpleCalculatorButton:
                openNewActivity(SimpleCalculator.class);
                break;
            case R.id.advancedCalculatorButton:
                openNewActivity(AdvancedCalculator.class);
                break;
            case R.id.aboutMeButton:
                openNewActivity(AboutMe.class);
                break;
            case R.id.exitButton:
                exitCalculatorApp();
                break;
            default:
                break;
        }
    }
}