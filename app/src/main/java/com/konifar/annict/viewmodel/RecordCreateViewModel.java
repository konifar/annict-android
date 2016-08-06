package com.konifar.annict.viewmodel;

import com.konifar.annict.BR;
import com.konifar.annict.model.Program;
import com.konifar.annict.repository.RecordRepository;
import com.konifar.annict.util.DateUtil;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class RecordCreateViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = RecordCreateViewModel.class.getSimpleName();

    private static final int PROGRESS_BAR_PRECISION = 10;

    private final Context context;

    private final RecordRepository repository;

    private final PageNavigator navigator;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Bindable
    public String thumbUrl;

    @Bindable
    public String workTitle;

    @Bindable
    public String episodeTitle;

    @Bindable
    public String displayDate;

    @Bindable
    public float rating;

    @Bindable
    public int ratingProgress; // For SeekBar

    @Bindable
    public String comment;

    @Bindable
    public boolean shouldTwitterShare;

    @Bindable
    public boolean shouldFacebookShare;

    public Program program;

    @Inject
    public RecordCreateViewModel(Context context, RecordRepository repository, PageNavigator navigator) {
        this.context = context;
        this.repository = repository;
        this.navigator = navigator;
    }

    public Program getProgram() {
        return program;
    }

    public void bindProgram(Program program) {
        this.program = program;

        if (program.work != null) {
            workTitle = program.work.title;
            if (program.work.twitterUserName != null) {
                thumbUrl = ViewHelper.getTwitterProfileImageUrl(program.work.twitterUserName);
            }
        }
        episodeTitle = ViewHelper.getEpisodeTitle(program.episode, context);

        displayDate = DateUtil.getProgramStartDate(program.startedAt);

        notifyPropertyChanged(BR.workTitle);
        notifyPropertyChanged(BR.thumbUrl);
        notifyPropertyChanged(BR.episodeTitle);
        notifyPropertyChanged(BR.displayDate);
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if (this.rating != rating) {
            this.rating = rating;
            notifyPropertyChanged(BR.rating);
            this.ratingProgress = (int) rating * PROGRESS_BAR_PRECISION;
            notifyPropertyChanged(BR.ratingProgress);
        }
    }

    public int getRatingProgress() {
        return ratingProgress;
    }

    public void setRatingProgress(int ratingProgress) {
        if (this.ratingProgress != ratingProgress) {
            this.ratingProgress = ratingProgress;
            notifyPropertyChanged(BR.ratingProgress);
            rating = ((float) ratingProgress) / PROGRESS_BAR_PRECISION;
            notifyPropertyChanged(BR.rating);
        }
    }

    @Override
    public void destroy() {
        compositeSubscription.unsubscribe();
    }
}
