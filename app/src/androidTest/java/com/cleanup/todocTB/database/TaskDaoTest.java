package com.cleanup.todocTB.database;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todocTB.model.Project;
import com.cleanup.todocTB.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase mTodocDatabase;

    private Project[] mListProjects = Project.getAllProjects();

    private Task mTaskDemo1 = new Task(mListProjects[0].getId(),"Test 1",new Date().getTime());
    private Task mTaskDemo2 = new Task(mListProjects[1].getId(),"Test 2",new Date().getTime());

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        mTodocDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        mTodocDatabase.ProjectDao().insertProject(mListProjects);

        List<Task> tasks = LiveDataTestUtil.getValue(mTodocDatabase.TaskDao().getTasks());
        assertTrue(tasks.isEmpty());

        List<Project> projects = LiveDataTestUtil.getValue(mTodocDatabase.ProjectDao().getProjects());
        assertFalse(projects.isEmpty());
    }

    @After
    public void closeDb() {
        mTodocDatabase.close();
    }

    @Test
    public void insertTasks() throws InterruptedException {
        mTodocDatabase.TaskDao().insertTask(mTaskDemo1);

        List<Task> tasks = LiveDataTestUtil.getValue(mTodocDatabase.TaskDao().getTasks());
        assertFalse(tasks.isEmpty());
        assertEquals(1,tasks.size());

        checkTaskColumnsFilled(mTaskDemo1,tasks.get(0));

        mTodocDatabase.TaskDao().deleteTask(tasks.get(0));
    }

    @Test
    public void deleteTasks() throws InterruptedException {
        mTodocDatabase.TaskDao().insertTask(mTaskDemo1);
        mTodocDatabase.TaskDao().insertTask(mTaskDemo2);

        List<Task> tasks = LiveDataTestUtil.getValue(mTodocDatabase.TaskDao().getTasks());
        assertFalse(tasks.isEmpty());
        assertEquals(2,tasks.size());

        checkTaskColumnsFilled(mTaskDemo1, tasks.get(0));
        checkTaskColumnsFilled(mTaskDemo2, tasks.get(1));

        mTodocDatabase.TaskDao().deleteTask(tasks.get(0));
        tasks = LiveDataTestUtil.getValue(mTodocDatabase.TaskDao().getTasks());
        assertEquals(1,tasks.size());

        checkTaskColumnsFilled(mTaskDemo2,tasks.get(0));

        mTodocDatabase.TaskDao().deleteTask(tasks.get(0));

    }

    private void checkTaskColumnsFilled(Task mTaskDemo, Task dbTask) {
        assertEquals(mTaskDemo.getName(), dbTask.getName());
        assertEquals(dbTask.getName(), mTaskDemo.getName());
        assertEquals(dbTask.getProject().getName(), mTaskDemo.getProject().getName());
    }

}
