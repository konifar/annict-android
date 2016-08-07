package com.konifar.annict.viewmodel;

import com.konifar.annict.BR;
import com.konifar.annict.R;
import com.konifar.annict.model.Program;
import com.konifar.annict.repository.RecordRepository;
import com.konifar.annict.util.DateUtil;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.view.View;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class RecordCreateViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = RecordCreateViewModel.class.getSimpleName();

    private static final int PROGRESS_BAR_PRECISION = 10;

    private static final float DEFAULT_RATING = 3.0f;

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
    public String recordsCount;

    @Bindable
    public float rating = DEFAULT_RATING;

    @Bindable
    public int ratingProgress = (int) DEFAULT_RATING * PROGRESS_BAR_PRECISION; // For SeekBar

    @Bindable
    public String comment;

    @Bindable
    public boolean shouldTwitterShare;

    @Bindable
    public boolean shouldFacebookShare;

    public ObservableInt loadingVisibility = new ObservableInt(View.GONE);

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

        recordsCount = context.getResources().getQuantityString(
            R.plurals.records_count, program.episode.recordsCount, program.episode.recordsCount);

        notifyPropertyChanged(BR.workTitle);
        notifyPropertyChanged(BR.thumbUrl);
        notifyPropertyChanged(BR.episodeTitle);
        notifyPropertyChanged(BR.displayDate);
        notifyPropertyChanged(BR.recordsCount);
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
    }

    public void onclickSubmitButton(@SuppressWarnings("unused") View view) {
        loadingVisibility.set(View.VISIBLE);
    }

    @Override
    public void destroy() {
        compositeSubscription.unsubscribe();
    }
}
