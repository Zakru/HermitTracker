package com.github.zakru.hermittracker

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

class HermitTypeAdapters {

    companion object {

        val DATE_TIME = object : TypeAdapter<DateTime>() {

            override fun write(out: JsonWriter?, value: DateTime?) {
                out?.value(value?.toString(ISODateTimeFormat.dateTime()))
            }

            override fun read(`in`: JsonReader?): DateTime {
                return DateTime.parse(`in`?.nextString())
            }
        }
    }
}