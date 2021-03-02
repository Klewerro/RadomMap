package com.klewerro.radommap.di

import com.klewerro.radommap.data.BaseRepository
import com.klewerro.radommap.data.LocalRepository
import com.klewerro.radommap.data.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository() = FirebaseRepository() as BaseRepository

}