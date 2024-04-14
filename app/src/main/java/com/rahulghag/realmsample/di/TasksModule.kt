package com.rahulghag.realmsample.di

import com.rahulghag.realmsample.data.local.TaskMapper
import com.rahulghag.realmsample.data.repositories.TaskRepositoryImpl
import com.rahulghag.realmsample.domain.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TasksModule {
    @Provides
    @Singleton
    fun provideTaskMapper(): TaskMapper {
        return TaskMapper()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(realm: Realm, taskMapper: TaskMapper): TaskRepository {
        return TaskRepositoryImpl(realm = realm, taskMapper = taskMapper)
    }
}