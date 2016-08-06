package com.konifar.annict.helper;

import com.konifar.annict.model.Program;

import java.util.ArrayList;
import java.util.List;

public class MockHelper {

    public static List<Program> mockPrograms(int programsCount) {
        List<Program> mockPrograms = new ArrayList<>();

        for (int i = 0; i < programsCount; i++) {
            mockPrograms.add(new Program());
        }
        return mockPrograms;
    }

}
