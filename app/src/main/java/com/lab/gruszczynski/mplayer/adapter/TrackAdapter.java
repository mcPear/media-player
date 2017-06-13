package com.lab.gruszczynski.mplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lab.gruszczynski.mplayer.R;
import com.lab.gruszczynski.mplayer.model.Track;

/**
 * Created by maciej on 05.06.17.
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private Track[] mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout relativeLayout;
        public ViewHolder(RelativeLayout l) {
            super(l);
            relativeLayout = l;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrackAdapter(Track[] myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView albumTV = (TextView) holder.relativeLayout.findViewById(R.id.albumTV);
        TextView performerTV = (TextView) holder.relativeLayout.findViewById(R.id.performerTV);
        TextView titleTV = (TextView) holder.relativeLayout.findViewById(R.id.titleTV);
        ImageView coverIV = (ImageView) holder.relativeLayout.findViewById(R.id.coverIV);

        albumTV.setText(mDataset[position].getAlbum());
        performerTV.setText(mDataset[position].getPerformer());
        titleTV.setText(mDataset[position].getTitle());
        coverIV.setImageDrawable(context.getDrawable(mDataset[position].getCover()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public Track getTrack(int position){
        return mDataset[position];
    }
}