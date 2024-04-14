package com.rahulghag.realmsample.di

import com.rahulghag.realmsample.data.local.TaskEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val configuration = RealmConfiguration.Builder(
            schema = setOf(TaskEntity::class)
        ).compactOnLaunch().build()
        return Realm.open(configuration = configuration)
    }
}