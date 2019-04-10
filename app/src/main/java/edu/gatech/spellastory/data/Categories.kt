package edu.gatech.spellastory.data

import android.content.Context
import com.opencsv.CSVIterator
import com.opencsv.CSVReader
import edu.gatech.spellastory.domain.Category
import edu.gatech.spellastory.util.SingletonHolder
import java.io.InputStreamReader

class Categories private constructor(context: Context) {

    private val categories: Map<Int, String> by lazy {
        val reader = CSVReader(InputStreamReader(context.assets.open("coded_categories.csv")))
        val categories = read(reader)
        reader.close()
        categories
    }

    private fun read(reader: CSVReader) = CSVIterator(reader).asSequence().map { line ->
        val num = Integer.parseInt(line[0])
        val name = line[1]
        num to name
    }.toMap()

    fun getCategory(code: Int): Category = Category(code, categories.getValue(code))

    fun getCategories(vararg codes: String): List<Category> =
        getCategories(*codes.map { Integer.parseInt(it) }.toIntArray())

    fun getCategories(vararg codes: Int): List<Category> = codes.map { getCategory(it) }

    companion object : SingletonHolder<Categories, Context>(::Categories)
}