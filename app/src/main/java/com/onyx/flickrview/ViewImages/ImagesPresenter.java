package com.onyx.flickrview.viewImages;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.onyx.flickrview.data.Image;
import com.onyx.flickrview.service.FlickrService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by onyekaanene on 17/11/2017.
 */

public class ImagesPresenter implements ImagesContract.ActionsListener{

    private static final String TAG = ImagesPresenter.class.getSimpleName();

    private final ImagesContract.View mImagesView;

    public ImagesPresenter(@NonNull ImagesContract.View imagesView) {
        mImagesView = checkNotNull(imagesView, "imagesView cannot be null!");
    }

    @Override
    public void loadImages(boolean forceUpdate) {
        Log.d(TAG, "In load images");
        mImagesView.setProgressIndicator(true);
//        if (!forceUpdate) {
//            return;
//        }
        new FetchImageTask().execute();
    }


    public class FetchImageTask extends AsyncTask<String, Void, Image[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "In FetchImageTask onPreExecute");
            mImagesView.setProgressIndicator(true);
        }

        @Override
        protected Image[] doInBackground(String... params) {
            URL imgRequestUrl = FlickrService.buildUrl();
            Log.d(TAG, "FetchImageTask doInBackground. URL is "+ imgRequestUrl);
            try {
                String excJsonResponse = FlickrService.getResponse(imgRequestUrl);
                Image[] rateData = formatJson(excJsonResponse);
                Log.d(TAG, "Data fetched from url: "+ excJsonResponse);


                return rateData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Image[] imageData) {
            Log.d(TAG, "In onPostExecute" );
            if (imageData != null) {
                String[] imageUrls = new String[imageData.length];
                int index = 0;
                for(Image image: imageData){
                    imageUrls[index]=image.getmImageUrl();
                    index++;
                }
                mImagesView.showImages(imageUrls);
            }
        }
    }

    public Image[] formatJson (String jsonString) throws JSONException {

        JSONObject json = new JSONObject(jsonString);
        JSONObject jsonFlickrFeedJson = json.getJSONObject("jsonFlickrFeed");
        JSONArray imagesArray;
        try {
            imagesArray = jsonFlickrFeedJson.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Image>>() {}.getType();
        ArrayList<Image> imagesJson = gson.fromJson(imagesArray.toString(), listType);

        int numberOfImages = imagesJson.size();
        Image[] imageItems = new Image[numberOfImages];

        int index = 0;
        for(Image image: imagesJson){
            imageItems[index]=image;
            index++;
        }

        return imageItems;
    }
}
