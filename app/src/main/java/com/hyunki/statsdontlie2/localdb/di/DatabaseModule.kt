package com.hyunki.statsdontlie2.localdb.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideBDLDatabase(application: Application): com.hyunki.statsdontlie2.localdb.Database {
        val sqlDriver: SqlDriver = AndroidSqliteDriver(Database.Schema, application.applicationContext, "BDL.db")
        val database = Database(sqlDriver)
        return com.hyunki.statsdontlie2.localdb.Database(database)
    }
}