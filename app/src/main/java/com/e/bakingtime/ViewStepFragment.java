package com.e.bakingtime;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;


public class ViewStepFragment extends Fragment {


    private BakingSteps step;
    private String description;
    private String videoURl;
    private boolean isTablet= false;
    private int currentPosition;
    private ArrayList<BakingSteps> stepsList;

    //mandatory constructor for instantiating the fragment
public ViewStepFragment(){}

private SimpleExoPlayer mExoplayer;
private SimpleExoPlayerView simpleExoPlayerView;
private TextView descriptionView;
private Button previousButton, nextButton;



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
        stepsList = bundle.getParcelableArrayList(StepPortraitActivity.PARCELED_LIST);

        description = step.getDescription();
        videoURl = step.getVideoURL();

        if(stepsList!=null){
            currentPosition = getIndexOfStep(step);
            Log.d("Position", String.valueOf(currentPosition));
        }
        isTablet = bundle.getBoolean("isTwopane");





     View StepsView = inflater.inflate(R.layout.fragment_view_recipe_step, container, false);

        simpleExoPlayerView = StepsView.findViewById(R.id.playerView);
        descriptionView = StepsView.findViewById(R.id.step_description);

        descriptionView.setText(description);

        previousButton = StepsView.findViewById(R.id.previous_button);
        nextButton = StepsView.findViewById(R.id.next_button);


        initializePlayer();

        if(isTablet){
            previousButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }

            setButtonClickListener();



        return StepsView;


    }

    private void setButtonClickListener() {

        nextButton.setOnClickListener(new View.OnClickListener() {
            ViewStepFragment viewStepFragment = new ViewStepFragment();
            Bundle bundle= new Bundle();
            @Override
            public void onClick(View view) {
                if(currentPosition == stepsList.size()-1){
                    currentPosition=0;
                    bundle.putParcelableArrayList(StepPortraitActivity.PARCELED_LIST, stepsList);
                    bundle.putParcelable(StepPortraitActivity.PARCELED_STEP,
                            stepsList.get(0));
                    viewStepFragment.setArguments(bundle);
                    performFragmentTransaction(viewStepFragment);
                }
                else{
                    bundle.putParcelableArrayList(StepPortraitActivity.PARCELED_LIST,
                            stepsList);
                    bundle.putParcelable(StepPortraitActivity.PARCELED_STEP,
                            stepsList.get(currentPosition + 1));
                    viewStepFragment.setArguments(bundle);
                    currentPosition++;
                    performFragmentTransaction(viewStepFragment);
                }


            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            ViewStepFragment mviewStepFragment = new ViewStepFragment();
            Bundle bundle = new Bundle();
            @Override
            public void onClick(View view) {
                if(currentPosition == 0) {
                    currentPosition = stepsList.size()-1;
                    bundle.putParcelableArrayList(StepPortraitActivity.PARCELED_LIST,
                            stepsList);
                    bundle.putParcelable(StepPortraitActivity.PARCELED_STEP,
                            stepsList.get(stepsList.size() - 1));
                    mviewStepFragment.setArguments(bundle);
                    performFragmentTransaction(mviewStepFragment);

                }
                else{
                    bundle.putParcelableArrayList(StepPortraitActivity.PARCELED_LIST,
                            stepsList);
                    bundle.putParcelable(StepPortraitActivity.PARCELED_STEP,
                            stepsList.get(currentPosition - 1));
                    mviewStepFragment.setArguments(bundle);
                    --currentPosition;
                    performFragmentTransaction(mviewStepFragment);


                }

            }
        });

    }

    public void performFragmentTransaction(ViewStepFragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_container, fragment)
                .commit();

    }

    private int getIndexOfStep(BakingSteps step) {
        for(BakingSteps bakingStep : stepsList)  {
            if(bakingStep.getId().equals(step.getId()))
                return stepsList.indexOf(bakingStep);
        }
        return -1;
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
