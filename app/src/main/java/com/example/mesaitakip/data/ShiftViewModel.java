package com.example.mesaitakip.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.Android.lifecycle.AndroidViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShiftViewModel extends AndroidViewModel {

    private final ShiftDao shiftDao;
    private final ExecutorService executorService;

    public ShiftViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        shiftDao = database.shiftDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(ShiftRecord shiftRecord) {
        executorService.execute(() -> shiftDao.insert(shiftRecord));
    }
}
