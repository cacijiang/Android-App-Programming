package edu.stanford.cs108.cityinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LookupActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.radioGreater);

        db = openOrCreateDatabase("CitiesDB",MODE_PRIVATE,null);
    }

    public void onSearch(View view) {
        String query = "";
        EditText editName = (EditText) findViewById(R.id.editName);
        String stringName = editName.getText().toString();
        EditText editContinent = (EditText) findViewById(R.id.editContinent);
        String stringContinent = editContinent.getText().toString();
        EditText editPopulation = (EditText) findViewById(R.id.editPopulation);
        String stringPopulation = editPopulation.getText().toString();
        if(!stringName.equals("")) {
            query += "AND name LIKE '%" + stringName + "%'";
        }
        if(!stringContinent.equals("")) {
            query += "AND continent LIKE '%" + stringContinent + "%'";
        }
        if(!stringPopulation.equals("")) {
            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            int radioId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioId);
            String option = radioButton.getText().toString();
            if(option.equals("Greater or Equal")) {
                query += "AND population >= " + stringPopulation;
            } else {
                query += "AND population < " + stringPopulation;
            }
        }
        if(query.length() != 0) {
            query = query.substring(4);
            query = "WHERE " + query;
        }
        query = "SELECT * FROM cities " + query;

        Cursor cursor = db.rawQuery( query,null);
        String[] fromArray = {"name","continent","population"};
        int[] toArray = {R.id.dataName,R.id.dataContinent,R.id.dataPopulation};
        ListView listView = (ListView) findViewById(R.id.listViewData);
        ListAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.custom_rowview, cursor, fromArray, toArray, 0);
        listView.setAdapter(adapter);
    }
}
