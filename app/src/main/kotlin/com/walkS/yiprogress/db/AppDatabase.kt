package com.walkS.yiprogress.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.walkS.yiprogress.state.InterviewState
import kotlin.concurrent.Volatile

@Database(entities = [InterviewState::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun interViewDao(): InterViewDao?

    companion object {
        @Volatile
        private var mAppDatabase: AppDatabase? = null

        //  在实例化 AppDatabase 对象时应遵循单例设计模式。每个 RoomDatabase 实例的成本相当高，几乎不需要在单个进程中访问多个实例。
        fun getInstance(context: Context): AppDatabase? {
            if (mAppDatabase == null) {
                synchronized(AppDatabase::class.java) {
                    if (mAppDatabase == null) {
                        mAppDatabase = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "dbYiProgress.db"
                        )
                            .addMigrations() // 默认不允许在主线程中连接数据库
                            // .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return mAppDatabase
        }
    }
}
