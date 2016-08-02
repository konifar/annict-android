package com.konifar.annict.viewmodel.event;

import com.konifar.annict.viewmodel.MyWorkItemViewModel;

import java.util.List;

public class MyWorksLoadedEvent {

    public List<MyWorkItemViewModel> myWorkItemViewModels;

    public MyWorksLoadedEvent(List<MyWorkItemViewModel> myWorkItemViewModels) {
        this.myWorkItemViewModels = myWorkItemViewModels;
    }

}
