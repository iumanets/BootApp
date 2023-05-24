package com.ironsource.bootapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.ironsource.bootapp.data.local.model.BootEventDto

@Dao
interface BootEventDao {
    @RawQuery
    suspend fun getEvents(query: SupportSQLiteQuery): List<BootEventDto>

    @Insert
    suspend fun insertEvent(event: BootEventDto)
}