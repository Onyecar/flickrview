package com.onyx.flickrview.ViewImages;

import com.onyx.flickrview.data.Image;

import java.util.List;

/**
 * Created by onyekaanene on 17/11/2017.
 */

public interface ImagesContract {
    interface View {

        void setProgressIndicator(boolean active);

        void getImages(List<Image> images);

        void showImages(String[] imageUrls);

    }
}