package edu.stanford.cs108.colorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.SeekBar;
import android.view.View;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {
    private Button buttonChange;
    private SeekBar seekRed;
    private SeekBar seekGreen;
    private SeekBar seekBlue;
    private TextView colorInfo;
    private View colorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorView = findViewById(R.id.colorView);
        seekRed = findViewById(R.id.seekRed);
        seekGreen = findViewById(R.id.seekGreen);
        seekBlue = findViewById(R.id.seekBlue);
        colorInfo = findViewById(R.id.colorInfo);
        buttonChange = findViewById(R.id.buttonChange);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int red = seekRed.getProgress();
                int green = seekGreen.getProgress();
                int blue = seekBlue.getProgress();
                colorView.setBackgroundColor(Color.rgb(red,green,blue));
                StringBuilder sb = new StringBuilder();
                sb.append("Red: ").append(red).append(", ").append("Green: ").append(green).
                        append(", ").append("Blue: ").append(blue);
                colorInfo.setText(sb.toString());
            }
        });
    }
}
