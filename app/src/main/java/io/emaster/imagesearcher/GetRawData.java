package io.emaster.imagesearcher;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 17/04/2016.
 */
enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    public GetRawData(String mRawUrl) {
        this.mDownloadStatus = mDownloadStatus.IDLE;
        this.mRawUrl = mRawUrl;
    }

    public void reset(){
        this.mData = null;
        this.mRawUrl = null;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    public void execute(){
        //this.mData = null;
        //this.mRawUrl = null;
        this.mDownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public String getmData() {
        return mData;
    }

    public class DownloadRawData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            mData = webData;
            Log.v(LOG_TAG, "data returned was: "+ mData);
            if(mData == null){
                if(mRawUrl == null){
                    mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
                }
            }else{//Success
                mDownloadStatus = DownloadStatus.OK;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            if(params ==null)
                return null;
            try {
                URL url = new URL(params[0]);
                Log.v(LOG_TAG, "params 0: "+ params[0].toString());
                //Log.v(LOG_TAG, "params 1: "+ params[1].toString());
                //Log.v(LOG_TAG, "params 2: "+ params[2].toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                StringBuffer stringBuffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                return stringBuffer.toString();
            }catch (MalformedURLException e){
                Log.e(LOG_TAG, "Error MalformedURL Exception", e);
                return null;
            }catch (IOException e){
                Log.e(LOG_TAG, "Error", e);
                return null;
            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch (final IOException e){
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }
    }
}
