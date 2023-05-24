package com.ironsource.bootapp.data

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.ironsource.bootapp.data.local.Db
import com.ironsource.bootapp.data.local.model.BootEventDto
import com.ironsource.bootapp.data.local.model.SortingOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

interface BootEventRepo {
    suspend fun getEvents(order: SortingOrder): List<BootEventDto>
    suspend fun saveBootEvent()
}

class BootEventRepoIml @Inject constructor(
    private val db: Db
) : BootEventRepo {
    override suspend fun getEvents(order: SortingOrder): List<BootEventDto> {
        val query = SupportSQLiteQueryBuilder
            .builder("boot_events")
            .orderBy("date $order")
            .create()

        return db.dao()
            .getEvents(SimpleSQLiteQuery(query.sql))
    }

    override suspend fun saveBootEvent() = withContext(Dispatchers.IO) {
        db.dao()
            .insertEvent(BootEventDto(date = Date()))
    }
}