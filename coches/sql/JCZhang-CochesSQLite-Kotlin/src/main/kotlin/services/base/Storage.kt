package services.base

interface Storage <T> {

    fun saveIntoJson(items: List<T>): List<T>

    fun loadDatafromJson(): List<T>

    fun loadDataFromCsv(): List<T>

}