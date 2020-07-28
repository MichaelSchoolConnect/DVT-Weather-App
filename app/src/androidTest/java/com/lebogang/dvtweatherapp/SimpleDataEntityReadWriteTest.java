package com.lebogang.dvtweatherapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class SimpleDataEntityReadWriteTest {
    /*private OfflineDataDao userDao;
    private OfflineDataDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, OfflineDataDatabase.class).build();
        userDao = db.offlineDataDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        *//*OfflineDataEntity user = TestUtil.createUser(3);
        user.getTemp();
        userDao.insertWeatherData(user);
        List<OfflineDataEntity> byName = userDao
        assertThat(byName.get(0), equalTo(user));*//*
    }*/
}

