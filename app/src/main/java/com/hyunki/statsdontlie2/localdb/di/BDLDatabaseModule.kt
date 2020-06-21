package com.hyunki.statsdontlie2.localdb.di

import android.app.Application
import com.hyunki.statsdontlie2.Database
import com.hyunki.statsdontlie2.localdb.BDLDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides

@Module
class BDLDatabaseModule {
    @Provides
    fun provideBDLDatabase(application: Application): BDLDatabase {
        val sqlDriver: SqlDriver = AndroidSqliteDriver(Database.Schema, application.applicationContext, "BDL.db")
        val database = Database(sqlDriver)
        return BDLDatabase(database)
    }
}