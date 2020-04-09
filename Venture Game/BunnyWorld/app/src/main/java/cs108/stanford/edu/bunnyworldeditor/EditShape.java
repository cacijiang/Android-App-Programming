package cs108.stanford.edu.bunnyworldeditor;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EditShape extends Activity {
    private Docs d;
    private Shape currShape;
    public TextView shapeNameTextView;
    String imageName = "";
    public Switch isMovableSwitch;
    public Switch isHiddenSwitch;

    public Spinner imageSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        d = mySingleton.getInstance().docStored;
        currShape = d.curPage.getSelectedShape();
        // just for testing:
//        currShape = new Shape("abc",  10f, 12f, 50f, 50f);

        // set activity width and height
        setContentView(R.layout.dialog_edit_shape);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, (int)(height * 1));


        imageSpinner = (Spinner) findViewById(R.id.imgSpinner);
        Button confirmChanges = (Button) findViewById(R.id.confirmChanges);

        shapeNameTextView = (TextView) findViewById(R.id.shapeName);

        isMovableSwitch = (Switch) findViewById(R.id.shapeMovable);
        isHiddenSwitch = (Switch) findViewById(R.id.shapeHidden);

        shapeNameTextView.setText(currShape.getId());
        isMovableSwitch.setChecked(currShape.isMovable());
        isHiddenSwitch.setChecked(!currShape.isVisible());

        // if shape is text
        if (currShape.getText().length() != 0) {
            EditText temp_text = (EditText) findViewById(R.id.textInput);
            temp_text.setText(currShape.getText());
            EditText temp_f_size = (EditText) findViewById(R.id.fontBox);
            temp_f_size.setText(Float.toString(currShape.getFontSize()));
            int temp_c = currShape.getFontColor();
            int temp_a = (temp_c >> 24) & 0xff;
            int temp_r = (temp_c >> 16) & 0xff;
            int temp_g = (temp_c >>  8) & 0xff;
            int temp_b = (temp_c      ) & 0xff;
            SeekBar temp_sb0 = (SeekBar) findViewById(R.id.aset);
            SeekBar temp_sb1 = (SeekBar) findViewById(R.id.rset);
            SeekBar temp_sb2 = (SeekBar) findViewById(R.id.gset);
            SeekBar temp_sb3 = (SeekBar) findViewById(R.id.bset);
            temp_sb0.setProgress(temp_a);
            temp_sb1.setProgress(temp_r);
            temp_sb2.setProgress(temp_g);
            temp_sb3.setProgress(temp_b);
        }



        Resources res = getResources();
        ArrayList<String> imageNameList = new ArrayList<String>(Arrays.asList("carrot", "carrot2", "death", "duck", "fire", "mystic"));
        final int number_o_files = imageNameList.size();
        int num = CreatedShapes.getInstance().getShapes().size();
        LinkedList<String> imageNames_created = CreatedShapes.getInstance().getNames();
        int capacity = number_o_files + num;
        List<String> imagePathsArr = new ArrayList<String>(capacity);

        for (int idx = 0; idx < number_o_files; idx++) {
            imagePathsArr.add (imageNameList.get(idx));
        }

        for(int idx = 0; idx < num; idx++){
            imagePathsArr.add(imageNames_created.get(idx));
        }

        ArrayAdapter<String> imageNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, imagePathsArr);
        imageSpinner.setAdapter(imageNameAdapter);
        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                imageName = parent.getSelectedItem().toString();

            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        final View colorView = (View) findViewById(R.id.bgcolor);
        final SeekBar sb0 = (SeekBar) findViewById(R.id.aset);
        final SeekBar sb1 = (SeekBar) findViewById(R.id.rset);
        final SeekBar sb2 = (SeekBar) findViewById(R.id.gset);
        final SeekBar sb3 = (SeekBar) findViewById(R.id.bset);

        sb0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,int i, boolean b) {
                // + "" converts int i to a String
                int alpha = sb0.getProgress();
                int red = sb1.getProgress();
                int green = sb2.getProgress();
                int blue = sb3.getProgress();
                colorView.setBackgroundColor(Color.argb(alpha,red,green,blue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,int i, boolean b) {
                // + "" converts int i to a String
                int alpha = sb0.getProgress();
                int red = sb1.getProgress();
                int green = sb2.getProgress();
                int blue = sb3.getProgress();
                colorView.setBackgroundColor(Color.argb(alpha,red,green,blue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,int i, boolean b) {
                // + "" converts int i to a String
                int alpha = sb0.getProgress();
                int red = sb1.getProgress();
                int green = sb2.getProgress();
                int blue = sb3.getProgress();
                colorView.setBackgroundColor(Color.argb(alpha,red,green,blue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,int i, boolean b) {
                // + "" converts int i to a String
                int alpha = sb0.getProgress();
                int red = sb1.getProgress();
                int green = sb2.getProgress();
                int blue = sb3.getProgress();
                colorView.setBackgroundColor(Color.argb(alpha,red,green,blue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        confirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText theText = (EditText) findViewById(R.id.textInput);
                String theText_value = theText.getText().toString();
                if (theText_value.length() > 0) {
                    currShape.setText(theText_value);

                    View colorView = (View) findViewById(R.id.bgcolor);
                    SeekBar sb0 = (SeekBar) findViewById(R.id.aset);
                    SeekBar sb1 = (SeekBar) findViewById(R.id.rset);
                    SeekBar sb2 = (SeekBar) findViewById(R.id.gset);
                    SeekBar sb3 = (SeekBar) findViewById(R.id.bset);
                    int alpha = sb0.getProgress();
                    int red = sb1.getProgress();
                    int green = sb2.getProgress();
                    int blue = sb3.getProgress();
                    currShape.setFontColor(Color.argb(alpha, red, green, blue));

                    EditText fsize = (EditText) findViewById(R.id.fontBox);
                    if (fsize.getText().toString().length() > 0) {
                        float fsize_value = Float.parseFloat(fsize.getText().toString());
                        currShape.setFontSize(fsize_value);
                    } else {
                        currShape.setFontSize(20);
                    }
                }
                if (imageName.length() != 0) {
                    currShape.setImgPath(imageName);
                }

                String newName = shapeNameTextView.getText().toString();
                if (!newName.equals(currShape.id)) {
                    try {
                        d.renameShape(currShape, newName);

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                currShape.setMovable(isHiddenSwitch.isChecked());
                currShape.setVisible(!isHiddenSwitch.isChecked());
                Intent intent = new Intent(EditShape.this, EditMain.class);
                startActivity(intent);
            }
        });
    }
}


