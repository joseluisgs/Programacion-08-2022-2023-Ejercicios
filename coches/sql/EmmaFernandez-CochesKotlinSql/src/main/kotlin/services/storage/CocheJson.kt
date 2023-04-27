package services.storage

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.adapter
import config.AppConfig
import dto.CochesDto
import dto.toDto
import models.Coche
import java.io.File

@OptIn(ExperimentalStdlibApi::class)
object CocheJson : CocheStorage {
    private val path = AppConfig.jsonPath + File.separator + "coches.json"
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter<CochesDto>()
    override fun save(items: List<Coche>) {
        File(path).writeText(adapter.indent("\t").toJson(items.toDto()))
    }

    override fun load(): List<Coche> {
        TODO("not needed")
    }
}
