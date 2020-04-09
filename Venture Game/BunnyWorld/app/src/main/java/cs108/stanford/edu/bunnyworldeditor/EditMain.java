package cs108.stanford.edu.bunnyworldeditor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class EditMain extends AppCompatActivity {
    SQLiteDatabase db;
    Docs d;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // initialize docs
        Intent intent = getIntent();
        if(intent.getBooleanExtra("new", false)){
            d = new Docs();
            mySingleton.getInstance().docStored = d;
        }
        if(intent.getBooleanExtra("load",false)){
            String gameName = intent.getStringExtra("gameName");
            String query = "SELECT * FROM games WHERE gameName = " + gameName;

            Cursor cursor = db.rawQuery(query,null);
            String json = cursor.getString(1);
            Gson gson = new Gson();
            d = gson.fromJson(json, Docs.class);
            mySingleton.getInstance().docStored = d;
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_main);
        Button copyShape = (Button) findViewById(R.id.button_copyShape);
        Button clearShape = (Button) findViewById(R.id.button_clear);
        Button editShape = (Button) findViewById(R.id.button_editShape);
        Button gotoPage = (Button) findViewById(R.id.button_gotoPage);
        Button addPage = (Button) findViewById(R.id.button_addPage);
        Button editPage = (Button) findViewById(R.id.button_editPage);
        Button scriptButton = (Button)findViewById(R.id.button_script);
        Button addShapeButton = (Button)findViewById(R.id.button_addShape);
        Button saveEditGameButton = (Button) findViewById(R.id.button_saveData);
//        Button editShapeButton = (Button)findViewById(R.id.button_editShape);
//        Button copyShapeButton = (Button)findViewById(R.id.button_copyShape);
//        Button delShapeButton = (Button)findViewById(R.id.button_deleteShape);
//        Button pasteShapeButton = (Button)findViewById(R.id.button_pasteShape);
        TextView pageName = (TextView)findViewById(R.id.page_name);



        pageName.setText(mySingleton.getInstance().docStored.curPage.name);

        editShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySingleton.getInstance().docStored.curPage.selectedShape != null) {
                    mySingleton.getInstance().docStored.isSaved = false;
//                if (d.curPage.getSelectedShape() == null) {
//                    Toast.makeText(v.getContext(), "Please Select a Shape", Toast.LENGTH_SHORT).show();
//                } else {
                    startActivity(new Intent(EditMain.this, EditShape.class));
//                }
                }
            }
        });

        gotoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingleton.getInstance().docStored.isSaved = false;
                startActivity(new Intent(EditMain.this, GotoPage.class));
            }
        });

        addShapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingleton.getInstance().docStored.isSaved = false;
                startActivity(new Intent(EditMain.this, AddShape.class));
            }
        });


        addPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingleton.getInstance().docStored.isSaved = false;
                startActivity(new Intent(EditMain.this, AddPage.class));
            }
        });

        editPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySingleton.getInstance().docStored.curPage != null) {
                    mySingleton.getInstance().docStored.isSaved = false;
                    startActivity(new Intent(EditMain.this, EditPage.class));
                }
            }
        });

        scriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingleton.getInstance().docStored.isSaved = false;
                startActivity(new Intent(EditMain.this, EditScript.class));
            }
        });

        saveEditGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySingleton.getInstance().docStored.isSaved = false;
                if (mySingleton.getInstance().docStored.curPage != null) {
                    Intent intent = new Intent(EditMain.this, SaveEditGame.class);
                    if (intent.getBooleanExtra("new", true)) {
                        intent.putExtra("filename", "demo");
                    }
                    startActivity(intent);
                }
            }
        });

        clearShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Docs newDocs = mySingleton.getInstance().docStored;
                newDocs.shapeDict = new HashMap<>();
                newDocs.curPage.shapes = new ArrayList<>();
                newDocs.curPage.relatedShapes = new HashSet<>();
                newDocs.curPage.selectedShape = null;

                mySingleton.getInstance().setDocStored(newDocs);

                PageView pv = findViewById(R.id.pageView);
                pv.invalidate();
            }
        });

        copyShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mySingleton.getInstance().docStored.curPage.selectedShape == null){
                    try {
                        throw new Exception("No current page.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Shape selectedShape = mySingleton.getInstance().docStored.curPage.selectedShape;
                    String newName = "Copyed_" + selectedShape.id;
                    Shape newShape = new Shape(newName, 160, 160, 0, 0);
                    newShape.setImgPath(selectedShape.getImgPath());

                    try {
                        mySingleton.getInstance().docStored.addShape(newShape);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PageView pv = findViewById(R.id.pageView);
                    pv.invalidate();
                }
            }
        });


//        // set button visibility
//        if (mySingleton.getInstance().docStored.curPage.getSelectedShape() == null) {
//            scriptButton.setVisibility(View.GONE);
//            editShapeButton.setVisibility(View.GONE);
//            delShapeButton.setVisibility(View.GONE);
//            copyShapeButton.setVisibility(View.GONE);
//            pasteShapeButton.setVisibility(View.GONE);
//        }
//        else {
//            scriptButton.setVisibility(View.VISIBLE);
//            editShapeButton.setVisibility(View.VISIBLE);
//            delShapeButton.setVisibility(View.VISIBLE);
//            copyShapeButton.setVisibility(View.VISIBLE);
//            pasteShapeButton.setVisibility(View.VISIBLE);
//        }
//        scriptButton.invalidate();
    }


//        if (d.currentPage.getSelectedShape() == null) {
//            shapeBox.setVisibility(View.VISIBLE);
//            delShapeButton.setVisibility(View.GONE);
//            shapeBox.invalidate();
//            delShapeButton.invalidate();
//
//        }




//    public void editPage(View view) {
//        AlertDialog.Builder editPageDialog = new AlertDialog.Builder(EditMain.this);
//        View dialogView = LayoutInflater.from(EditMain.this).inflate(R.layout.dialoig_edit_page,
//                null);
//        editPageDialog.setTitle("Edit Page");
//        editPageDialog.setView(dialogView);
//        /*
//        editPageDialog.setPositiveButton("Save",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(),
//                                "Saved!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        editPageDialog.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//         */
//        editPageDialog.show();
//    }



    public void drawShape(View view) {
        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }


}

