package me.dl33

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertTrue

class IntegrationTest {
    @Test
    fun `basic test`(): Unit = runBlocking {
        val server = server()
        server.start(wait = false)
        val client = HttpClient {
            install(HttpTimeout) {
                requestTimeoutMillis = 2000 // give server time to boot
            }
        }
        val result = client.get {
            url {
                host = "localhost"
                port = 9876
            }
            parameter("words", "a")
        }.bodyAsText()
        assertTrue { result.contains("a.txt") }
    }
}
