package com.onyx.flickrview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.onyx.flickrview.data.Image;
import com.onyx.flickrview.R;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Image> mImages;
    Context mContext;

    public ImageAdapter(Context context, List<Image> images) {
        setList(images);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View imageView = inflater.inflate(R.layout.item_image, parent, false);

        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Image image = mImages.get(position);

        Glide.with(mContext).load(viewHolder.image.getmMediaUrl().getmImageUrl()).into(viewHolder.mImageView);
    }

    public void replaceData(List<Image> images) {
        setList(images);
        notifyDataSetChanged();
    }

    private void setList(List<Image> images) {
        mImages = checkNotNull(images);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public Image getItem(int position) {
        return mImages.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Image image;
        public final ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
