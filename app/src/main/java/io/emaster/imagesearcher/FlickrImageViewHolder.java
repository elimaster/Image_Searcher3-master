package io.emaster.imagesearcher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 20/04/2016.
 */
public class FlickrImageViewHolder extends RecyclerView.ViewHolder {
    protected ImageView imageView;
    protected TextView textView;

    public FlickrImageViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView)itemView.findViewById(R.id.thumbnail_list);
        this.textView = (TextView) itemView.findViewById(R.id.title_list);
    }
}
