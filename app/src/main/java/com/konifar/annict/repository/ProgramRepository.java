package com.konifar.annict.repository;

import com.konifar.annict.model.Program;
import java.util.List;
import rx.Observable;

public interface ProgramRepository {

  Observable<List<Program>> getMineOrderByStartedAtDescWithAuth(String authCode, int page);

  Observable<List<Program>> getMineOrderByStartedAtDesc(int page);
}