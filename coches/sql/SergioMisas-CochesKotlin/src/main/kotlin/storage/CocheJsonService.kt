package storage

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import config.AppConfig
import dto.CochesDto
import dto.toDto
import models.Coche
import java.io.File

@OptIn(ExperimentalStdlibApi::class)
object CocheJsonService: CocheStorageService {

    private val path = AppConfig.appData + File.separator + "coches.json"

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter<CochesDto>()

    override fun guardar(items: List<Coche>) {
        File(path).writeText(adapter.indent("\t").toJson(items.toDto()))
    }

    override fun cargar(): List<Coche> {
        TODO("No es necesario")
    }
}
