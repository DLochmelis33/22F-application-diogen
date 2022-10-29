package me.dl33

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.io.path.readText
import kotlin.streams.toList

/**
 * Upon calling [search], simply searches every file in [folder].
 */
class SimpleSearcher(override val folder: String) : Searcher {
    override fun search(words: List<String>): List<String> {
        return Files.list(Path(folder)).filter { file: Path ->
            val fileText = file.readText()
            words.any { fileText.contains(it) }
        }.map { it.name }.toList()
    }
}
