package com.crystalcube.android.doodledraw.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.crystalcube.android.doodledraw.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

/**
 * <p>
 * Main dashboard activity that's launched when application starts
 * The activity uses AndroidAnnotations {@link EActivity}  and {@link OptionsMenu} annotations
 * to inject views and action bar menu.
 * </p>
 * <p>
 * For more information on AndroidAnnotation please visit:
 * <a href="http://androidannotations.org/">AndroidAnnotations website</a>
 * </p>
 *
 * @author Tanveer
 *         Created: 11/09/2015
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
    * Commented out to demonstrate boiler-plate code reduction
    * with AA. see reduced version of same function below.
    *
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @OptionsItem(R.id.action_settings)
    public void onSettingsSelected() {
        String settingPrompt = getString(R.string.prompt_setting);
        Toast.makeText(this, settingPrompt, Toast.LENGTH_SHORT).show();
    }
}
