package com.konifar.annict.helper;

import com.konifar.annict.model.Program;
import com.konifar.annict.model.Programs;

import java.util.ArrayList;
import java.util.List;

public class MockHelper {

    public static Programs mockPrograms(int programsCount) {
        List<Program> mockPrograms = new ArrayList<>();

        for (int i = 0; i < programsCount; i++) {
            mockPrograms.add(new Program());
        }

        Programs programs = new Programs();
        programs.list = mockPrograms;

        return programs;
    }

}
