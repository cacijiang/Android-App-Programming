package cs108.stanford.edu.bunnyworldeditor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LoadAdventure extends AppCompatActivity {


    SQLiteDatabase db;
    String tableName = "games";
    boolean isEdit;
    boolean isSaving;
    SimpleCursorAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_adventure);
        setTitle("Adventure Selection");
        db = openOrCreateDatabase("GamesDB", MODE_PRIVATE,null);
        listView = (ListView) findViewById(R.id.loadListView);
        // Determine which table to load from
//        Intent intent = getIntent();
//        isSaving = intent.getBooleanExtra("isSaving", false);
//        isEdit = intent.getBooleanExtra("isEdit", false);
//        final boolean isNewPlay = intent.getBooleanExtra("isNewPlay", false);
//        if(isEdit || isNewPlay){
//            tableName = "editModeSave";
//        } else{
//            tableName = "playModeSave";
//        }

        // Choose which buttom to show
        Button saveButton = (Button) findViewById(R.id.save);
        Button loadButton = (Button) findViewById(R.id.load);

//        List<>loadNames();
//        Cursor tablesCursor = db.rawQuery(
//                "SELECT * FROM sqlite_master WHERE type='table' AND name='" + tableName + "';", null);
//        System.out.println("SELECT * FROM sqlite_master WHERE type='table' AND name='" + tableName + "';");

        showSavings(tableName);

    }

    public void onCreateNew(View view) {
        Intent intent = new Intent(this, EditMain.class);
        intent.putExtra("new", true);
        startActivity(intent);
    }

    private List<String> loadNames() {
        List<String> names = new ArrayList<>();
        String query = "SELECT name FROM games";
        Cursor cursor = db.rawQuery( query,null);
        while (cursor.moveToNext()) {
            names.add(cursor.getString(0));
        }
        return names;
    }


    public void onLoad(View view){
        ListView listView = (ListView) findViewById(R.id.loadListView);
        int pos = listView.getCheckedItemPosition();
        if(pos == AdapterView.INVALID_POSITION){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please select an adventure to edit on.",
                    Toast.LENGTH_SHORT);
            toast.show();
        } else{
            TextView selectedTextView = (TextView) adapter.getView(pos, null, listView);
            String gameName = selectedTextView.getText().toString();
            String sqlStr = "SELECT gamesData from "
                    + tableName
                    + " where name = '"
                    + gameName
                    +"';";
            Cursor tableCursor = db.rawQuery(sqlStr, null);
            String fileName;
            if(tableCursor.moveToNext()){
                fileName = tableCursor.getString(0);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Load from " + gameName,
                        Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(LoadAdventure.this, EditMain.class);
                intent.putExtra("fromLoad", true);
                intent.putExtra("gameName", fileName);
                startActivity(intent);
            }
        }
    }



    private void showSavings(String tableName){
        String selectTable;
        selectTable = "SELECT * from " + tableName;
        Cursor tableCursor = db.rawQuery(selectTable, null);
        String[] fromArray = {"name"};
        int[] toArray = {android.R.id.text1};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_activated_1,
                tableCursor, fromArray, toArray, 0);
        ListView listView = (ListView) findViewById(R.id.loadListView);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.load_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.select_mode:
                intent = new Intent(this, EditMain.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void onReset(View view) {
        String resetStr = "DROP TABLE IF EXISTS games;";
        db.execSQL(resetStr);
        setupDatabase();

        //refresh
        String selectTable = "SELECT * from " + tableName;
        Cursor tableCursor = db.rawQuery(selectTable, null);
        adapter.changeCursor(tableCursor);
        listView.setItemChecked(-1, true);
        listView.invalidateViews();

    }

    public void onDelete(View view){
        ListView listView = (ListView) findViewById(R.id.loadListView);
        int pos = listView.getCheckedItemPosition();
        if(pos == AdapterView.INVALID_POSITION){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please select a record first",
                    Toast.LENGTH_SHORT);
            toast.show();
        } else{
            TextView selectedTextView = (TextView) adapter.getView(pos, null, listView);
            String gameName;
            gameName = selectedTextView.getText().toString();
            if(gameName.equals("++New Saving++")){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Are you sure you want to DELETE" + gameName,
                        Toast.LENGTH_SHORT);
                toast.show();
            } else{
                String sqlStr = "DELETE from "
                        + tableName
                        + " where name = '"
                        + gameName
                        +"';";
                try{
                    db.execSQL(sqlStr);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Successfully delete " + gameName,
                            Toast.LENGTH_SHORT);
                    toast.show();

                    //refresh
                    String selectTable = "SELECT * from " + tableName;
                    Cursor tableCursor = db.rawQuery(selectTable, null);
                    adapter.changeCursor(tableCursor);
                    listView.setItemChecked(-1, true);
                    listView.invalidateViews();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }


    private void setupDatabase() {
        String setupStr = "CREATE TABLE games ("
                + "name TEXT, gamesData TEXT,"
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ");";
        System.err.println(setupStr);
        db.execSQL(setupStr);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Database Reset.", Toast.LENGTH_SHORT);
        toast.show();

    }
}




