package edu.stanford.cs108.cityinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = openOrCreateDatabase("CitiesDB",MODE_PRIVATE,null);
    }

    public void onAdd(View view) {
        EditText editName = (EditText) findViewById(R.id.editName);
        String stringName = editName.getText().toString();
        EditText editContinent = (EditText) findViewById(R.id.editContinent);
        String stringContinent = editContinent.getText().toString();
        EditText editPopulation = (EditText) findViewById(R.id.editPopulation);
        String stringPopulation = editPopulation.getText().toString();

        if(!(stringName.equals("") || stringContinent.equals("") || stringPopulation.equals(""))) {
            String sqlCommand = "INSERT INTO cities VALUES ('"
                    + stringName
                    + "','"
                    + stringContinent
                    + "',"
                    + stringPopulation
                    + ",NULL);";

            System.err.println(sqlCommand);

            db.execSQL(sqlCommand);

            Toast toast = Toast.makeText( this, stringName + " Added", Toast.LENGTH_SHORT);
            toast.show();
        }
        editName.getText().clear();
        editContinent.getText().clear();
        editPopulation.getText().clear();
    }
}
