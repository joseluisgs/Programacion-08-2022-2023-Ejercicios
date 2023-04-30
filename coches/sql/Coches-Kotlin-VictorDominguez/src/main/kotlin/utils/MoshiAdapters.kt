package utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.util.*

class LocalDateAdapter : JsonAdapter<LocalDate>() {

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDate? = LocalDate.parse(reader.readJsonValue().toString())
    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        writer.jsonValue(value.toString())
    }

}

class UUIDAdapter : JsonAdapter<UUID>() {

    @FromJson
    override fun fromJson(reader: JsonReader): UUID? = UUID.fromString(reader.readJsonValue().toString())

    @ToJson
    override fun toJson(writer: JsonWriter, value: UUID?) {
        writer.jsonValue(value.toString())
    }

}