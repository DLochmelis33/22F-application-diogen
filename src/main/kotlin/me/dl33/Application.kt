package me.dl33

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.html.FormEncType
import kotlinx.html.FormMethod
import kotlinx.html.body
import kotlinx.html.form
import kotlinx.html.submitInput
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.textInput
import kotlinx.html.tr

fun main() {
    embeddedServer(Netty, port = 9876, host = "0.0.0.0", module = Application::searcherModule)
        .start(wait = true)
}

private val searcher: Searcher = SimpleSearcher(System.getenv("SEARCHER_FOLDER") ?: "./words/")

fun Application.searcherModule() {
    routing {
        get("/") {
            val words = call.parameters["words"]?.split(" ") ?: emptyList()
            val resultingFilenames = searcher.search(words)

            call.respondHtml {
                body {
                    form(
                        action = "/",
                        method = FormMethod.get,
                        encType = FormEncType.applicationXWwwFormUrlEncoded,
                    ) {
                        textInput(name = "words") { required = true }
                        submitInput { value = "Search" }
                    }
                    if (resultingFilenames.isEmpty()) {
                        +"No files matching query"
                    } else {
                        table {
                            for (filename in resultingFilenames) tr { td { +filename } }
                        }
                    }
                }
            }
        }
    }
}
