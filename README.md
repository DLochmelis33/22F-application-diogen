# Application for "Diogen as a service" project

## Description

This is a simple server built using Ktor. It has a single root endpoint that serves a simple HTML page with a search bar and a list of files matching the query. The query is a list of words separated by spaces* _(see improvements)_.

The searching is done via a [Searcher](src/main/kotlin/me/dl33/Searcher.kt) interface with only one method. The reason to abstract this functionality is to allow different searching algorithms. The most basic one &mdash; scan every file on every query for every word &mdash; is implemented in [SimpleSearcher](src/main/kotlin/me/dl33/SimpleSearcher.kt).

## Running the app

This is a Gradle project, therefore there is `gradlew run` command to run the server and `gradlew test` command to run the tests.

## List of improvements:
- [ ] UI (this is out of test task scope)
- [ ] Fancier searching algorithms:
  * [ ] ~~Preload files into RAM~~ (most likely impossible in practice)
  * [ ] Search for multiple words simultaneously (i.e. with Aho-Corasick algorithm) &mdash; will be useful if queries contain a lot of words
  * [ ] Index files (assumes words are always split by a space and cannot contain one) _(*)_
