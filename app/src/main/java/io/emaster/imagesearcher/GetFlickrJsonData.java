package io.emaster.imagesearcher;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 20/04/2016.
 */
public class GetFlickrJsonData extends GetRawData {
    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;

    public List<Photo> getmPhotos() {
        return mPhotos;
    }

    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        createAndUpdateUrl(searchCriteria, matchAll);
        mPhotos = new ArrayList<Photo>();
    }

    @Override
    public void execute() {
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI = "+ mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());
    }

    private boolean createAndUpdateUrl(String searchCriteria, boolean matchAll) {
        //https://api.flickr.com/services/feeds/photos_public.gne?tags=towns&format=json&nojsoncallback=1
        final String FLICKR_API_BASE_URI = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";
        mDestinationUri = Uri.parse(FLICKR_API_BASE_URI).buildUpon()
                .appendQueryParameter(TAGS_PARAM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();
        return mDestinationUri != null;
    }

    public void processResult() {
        if(getmDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
        }

        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_LINK = "link";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_IMAGE_URL = "m"; //"media"
        final String FLICKR_DATETAKEN = "date_taken";
        final String FLICKR_DESCRIPTION = "description";
        final String FLICKR_DATEPUBLISHED = "published";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUYHORID = "author_id";
        final String FLICKR_TAGS = "tags";

        try{
            JSONObject jsonData = new JSONObject(getmData());
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
            for(int i=0; i<itemsArray.length(); i++){
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLE);
                //String link = jsonPhoto.getString(FLICKR_LINK);
                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String imageUrl = jsonMedia.getString(FLICKR_IMAGE_URL);
                String link = imageUrl.replaceFirst("_m.","_b.");
                String dateTaken = jsonPhoto.getString(FLICKR_DATETAKEN);
                String description = jsonPhoto.getString(FLICKR_DESCRIPTION);
                String datePublished = jsonPhoto.getString(FLICKR_DATEPUBLISHED);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUYHORID);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                Photo photoObject = new Photo(title, link, imageUrl, dateTaken, description, datePublished, author, authorId, tags);
                this.mPhotos.add(photoObject);
            }
            for(Photo singlePhoto: mPhotos){
                Log.v(LOG_TAG, singlePhoto.toString());
            }
        }catch (JSONException jsone){
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error processing json data");
        }
            /*
                "title": "Monterosso, Cinque Terre",
                "link": "https://www.flickr.com/photos/chibeba/26535917925/",
                "media": {"m":"https://farm2.staticflickr.com/1464/26535917925_f38059d348_m.jpg"},
                "date_taken": "2016-04-19T11:57:47-08:00",
                "description": " <p><a href=\"https://www.flickr.com/people/chibeba/\">chibeba<\/a> posted a photo:<\/p> <p><a href=\"https://www.flickr.com/photos/chibeba/26535917925/\" title=\"Monterosso, Cinque Terre\"><img src=\"https://farm2.staticflickr.com/1464/26535917925_f38059d348_m.jpg\" width=\"240\" height=\"180\" alt=\"Monterosso, Cinque Terre\" /><\/a><\/p> <p><\/p>",
                "published": "2016-04-20T05:54:40Z",
                "author": "nobody@flickr.com (chibeba)",
                "author_id": "26448117@N05",
                "tags": "italy heritage history coast town seaside spring europe village liguria villages"

             */
    }

    public class DownloadJsonData extends DownloadRawData{
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }



        @Override
        protected String doInBackground(String... params) {
            String[] par = {mDestinationUri.toString()};
            return super.doInBackground(par);
        }
    }
}
