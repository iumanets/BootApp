package com.ironsource.bootapp.ui.mapper

import android.content.Context
import com.ironsource.bootapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject

interface UiMapper {
   fun map(events: List<Date>):String
}


class UiMapperImpl @Inject constructor(@ApplicationContext val context: Context) : UiMapper{
    override fun map(events: List<Date>): String {
        if (events.isEmpty()) {
            return context.getString(R.string.no_boot)
        }

        return  events.mapIndexed { index, date ->
            "${index + 1} - ${date.time}"
        }.joinToString("\n")
    }
}