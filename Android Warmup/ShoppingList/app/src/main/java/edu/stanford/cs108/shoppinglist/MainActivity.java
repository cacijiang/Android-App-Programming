package edu.stanford.cs108.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editItem;
    private TextView itemList;
    private Button buttonAdd, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editItem  =  findViewById(R.id.editItem);
        buttonAdd =  findViewById(R.id.buttonAdd);
        buttonClear = findViewById(R.id.buttonClear);
        itemList = findViewById(R.id.itemList);
        buttonAdd.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonAdd:
                String curr = itemList.getText().toString();
                String item = editItem.getText().toString();
                itemList.setText(curr+item+"\n");
                editItem.setText("");
                break;
            case R.id.buttonClear:
                itemList.setText("");
        }
    }
}
