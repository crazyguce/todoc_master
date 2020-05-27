package com.cleanup.todocTB.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todocTB.model.Project;
import com.cleanup.todocTB.model.Task;
import com.cleanup.todocTB.repositories.ProjectDataRepository;
import com.cleanup.todocTB.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final ProjectDataRepository mProjectDataSource;
    private final TaskDataRepository mTaskDataSource;
    private final Executor mExecutor;

    @Nullable
    private LiveData<List<Project>> mProjects;

    public TaskViewModel(ProjectDataRepository pProjectDataSource,
                         TaskDataRepository pTaskDataSource, Executor pExecutor) {

        mProjectDataSource = pProjectDataSource;
        mTaskDataSource = pTaskDataSource;
        mExecutor = pExecutor; 
    }

    public void init() {
        if (mProjects == null)
            mProjects = mProjectDataSource.getProjects();
    }

    @Nullable
    public LiveData<List<Project>> getProjects() {
        return mProjects;
    }

    public LiveData<List<Task>> getTasks() { return mTaskDataSource.getTasks(); }

    public void createTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.createTask(task));
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.deleteTask(task));
    }
}
