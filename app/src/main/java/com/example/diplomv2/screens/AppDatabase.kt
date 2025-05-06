package com.example.diplomv2.screens

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity
data class StatisticsEntry(
    @PrimaryKey val levelId: Int, // уникальность по уровню
    val correctAnswers: Int,
    val starsEarned: Int,
    val isExpress: Boolean = false
)
@Dao
interface StatisticsDao {
    @Query("SELECT * FROM StatisticsEntry")
    suspend fun getAllStats(): List<StatisticsEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStat(stat: StatisticsEntry)

    @Query("SELECT * FROM StatisticsEntry WHERE isExpress = 1")
    suspend fun getExpressStats(): List<StatisticsEntry>

    @Query("SELECT * FROM StatisticsEntry WHERE levelId = :levelId")
    suspend fun getStatByLevel(levelId: Int): StatisticsEntry?

    @Query("DELETE FROM StatisticsEntry WHERE levelId = :levelId")
    suspend fun deleteStat(levelId: Int)  // ← НОВЫЙ МЕТОД
}

@Database(entities = [ShapeStatEntity::class, StatisticsEntry::class], version = 7)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shapeStatDao(): ShapeStatDao
    abstract fun statisticsDao(): StatisticsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


@Entity(tableName = "shape_stats")
data class ShapeStatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val correctAnswers: Int,
    val timestamp: Long
)

@Dao
interface ShapeStatDao {
    @Insert
    suspend fun insert(stat: ShapeStatEntity)

    @Query("SELECT * FROM shape_stats ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ShapeStatEntity>>
}
