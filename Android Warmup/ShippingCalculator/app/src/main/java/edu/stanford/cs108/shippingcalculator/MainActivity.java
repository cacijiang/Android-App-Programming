package edu.stanford.cs108.shippingcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    private EditText editWeight;
    private TextView calculatedCost;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button buttonCalculate;
    private CheckBox checkInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editWeight = findViewById(R.id.editWeight);
        radioGroup = findViewById(R.id.radioGroup);
        calculatedCost = findViewById(R.id.calculatedCost);
        checkInsurance = findViewById(R.id.checkInsurance);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    double weight = Double.parseDouble(editWeight.getText().toString());
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    String option = radioButton.getText().toString();
                    double cost = 0;
                    if(option.equals("Next Day")) {
                        cost = 10*weight;
                    } else if(option.equals("Second Day")) {
                        cost = 5*weight;
                    } else {
                        cost = 3*weight;
                    }
                    if(checkInsurance.isChecked()) {
                        cost *= 1.2;
                    }
                    StringBuilder sb = new StringBuilder("Cost: $");
                    sb.append((int) Math.ceil(cost));
                    calculatedCost.setText(sb.toString());
            }
        });
    }
}
