package com.ironsource.bootapp.di

import android.content.Context
import androidx.room.Room
import com.ironsource.bootapp.data.BootEventRepo
import com.ironsource.bootapp.data.BootEventRepoIml
import com.ironsource.bootapp.data.local.Db
import com.ironsource.bootapp.ui.mapper.UiMapper
import com.ironsource.bootapp.ui.mapper.UiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    fun bindBootEventRepo(bootEventRepoIml: BootEventRepoIml): BootEventRepo

    @Binds
    fun bindUiMapper(uiMapperIml:UiMapperImpl): UiMapper

    companion object {
        @Provides
        @Singleton
        fun provideDb(
            @ApplicationContext context: Context,
        ): Db =
            Room.databaseBuilder(context, Db::class.java, "boot_db")
                .build()
    }
}