package com.walkS.yiprogress.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.state.OfferState
import java.util.concurrent.locks.ReentrantLock


@Database(entities = [InterviewState::class, OfferState::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun interViewDao(): InterViewDao
    abstract fun offerDao(): OfferDao

    companion object {
        private const val DATABASE_NAME = "dbYiProgress.db"
        private const val ENCRYPTION_KEY = "mySecretKey" // 更换为实际的密钥
        private var mAppDatabase: AppDatabase? = null
        private val lock = ReentrantLock()
        //  在实例化 AppDatabase 对象时应遵循单例设计模式。每个 RoomDatabase 实例的成本相当高，几乎不需要在单个进程中访问多个实例。
        fun getInstance(context: Context): AppDatabase? {
            lock.lock()
            try {
                if (mAppDatabase == null) {
                    val databaseBuilder = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )

                    databaseBuilder.addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // 数据库创建时的初始化操作
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // 数据库打开时的操作
                        }
                        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                            super.onDestructiveMigration(db)
                            // 当数据库发生破坏性迁移时的操作
                        }
                    })

                    mAppDatabase = databaseBuilder.build()
                }
                return mAppDatabase!!
            } finally {
                lock.unlock()
            }
        }
    }
}
