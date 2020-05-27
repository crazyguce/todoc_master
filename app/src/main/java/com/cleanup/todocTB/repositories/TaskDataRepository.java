package com.cleanup.todocTB.repositories;

import android.arch.lifecycle.LiveData;


import com.cleanup.todocTB.database.dao.TaskDao;
import com.cleanup.todocTB.model.Task;

import java.util.List;

public class TaskDataRepository {

    private TaskDao mTaskDao;

    public TaskDataRepository(TaskDao pTaskDao) { mTaskDao = pTaskDao; }

    public LiveData<List<Task>> getTasks() { return mTaskDao.getTasks(); }

    public void createTask(Task pTask) { mTaskDao.insertTask(pTask); }

    public void deleteTask(Task pTask) { mTaskDao.deleteTask(pTask); }
}
