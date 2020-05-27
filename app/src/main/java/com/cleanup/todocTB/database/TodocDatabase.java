package com.cleanup.todocTB.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todocTB.database.dao.TaskDao;
import com.cleanup.todocTB.model.Project;
import com.cleanup.todocTB.database.dao.ProjectDao;
import com.cleanup.todocTB.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1 , exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    private static TodocDatabase INSTANCE;

    public abstract ProjectDao ProjectDao();
    public abstract TaskDao TaskDao();

    public static TodocDatabase getInstance(Context pContext) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(pContext.getApplicationContext(),
                            TodocDatabase.class, "Todoc.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Project[] projects = Project.getAllProjects();
                for (Project project : projects) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    db.insert("project", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }
}
