package io.emaster.imagesearcher;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by user on 01/05/2016.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    protected Toolbar activateToolbar(){
        if(mToolbar == null){
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if(mToolbar!=null){
                setSupportActionBar(mToolbar);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
        return mToolbar;
    }
    protected Toolbar activateToolbarWhithHomeEnabled(){
        activateToolbar();
            if(mToolbar!=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        return mToolbar;
    }
}
