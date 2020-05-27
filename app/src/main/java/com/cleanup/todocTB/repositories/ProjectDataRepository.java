package com.cleanup.todocTB.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todocTB.database.dao.ProjectDao;
import com.cleanup.todocTB.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao pProjectDao) { mProjectDao = pProjectDao; }

    public LiveData<List<Project>> getProjects() { return mProjectDao.getProjects(); }
}
