package com.tefah.bakingapp.UIs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.tefah.bakingapp.R;
import com.tefah.bakingapp.UIs.StepDisplayActivity;
import com.tefah.bakingapp.pojo.Recipe;
import com.tefah.bakingapp.pojo.Step;

import org.parceler.Parcels;

import butterknife.ButterKnife;

/**
 * Fragment to display step details
 */

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener{

    public static Recipe recipe;
    private Step step;
    private static int position;
    private SimpleExoPlayer exoPlayer;
    private Context context;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder playbackStateCompat;


    SimpleExoPlayerView exoPlayerView;
    TextView    detailedDescription;
    Button  nextButton;
    Button  prevButton;

    public StepDetailFragment(){}

    public void setVariables(Bundle args) {
        recipe = Parcels.unwrap(args.getParcelable(String.valueOf(R.string.recipeKey)));
        position = args.getInt(String.valueOf(R.string.positionKey));
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        context = getContext();
        exoPlayerView = ButterKnife.findById(rootView, R.id.simpleExoPlayerView);

        if (rootView.findViewById(R.id.stepDetailedDescription)!= null){
            detailedDescription = ButterKnife.findById(rootView, R.id.stepDetailedDescription);
            nextButton          = ButterKnife.findById(rootView, R.id.next);
            prevButton          = ButterKnife.findById(rootView, R.id.prev);
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position > 0) {
                        position--;
                        if (getActivity()  instanceof StepDisplayActivity)
                            ((StepDisplayActivity) getActivity()).setPosition(position);
                        start();
                    }
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position < recipe.getSteps().size() - 1) {
                        position++;
                        if (getActivity()  instanceof StepDisplayActivity)
                            ((StepDisplayActivity) getActivity()).setPosition(position);
                        start();
                    }
                }
            });
        }
        start();
        initializeMediaSession();
        return rootView;
    }

    /**
     * start display for the first time and restart display with new step
     */
    private void start(){
        step = this.recipe.getSteps().get(position);
        releasePlayer();
        initializeExoPlayer();
        if (detailedDescription!= null)
            detailedDescription.setText(step.getDescription());
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mediaSession = new MediaSessionCompat(context, context.getClass().getSimpleName());

        // Enable callbacks from MediaButtons and TransportControls.
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        playbackStateCompat = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(playbackStateCompat.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mediaSession.setActive(true);

    }

    public void initializeExoPlayer() {
        if (exoPlayer == null && !step.getVideoUrl().isEmpty()) {
            Uri uri = Uri.parse(step.getVideoUrl());

            //https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(context);
            exoPlayer = ExoPlayerFactory.newSimpleInstance
                    (renderersFactory, new DefaultTrackSelector(), new DefaultLoadControl());
            exoPlayerView.setPlayer(exoPlayer);
            DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "ExoPlayer"));
            MediaSource mediaSource = new ExtractorMediaSource
                    (uri,dataSourceFactory,new DefaultExtractorsFactory(),null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (exoPlayer!=null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayerView.setPlayer(null);
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mediaSession.setActive(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            playbackStateCompat.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            playbackStateCompat.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);

        }
        mediaSession.setPlaybackState(playbackStateCompat.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    public class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            // for control playing and pausing with headphone
            if (!exoPlayer.getPlayWhenReady())
                exoPlayer.setPlayWhenReady(true);
            else
                exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }
}
