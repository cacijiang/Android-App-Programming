package edu.stanford.cs108.mobiledrawing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.view.View;
import static edu.stanford.cs108.mobiledrawing.CustomView.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.radioRect);
    }

    public void onUpdate(View view) {
        CustomView cv = findViewById(R.id.customView);
        if(currIdx >= 0) {
            EditText editX = findViewById(R.id.editX);
            String strX = editX.getText().toString();
            EditText editY = findViewById(R.id.editY);
            String strY = editY.getText().toString();
            EditText editWidth = findViewById(R.id.editWidth);
            String strWidth = editWidth.getText().toString();
            EditText editHeight = findViewById(R.id.editHeight);
            String strHeight = editHeight.getText().toString();

            if (!(strX.equals("") || strY.equals("") || strWidth.equals("") || strHeight.equals(""))) {
                float floatX = Float.parseFloat(strX);
                float floatY = Float.parseFloat(strY);
                float floatWidth = Float.parseFloat(strWidth);
                float floatHeight = Float.parseFloat(strHeight);
                if (option.equals("Select") || option.equals("Erase")) {
                    shapes.get(currIdx).dimens.left = floatX;
                    shapes.get(currIdx).dimens.top = floatY;
                    shapes.get(currIdx).dimens.right = floatX + floatWidth;
                    shapes.get(currIdx).dimens.bottom = floatY + floatHeight;
                    hasUpdated = true;
                } else {
                    shapes.remove(currIdx);
                    left = floatX;
                    top = floatY;
                    right = floatX+floatWidth;
                    bottom = floatY+floatHeight;
                }
            }
        }
        cv.invalidate();
    }
}
