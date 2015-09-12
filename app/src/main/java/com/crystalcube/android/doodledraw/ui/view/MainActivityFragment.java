package com.crystalcube.android.doodledraw.ui.view;

import android.support.v4.app.Fragment;

import com.crystalcube.android.doodledraw.R;
import com.crystalcube.android.doodledraw.ui.widgets.ColorPicker;
import com.crystalcube.android.doodledraw.ui.widgets.DoodleView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * A fragment containing a custom view that allow user to draw on a canvas.
 *
 * @author Tanveer
 *         Created: 11/09/2015
 */
@EFragment(R.layout.fragment_main)
@OptionsMenu(R.menu.menu_clear)
public class MainActivityFragment extends Fragment
        implements ColorPicker.OnColorSelectedListener {

    /**
     * Injected {@link DoodleView}, AA dictates default scope to be set.
     */
    @ViewById(R.id.doodle_view)
    DoodleView doodleView;

    @ViewById(R.id.color_picker)
    ColorPicker colorPicker;

    /*
    * Typical fragment setup is not required with AA extended fragments.
    * use view injection and @AfterViews life cycle hook to paint UI.
    *
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    */

    @AfterViews
    void setupViews() {
        colorPicker.setOnColorSelectedListener(this);
    }

    @OptionsItem(R.id.action_clear)
    public void onClearMenuItenSelected() {
        doodleView.clearDoodle();
    }

    @OptionsItem(R.id.action_save)
    public void onSaveMenuItenSelected() {
        doodleView.saveDoodle();
    }

    @Override
    public void onColorSelected(int color) {
        doodleView.setColor(color);
    }
}
