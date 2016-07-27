package com.konifar.annict.viewmodel.event;

import com.konifar.annict.viewmodel.MyProgramItemViewModel;

import java.util.List;

public class MyProgramsLoadedEvent {

    public List<MyProgramItemViewModel> myProgramItemViewModels;

    public MyProgramsLoadedEvent(List<MyProgramItemViewModel> myProgramItemViewModels) {
        this.myProgramItemViewModels = myProgramItemViewModels;
    }

}
