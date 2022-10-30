package me.dl33

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.appendLines
import kotlin.io.path.bufferedReader
import kotlin.io.path.createFile
import kotlin.io.path.createTempDirectory
import kotlin.io.path.exists
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.readLines

/**
 * Upon instantiating, indexes files in [folder] and saves the index to [indexFolder].
 * The index is a collection of files, where a file 'hello' contains a list of
 * all files in [folder] containing a 'hello' word.
 */
class WordsToFilesIndexingSearcher(
    override val folder: String,
    indexFolder: String? = null
) : Searcher {

    private val indexFolder: Path = indexFolder?.let { Path(it) } ?: createTempDirectory()

    init {
        buildIndex()
    }

    private fun buildIndex() {
        check(indexFolder.listDirectoryEntries().isEmpty())

        // TODO: improvement: don't open files so often, make a custom buffer
        Files.list(Path(folder)).forEach { file ->
            file.bufferedReader().forEachLine { line ->
                for (word in line.split(" ")) {
                    indexFolder.resolve(word).run {
                        if (!exists()) createFile()
                        appendLines(listOf(file.name))
                    }
                }
            }
        }
    }

    override fun search(words: List<String>): List<String> {
        return words.flatMap { word ->
            indexFolder.resolve(word).takeIf { it.exists() }?.readLines() ?: emptyList()
        }.distinct()
    }
}
