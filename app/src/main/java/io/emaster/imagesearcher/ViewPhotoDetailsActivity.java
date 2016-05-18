package io.emaster.imagesearcher;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@TargetApi(Build.VERSION_CODES.M)
public class ViewPhotoDetailsActivity extends BaseActivity  {

    ViewGroup detailContainer;
    //@BindView(R.id.ll_container) ViewGroup detailContainer;
    //@BindView(R.id.photo_title) TextView photo_title;
    //@BindView(R.id.photo_tags) TextView photo_tags;
    //@BindView(R.id.photo_author)TextView photo_author;
    //@BindView(R.id.photo_date_published)TextView photo_date_published;
    //@BindView(R.id.photo_date_taken)TextView photo_date_taken;
    //@BindView(R.id.photo_author_id)TextView photo_author_id;
    FloatingActionButton fab;
    Photo photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        activateToolbarWhithHomeEnabled();

        Intent intent =getIntent();
        photo = (Photo)intent.getSerializableExtra(PHOTO_TRANSFER);

        //ButterKnife.bind(this);

        BindViews(photo);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ButterKnife.bind(this);
        //populate();
    }

    private void animate(){
        fab.setScaleX(0);
        fab.setScaleY(0);
        fab.animate().scaleX(1).scaleY(1).start();
    }

    public void BindViews(Photo photo) {
        detailContainer = (ViewGroup) findViewById(R.id.ll_container);
        TextView photo_title = (TextView)findViewById(R.id.photo_title);
        photo_title.setText(photo.getmTitle());
        TextView photo_tags = (TextView)findViewById(R.id.photo_tags);
        photo_tags.setText(photo.getmTags());
        TextView photo_author = (TextView)findViewById(R.id.photo_author);
        photo_author.setText(photo.getmAuthor());
        TextView photo_date_published = (TextView)findViewById(R.id.photo_date_published);
        photo_date_published.setText(photo.getmDatePublished());
        TextView photo_date_taken = (TextView)findViewById(R.id.photo_date_taken);
        photo_date_taken.setText(photo.getmDateTaken());
        TextView photo_author_id = (TextView)findViewById(R.id.photo_author_id);
        photo_author_id.setText(photo.getmAuthorId());


        ImageView photoImage = (ImageView)findViewById(R.id.photo_image);
        Picasso.with(this).load(photo.getmLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
    }

    public void onPanelClick(View view){
        animate();
    }

    //@OnClick(R.id.photo_image)
    public void onImageClick(View view){
        setupTransition();
    }

    public void setupTransition() {
        ViewGroup transitionRoot = detailContainer;
        Scene expandedScene = Scene.getSceneForLayout(transitionRoot,
                R.layout.content_view_photo_details_expanded, this);
        //TransitionManager.go(expandedScene, new ChangeBounds());
        expandedScene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                BindViews(photo);
                //ButterKnife.bind(ViewPhotoDetailsActivity.this);
                populate();
            }
        });

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200);
        transitionSet.addTransition(changeBounds);

        Fade fadeDetails = new Fade();
        fadeDetails.addTarget(R.id.photo_tags);
        fadeDetails.setDuration(300);
        transitionSet.addTransition(fadeDetails);

        TransitionManager.go(expandedScene, transitionSet);
    }
/*
    @OnClick(R.id.photo_image) void onImageClickTrans(View view){
            ViewGroup transitionRoot = detailContainer;
            Scene expandedScene = Scene.getSceneForLayout(transitionRoot,
                    R.layout.content_view_photo_details_expanded, view.getContext());
            TransitionManager.go(expandedScene, new ChangeBounds());
    }
*/
    private void populate(){
        Bitmap photoBitmap = getReducedBitmap(R.id.photo_image);
        colorizeFromImage(photoBitmap);
    }

    private Bitmap getReducedBitmap(int photoResId){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 8;
        return BitmapFactory.decodeResource(getResources(),photoResId, options);
    }

    private void colorizeFromImage(Bitmap image){
        Palette palette = Palette.from(image).generate();
        //set panel color
        int defaultPanelColor = 0xFF808080;
        int defaultFabColor = 0xFFEEEEEE;
        palette.getDarkVibrantColor(defaultPanelColor);
        //palette.getLightVibrantColor(defaultPanelColor);
        palette.getLightMutedColor(defaultPanelColor);
        //palette.getMutedColor(defaultPanelColor);
        //palette.getDarkMutedColor(defaultPanelColor);

        //set fab color
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_pressed}
        };
        int[] colors = new int[]{
                palette.getVibrantColor(defaultFabColor),
                palette.getLightVibrantColor(defaultFabColor)
        };
        fab.setBackgroundTintList(new ColorStateList(states, colors));
    }
}
