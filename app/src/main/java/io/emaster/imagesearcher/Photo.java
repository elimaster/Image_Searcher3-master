package io.emaster.imagesearcher;

import java.io.Serializable;

/**
 * Created by user on 20/04/2016.
 */
public class Photo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String mTitle;
    private String mLink;
    private String mImage;
    private String mDateTaken;
    private String mDescription;
    private String mDatePublished;
    private String mAuthor;
    private String mAuthorId;
    private String mTags;

    public Photo(String mTitle, String mLink, String mImage, String mDateTaken, String mDescription, String mDatePublished, String mAuthor, String mAuthorId, String mTags) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mImage = mImage;
        this.mDateTaken = mDateTaken;
        this.mDescription = mDescription;
        this.mDatePublished = mDatePublished;
        this.mAuthor = mAuthor;
        this.mAuthorId = mAuthorId;
        this.mTags = mTags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mDateTaken='" + mDateTaken + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mDatePublished='" + mDatePublished + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mTags='" + mTags + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmDateTaken() {
        return mDateTaken;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmDatePublished() {
        return mDatePublished;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    public String getmTags() {
        return mTags;
    }
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
   "tags": "italy heritage history coast town seaside spring europe village liguria villages unesco worldheritagesite coastal april cinqueterre towns italianriviera 2016 fivevillages 5villages"

*/