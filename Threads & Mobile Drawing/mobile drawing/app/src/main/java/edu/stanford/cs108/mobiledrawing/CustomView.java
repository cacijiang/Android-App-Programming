package edu.stanford.cs108.mobiledrawing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.graphics.RectF;
import java.util.*;
import android.app.Activity;
import android.widget.EditText;


import androidx.annotation.Nullable;

public class CustomView extends View {
    protected static int currIdx = -1;
    protected static float x, y, x1, y1, x2, y2;
    protected static float left, right, top, bottom;
    protected static Paint whileFillPaint, redOutlinePaint, blueOutlinePaint;
    protected static String option = "Rect";
    protected static boolean hasUpdated;
    protected static List<Shape> shapes = new ArrayList<>();

    protected class Shape {
        RectF dimens;
        boolean isRect;

        protected Shape(RectF dimens, boolean isRect) {
            this.dimens = new RectF(dimens);
            this.isRect = isRect;
        }
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        whileFillPaint = new Paint();
        whileFillPaint.setColor(Color.WHITE);
        redOutlinePaint = new Paint();
        redOutlinePaint.setColor(Color.RED);
        redOutlinePaint.setStyle(Paint.Style.STROKE);
        redOutlinePaint.setStrokeWidth(5.0f);
        blueOutlinePaint = new Paint();
        blueOutlinePaint.setColor(Color.BLUE);
        blueOutlinePaint.setStyle(Paint.Style.STROKE);
        blueOutlinePaint.setStrokeWidth(15.0f);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (option) {
            case "Select":
                if(!hasUpdated) {
                    updateCurrentIndex();
                }
                hasUpdated = false;
                drawHelper(canvas);
                displayTextFields();
                break;
            case "Rect":
                RectF rectangle = new RectF(left, top, right, bottom);
                currIdx = shapes.size();
                shapes.add(new Shape(rectangle,true));
                drawHelper(canvas);
                displayTextFields();
                break;
            case "Oval":
                RectF oval = new RectF(left, top, right, bottom);
                currIdx = shapes.size();
                shapes.add(new Shape(oval,false));
                drawHelper(canvas);
                displayTextFields();
                break;
            case "Erase":
                if(!hasUpdated) {
                    updateCurrentIndex();
                }
                hasUpdated = false;
                if(currIdx >= 0) {
                    shapes.remove(currIdx);
                    currIdx = -1;
                }
                drawHelper(canvas);
                displayTextFields();
                break;
        }
    }

    private void drawHelper(Canvas canvas) {
        for(int i = 0; i < shapes.size(); i++) {
            Shape currShape = shapes.get(i);
            if(currShape.isRect) {
                canvas.drawRect(currShape.dimens, whileFillPaint);
                if(i == currIdx) {
                    canvas.drawRect(currShape.dimens, blueOutlinePaint);
                } else {
                    canvas.drawRect(currShape.dimens, redOutlinePaint);
                }
            }  else {
                canvas.drawOval(currShape.dimens, whileFillPaint);
                if(i == currIdx) {
                    canvas.drawOval(currShape.dimens, blueOutlinePaint);
                } else {
                    canvas.drawOval(currShape.dimens, redOutlinePaint);
                }
            }
        }
    }

    private void updateCurrentIndex() {
        for(int i = shapes.size()-1; i >= 0; i--) {
            Shape currShape = shapes.get(i);
            if(currShape.dimens.left <= x && currShape.dimens.right >= x
                    && currShape.dimens.top <= y && currShape.dimens.bottom >= y) {
                currIdx = i;
                return;
            }
        }
        currIdx = -1;
    }

    public void displayTextFields() {
        if(currIdx >= 0) {
            Shape currShape = shapes.get(currIdx);
            float width = currShape.dimens.right - currShape.dimens.left;
            float height = currShape.dimens.bottom - currShape.dimens.top;
            EditText editX = ((Activity) getContext()).findViewById(R.id.editX);
            editX.setText(String.valueOf(currShape.dimens.left));
            EditText editY = ((Activity) getContext()).findViewById(R.id.editY);
            editY.setText(String.valueOf(currShape.dimens.top));
            EditText editWidth = ((Activity) getContext()).findViewById(R.id.editWidth);
            editWidth.setText(String.valueOf(width));
            EditText editHeight = ((Activity) getContext()).findViewById(R.id.editHeight);
            editHeight.setText(String.valueOf(height));
        } else {
            EditText editX = ((Activity) getContext()).findViewById(R.id.editX);
            editX.setText("");
            EditText editY = ((Activity) getContext()).findViewById(R.id.editY);
            editY.setText("");
            EditText editWidth = ((Activity) getContext()).findViewById(R.id.editWidth);
            editWidth.setText("");
            EditText editHeight = ((Activity) getContext()).findViewById(R.id.editHeight);
            editHeight.setText("");
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        RadioGroup radioGroup = ((Activity) getContext()).findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = ((Activity) getContext()).findViewById(radioId);
        option = radioButton.getText().toString();
        switch (option) {
            case "Select":
            case "Erase":
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        invalidate();
                        break;
                }
                break;
            case "Rect":
            case "Oval":
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();

                        if (x1>x2) {
                            left = x2;
                            right = x1;
                        } else {
                            left = x1;
                            right = x2;
                        }

                        if (y1>y2) {
                            top = y2;
                            bottom = y1;
                        } else {
                            top = y1;
                            bottom = y2;
                        }
                        invalidate();
                        break;
                }
                break;
        }
        return true;
    }
}

