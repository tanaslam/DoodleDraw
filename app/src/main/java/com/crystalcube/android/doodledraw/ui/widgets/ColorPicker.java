package com.crystalcube.android.doodledraw.ui.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crystalcube.android.doodledraw.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * <p>
 * An extended {@link EViewGroup } custom view that use LinearLayout to display 2x5 color palette
 * and let user pick a color.
 * </p>
 *
 * @author tanny
 *         Created: 11/09/15.
 */
@EViewGroup(R.layout.layout_color_picker)
public class ColorPicker extends LinearLayout {

    /**
     * <p>
     * Color selection listener interface.
     * </p>
     */
    public interface OnColorSelectedListener {

        /**
         * Color selection callback method to notify color selection.
         *
         * @param color one of {@link Color} constant
         */
        void onColorSelected(int color);
    }

    private static final int COLORS[] = {
            Color.BLACK, Color.GRAY, Color.LTGRAY, Color.WHITE, Color.RED,
            Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};

    @ViewById(R.id.palette_row_top)
    protected ViewGroup topRow;

    @ViewById(R.id.palette_row_bottom)
    protected ViewGroup bottomRow;

    private int size;
    private int padding;

    private OnColorSelectedListener listener;

    public ColorPicker(Context context) {
        this(context, null);
    }

    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets color selection listener.
     *
     * @param listener The instance of {@link com.crystalcube.android.doodledraw.ui.widgets.ColorPicker.OnColorSelectedListener}
     */
    public void setOnColorSelectedListener(OnColorSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * This will be called when view initialization is done.
     */
    @AfterViews
    void init() {
        size = getContext().getResources().getDimensionPixelSize(R.dimen.color_item_size);
        padding = getContext().getResources().getDimensionPixelSize(R.dimen.color_item_padding);
        addTopRow();
        addBottomRow();
    }

    private void addTopRow() {

        for (int i = 0; i < COLORS.length / 2; i++) {
            addColorItem(topRow, COLORS[i]);
        }

        topRow.requestLayout();
    }

    private void addBottomRow() {
        for (int i = COLORS.length / 2; i < COLORS.length; i++) {
            addColorItem(bottomRow, COLORS[i]);
        }

        bottomRow.requestLayout();
    }

    private void addColorItem(ViewGroup container, int color1) {
        View view = new View(getContext());
        LayoutParams lp = getColorItemLayoutParams();
        view.setLayoutParams(lp);
        int color = color1;
        view.setBackgroundColor(color);
        view.setTag(color);
        setSelectionListener(view);
        container.addView(view);
    }

    private void setSelectionListener(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onColorSelected((Integer) v.getTag());
                }
            }
        });
    }

    private LayoutParams getColorItemLayoutParams() {
        LayoutParams lp = new LayoutParams(size, size, 1);
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.leftMargin = padding;
        lp.topMargin = padding;
        lp.rightMargin = padding;
        lp.bottomMargin = padding;
        return lp;
    }
}
