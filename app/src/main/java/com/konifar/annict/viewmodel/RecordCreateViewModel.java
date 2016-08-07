package com.konifar.annict.viewmodel;

import com.konifar.annict.BR;
import com.konifar.annict.R;
import com.konifar.annict.model.Program;
import com.konifar.annict.model.Record;
import com.konifar.annict.repository.RecordRepository;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.util.ViewHelper;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
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

    public Record record;

    private OnUpdateListener onUpdateListener;

    private OnClickDismissListener onClickDismissListener;

    @Inject
    public RecordCreateViewModel(Context context, RecordRepository repository, PageNavigator navigator) {
        this.context = context;
        this.repository = repository;
        this.navigator = navigator;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener, OnClickDismissListener onClickDismissListener) {
        this.onUpdateListener = onUpdateListener;
        this.onClickDismissListener = onClickDismissListener;
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

        recordsCount = context.getResources().getQuantityString(
            R.plurals.records_count, program.episode.recordsCount, program.episode.recordsCount);

        notifyPropertyChanged(BR.workTitle);
        notifyPropertyChanged(BR.thumbUrl);
        notifyPropertyChanged(BR.episodeTitle);
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

    public void onclickSubmitButton(@SuppressWarnings("unused") View button) {
        Subscription sub = getObservable()
            .doOnSubscribe(() -> loadingVisibility.set(View.VISIBLE))
            .doOnError(throwable -> loadingVisibility.set(View.GONE))
            .doOnCompleted(() -> loadingVisibility.set(View.GONE))
            .subscribe(
                this::onSuccess,
                this::onFailure
            );

        compositeSubscription.add(sub);
    }

    private void onFailure(Throwable throwable) {
        loadingVisibility.set(View.GONE);
        Log.e(TAG, "Record create failed.", throwable);
        TopSnackbarViewModel viewModel =
            ResultType.UPDATE_ERROR.createViewModel(context,
                view -> onclickSubmitButton(null)
            );
        onUpdateListener.onRecordUpdated(record, viewModel);
    }

    private void onSuccess(Record record) {
        this.record = record;
        TopSnackbarViewModel viewModel =
            ResultType.SUCCESS.createViewModel(context,
                view -> onClickDismissListener.onClickDismiss()
            );
        onUpdateListener.onRecordUpdated(record, viewModel);
    }

    private Observable<Record> getObservable() {
        if (record != null) {
            // Update
            return repository.update(
                record.id,
                comment,
                rating,
                shouldTwitterShare,
                shouldFacebookShare
            );
        } else {
            // Create
            return repository.create(
                program.episode.id,
                comment,
                rating,
                shouldTwitterShare,
                shouldFacebookShare
            );
        }
    }

    @Override
    public void destroy() {
        compositeSubscription.unsubscribe();
    }

    public interface OnUpdateListener {

        void onRecordUpdated(Record record, TopSnackbarViewModel viewModel);
    }

    public interface OnClickDismissListener {

        void onClickDismiss();
    }

    private enum ResultType {

        SUCCESS(R.string.record_create_completed, R.string.close),
        UPDATE_ERROR(R.string.record_create_failed, R.string.retry);

        private int textResId;

        private int buttonResId;

        ResultType(@StringRes int textResId, @StringRes int buttonResId) {
            this.textResId = textResId;
            this.buttonResId = buttonResId;
        }

        public TopSnackbarViewModel createViewModel(Context context,
            TopSnackbarViewModel.OnClickButtonListener listener) {
            return new TopSnackbarViewModel(context.getString(textResId), context.getString(buttonResId), listener);
        }
    }

}

