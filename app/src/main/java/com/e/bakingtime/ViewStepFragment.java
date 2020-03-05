package com.e.bakingtime;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.bakingtime.Model.BakingSteps;
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

import java.util.List;


public class ViewStepFragment extends Fragment {


    private BakingSteps step;
    private String description;
    private String videoURl;

    //mandatory constructor for instantiating the fragment
public ViewStepFragment(){}

private SimpleExoPlayer mExoplayer;
private SimpleExoPlayerView simpleExoPlayerView;
private TextView descriptionView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        step = bundle.getParcelable(StepPortraitActivity.PARCELED_STEP);



            description = step.getDescription();
            videoURl = step.getVideoURL();




     View StepsView = inflater.inflate(R.layout.fragment_view_recipe_step, container, false);

        simpleExoPlayerView = StepsView.findViewById(R.id.playerView);
        descriptionView = StepsView.findViewById(R.id.step_description);
        descriptionView.setText(description);


        initializePlayer();

        return StepsView;
    }



    private void initializePlayer(){
        if(mExoplayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoplayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(mExoplayer);

            //prepare the media source
            if (videoURl.isEmpty()) {
                Toast.makeText(getContext(), "No video link", Toast.LENGTH_SHORT).show();
                simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                        getResources(), R.drawable.exo_controls_pause));
            }
            else {
                String UserAgent = Util.getUserAgent(getActivity(), getActivity().getPackageName());
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURl), new DefaultDataSourceFactory
                        (getActivity(), UserAgent), new DefaultExtractorsFactory(), null, null);

                mExoplayer.prepare(mediaSource);
                mExoplayer.setPlayWhenReady(true);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if((Util.SDK_INT <= 23)|| mExoplayer==null){
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();

    }

    private void releasePlayer() {
        mExoplayer.stop();
        mExoplayer.release();
        mExoplayer = null;
    }
}
