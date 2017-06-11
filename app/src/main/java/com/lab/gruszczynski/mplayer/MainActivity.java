package com.lab.gruszczynski.mplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.lab.gruszczynski.mplayer.adapter.TrackAdapter;
import com.lab.gruszczynski.mplayer.listener.RecyclerTouchListener;
import com.lab.gruszczynski.mplayer.model.Track;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrackAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView.ItemDecoration mDividerItemDecoration;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        // specify an adapter (see also next example)
        adapter = new TrackAdapter(getTrackSet(), getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplication(), recyclerView, new TrackTouchListener()));

    }

    private Track[] getTrackSet(){

        ArrayList<Track> tracks = new ArrayList<>();
        tracks.add(new Track(getString(R.string.taco_hemingway), getString(R.string.marmur), getString(R.string.deszcz), R.drawable.marmur, R.raw.deszcz_na_betonie));
        tracks.add(new Track(getString(R.string.taco_hemingway), getString(R.string.marmur), getString(R.string.prostokaty), R.drawable.marmur, R.raw.swiecace_prostokaty));
        tracks.add(new Track(getString(R.string.taco_hemingway), getString(R.string.wosk), getString(R.string.szczerze), R.drawable.wosk, R.raw.szczerze));

        return tracks.toArray(new Track[tracks.size()]);
    }

    private class TrackTouchListener implements RecyclerTouchListener.ClickListener{
        @Override
        public void onClick(View view, int position) {
            Track track = adapter.getTrack(position);
            Toast.makeText(context, track.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), PlayActivity.class);
            intent.putExtra("title", track.getTitle());
            intent.putExtra("album", track.getAlbum());
            intent.putExtra("performer", track.getPerformer());
            intent.putExtra("cover", track.getCover());
            intent.putExtra("sound", track.getSound());
            startActivity(intent);
        }
        @Override
        public void onLongClick(View view, int position) {}
    }
}
