package com.freez.cat.data.local.roomdatabase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.freez.cat.data.local.roomdatabase.dao.FavoriteCatDao
import com.freez.cat.data.local.roomdatabase.entity.FavoriteCatsEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteCatDaoTest {


    private lateinit var database: AppDatabase
    private lateinit var favoriteCatDao: FavoriteCatDao

    @Before
    fun setUp() {
        // Create in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        favoriteCatDao = database.favoriteCatDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertFavoriteCatAndRetrieveIt() = runBlocking {
        val favoriteCat = FavoriteCatsEntity(id = null, catId = "fake-cat-id")

        favoriteCatDao.insert(favoriteCat)

        val allFavorites = favoriteCatDao.getAllFavoriteCats().first()
        assertTrue(allFavorites.contains("fake-cat-id"))
    }

    @Test
    fun removeFavoriteCatAndCheckIfRemoved() = runBlocking {
        val favoriteCat = FavoriteCatsEntity(id = null, catId = "fake-cat-id")

        favoriteCatDao.insert(favoriteCat)

        favoriteCatDao.remove("fake-cat-id")

        val allFavorites = favoriteCatDao.getAllFavoriteCats().first()
        assertFalse(allFavorites.contains("fake-cat-id"))
    }

    @Test
    fun checkIsFavorite() = runBlocking {
        val favoriteCat = FavoriteCatsEntity(id = null, catId = "fake-cat-id")

        favoriteCatDao.insert(favoriteCat)

        val isFavorite = favoriteCatDao.isFavorite("fake-cat-id").first()
        assertTrue(isFavorite)
    }

    @Test
    fun checkIsNotFavorite() = runBlocking {
        val isFavorite = favoriteCatDao.isFavorite("unknown_cat").first()
        assertFalse(isFavorite)
    }
}
