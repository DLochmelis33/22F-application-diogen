package me.dl33

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IntegrationTest {

    private val wordsFolder = "./words/"

    @Test
    fun `basic test`() = listOf(
        SimpleSearcher(wordsFolder),
        WordsToFilesIndexingSearcher(wordsFolder),
        FilesToWordsIndexingSearcher(wordsFolder),
    ).forEach { searcher ->
        testApplication {
            application {
                searcherModule(searcher)
            }
            val result = client.get {
                parameter("words", "b")
            }.bodyAsText()
            assertTrue { result.contains("b.txt") }
            assertTrue { result.contains("ab.txt") }
            assertFalse { result.contains("a.txt") }
        }
    }
}
