package me.dl33

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createTempDirectory
import kotlin.io.path.forEachLine
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.readText
import kotlin.io.path.writeText

class FilesToWordsIndexingSearcher(
    override val folder: String,
    indexFolder: String? = null
) : Searcher {
    private val indexFolder: Path = indexFolder?.let { Path(it) } ?: createTempDirectory()
    private val serialFormat = Json

    init {
        buildIndex()
    }

    private fun buildIndex() {
        check(indexFolder.listDirectoryEntries().isEmpty())

        Files.list(Path(folder)).forEach { file ->
            val trie = Trie()
            file.forEachLine { line ->
                line.split(" ").forEach { word ->
                    trie.add(word)
                }
            }
            indexFolder.resolve(file.name).writeText(serialFormat.encodeToString(trie))
        }
    }

    override fun search(words: List<String>): List<String> {
        return Files.list(indexFolder).filter { indexFile ->
            val trie = serialFormat.decodeFromString<Trie>(indexFile.readText())
            words.any { trie.contains(it) }
        }.map { it.name }.toList()
    }
}
