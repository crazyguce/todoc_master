package com.cleanup.todocTB.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todocTB.viewmodel.TaskViewModel;
import com.cleanup.todocTB.repositories.ProjectDataRepository;
import com.cleanup.todocTB.repositories.TaskDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectDataRepository mProjectDataSource;
    private final TaskDataRepository mTaskDataSource;
    private final Executor mExecutor;

    public ViewModelFactory(ProjectDataRepository pProjectDataSource,
                            TaskDataRepository pTaskDataSource,  Executor pExecutor) {

        mProjectDataSource = pProjectDataSource;
        mTaskDataSource = pTaskDataSource;
        mExecutor = pExecutor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mProjectDataSource, mTaskDataSource, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
