package com.e.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URI;


public class ViewStepFragment extends Fragment {

    private Uri videoUri;

    //mandatory constructor for instantiating the fragment
public ViewStepFragment(){}

private SimpleExoPlayer mExoplayer;
private SimpleExoPlayerView simpleExoPlayerView;
private TextView descriptionView;
private String videoUrlString;



    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View StepsView = inflater.inflate(R.layout.fragment_view_recipe_step, container, false);

        simpleExoPlayerView = StepsView.findViewById(R.id.playerView);
        descriptionView = StepsView.findViewById(R.id.step_description);


        initializePlayer(videoUri);

        return StepsView;
    }


    private void initializePlayer(Uri videoUri){
        if(mExoplayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoplayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector,loadControl);
            simpleExoPlayerView.setPlayer(mExoplayer);

            //prepare the media source
            String UserAgent = Util.getUserAgent(getActivity(), getActivity().getPackageName());
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory
                    (getActivity(), UserAgent), new DefaultExtractorsFactory(),null, null);

            mExoplayer.prepare(mediaSource);
            mExoplayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();

    }


    public void setVideoUrlString(String VideoUrl){
        if(VideoUrl!=null){
            videoUrlString = VideoUrl;
            videoUri = Uri.parse(videoUrlString);

        }}

    public void setLongDescription(String description) {
        descriptionView.setText(description);
    }

    private void releasePlayer() {
        mExoplayer.stop();
        mExoplayer.release();
        mExoplayer = null;
    }
}
