package com.crystalcube.android.doodledraw.ui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.crystalcube.android.doodledraw.R;

import org.androidannotations.annotations.EView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * <p>
 * An extended {@link EView} custom view that uses custom paint and color and let user
 * to draw on view canvas. {@link EView} annotation let us inject system services and resources.
 * </p>
 *
 * @author Tanveer
 *         Created: 11/09/2015
 */
@EView
public class DoodleView extends View {

    private static final String TAG = DoodleView.class.getSimpleName();

    private HashMap<Integer, Path> paths = new HashMap<>();
    private Path currentPath;
    private Paint paint;
    private int drawColor = Color.BLACK;
    private float x, y;
    private static final float TOLERANCE = 5;

    public DoodleView(Context context) {
        this(context, null);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        // Enable drawing cache to save bitmap
        setDrawingCacheEnabled(true);

        // Create paint with stroke
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10f);

        // Create a new drawing path with default color that we'll update and drawn on touch
        setColor(drawColor);
    }

    /**
     * Returns selected draw color
     *
     * @return One of the color const defined in {@link Color}
     * @see Color#BLACK
     * @see Color#BLUE
     * @see Color#RED
     * @see Color#GREEN
     */
    public int getDrawColor() {
        return drawColor;
    }

    /**
     * Clears the drawing view
     */
    public void clearDoodle() {
        for (Integer key : paths.keySet()) {
            paths.get(key).reset();
        }
        invalidate();
    }

    /**
     * Saves canvas drawing to galley using drawing cache
     */
    public void saveDoodle() {
        try {
            setDrawingCacheBackgroundColor(Color.WHITE);
            Bitmap bitmap = getDrawingCache();
            Canvas canvas = new Canvas(bitmap);
            drawPaths(canvas);
            String fileName = Environment.getExternalStorageDirectory() + "/" +
                    Environment.DIRECTORY_PICTURES + "/doodle-" + System.currentTimeMillis() + ".jpg";
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            Toast.makeText(getContext(),
                    getContext().getResources().getString(R.string.bitmap_saved), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Unable to save doodle, error:- " + e.getMessage());
        }
    }

    /**
     * Sets doodle color for drawing.
     *
     * @param colorInt one on the color constant from {@link Color} class
     */
    public void setColor(int colorInt) {
        drawColor = colorInt;
        paint.setColor(drawColor);
        setCurrentPath();
    }

    private void penDown(float x, float y) {
        /* Start paths at x,y screen coordinates */
        currentPath.moveTo(x, y);
        this.x = x;
        this.y = y;
    }


    private void penMove(float x, float y) {
        /* Move paths to new x,y coordinates */
        float dx = Math.abs(x - this.x);
        float dy = Math.abs(y - this.y);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            currentPath.quadTo(this.x, this.y, (x + this.x) / 2, (y + this.y) / 2);
            this.x = x;
            this.y = y;
        }
    }

    private void upTouch() {
        /*Draw doodle along paths */
        currentPath.lineTo(x, y);
    }

    private void setCurrentPath() {
        if (paths.containsKey(drawColor)) {
            currentPath = paths.get(drawColor);
        } else {
            currentPath = new Path();
            paths.put(drawColor, currentPath);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                penDown(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                penMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }

    private void drawPaths(Canvas canvas) {
        for (Integer key : paths.keySet()) {
            Path path = paths.get(key);
            paint.setColor(key);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        /*TODO Declare parcelable state (paths and currentPath) and
         save instance state we want keep during configuration changes */
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        //TODO restore instance data we want keep during configuration changes
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPaths(canvas);
    }
}