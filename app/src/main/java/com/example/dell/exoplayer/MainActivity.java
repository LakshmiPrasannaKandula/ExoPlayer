package com.example.dell.exoplayer;

import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer  player;

    String url="http://blueappsoftware.in/layout_design_android_blog.mp4";

    private long curpos;
    private boolean playWhenReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exoPlayerView=findViewById(R.id.exoplayer);

        try {

            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
            TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            player=ExoPlayerFactory.newSimpleInstance(this,trackSelector);
            Uri uri=Uri.parse(url);
            DefaultHttpDataSourceFactory sourceFactory=new DefaultHttpDataSourceFactory("exoplayer");
            ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
            MediaSource mediaSource=new ExtractorMediaSource(uri,sourceFactory,extractorsFactory,null,null);

            exoPlayerView.setPlayer(player);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);

            if(savedInstanceState!=null){
                curpos=savedInstanceState.getLong("current");
                player.seekTo(curpos);
                playWhenReady=savedInstanceState.getBoolean("play_back");
                player.setPlayWhenReady(playWhenReady);
            }
        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player!=null){
            outState.putLong("current",player.getCurrentPosition());
            outState.putBoolean("play_back",player.getPlayWhenReady());
        }
    }

}
