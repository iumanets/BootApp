package com.ironsource.bootapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ironsource.bootapp.data.local.converters.TimeConverter
import com.ironsource.bootapp.data.local.dao.BootEventDao
import com.ironsource.bootapp.data.local.model.BootEventDto

@Database(entities = [BootEventDto::class], version = 1, exportSchema = false)
@TypeConverters(TimeConverter::class)
abstract class Db : RoomDatabase() {
    abstract fun dao(): BootEventDao
}