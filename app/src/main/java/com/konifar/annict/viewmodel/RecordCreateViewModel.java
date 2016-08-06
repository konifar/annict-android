package com.konifar.annict.viewmodel;

import com.konifar.annict.BR;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Program;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.viewmodel.event.EventBus;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class RecordCreateViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = RecordCreateViewModel.class.getSimpleName();

    private final Context context;

    private final AnnictClient client;

    private final EventBus eventBus;

    private final PageNavigator pageNavigator;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Bindable
    public Program program;

    @Inject
    public RecordCreateViewModel(Context context, AnnictClient client, EventBus eventBus,
        PageNavigator pageNavigator) {
        this.context = context;
        this.client = client;
        this.eventBus = eventBus;
        this.pageNavigator = pageNavigator;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
        notifyPropertyChanged(BR.program);
    }

    @Override
    public void destroy() {
        compositeSubscription.unsubscribe();
    }
}
