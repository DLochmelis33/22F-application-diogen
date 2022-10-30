# Application for "Diogen as a service" project

## Description

This is a simple server built using Ktor. It has a single root endpoint that serves a simple HTML page with a search bar and a list of files matching the query. The query is a list of words separated by spaces* _(see improvements)_.

The searching is done via a [Searcher](src/main/kotlin/me/dl33/Searcher.kt) interface with only one `search` method. The reason to abstract this functionality is to allow different searching algorithms. The most basic one &mdash; scan every file on every query for every word &mdash; is implemented in [SimpleSearcher](src/main/kotlin/me/dl33/SimpleSearcher.kt).

## Running the app

This is a Gradle project, therefore there is `gradlew run` command to run the server and `gradlew test` command to run the tests.

## List of improvements:
- [ ] ~~Better UI~~ _(outside of test task scope)_
- [ ] Fancier searching algorithms:
  * [ ] ~~Preload files into RAM~~ (most likely impossible in practice)
  * [x] Index files (assumes words cannot contain spaces; _here is the asterisk*_):
    * [x] "Files to words": for every file build a trie and save it, on query iterate through files and check for words in \~constant time, process queries in _O(\#files)_ time. Good when there are a few (maybe large) files and many words per query. ([implementation](src/main/kotlin/me/dl33/FilesToWordsIndexingSearcher.kt))
    * [x] "Words to files": for every word save a set of files that contain them, then process queries in _O(\#words x |Ans|)_ time. Good when there is a lot of small files and few words per query. ([implementation](src/main/kotlin/me/dl33/WordsToFilesIndexingSearcher.kt))
  * [ ] Search for multiple words simultaneously (i.e. with Aho-Corasick algorithm). May be useful if queries contain a lot of words AND data is so large that indexing is not an option.
  * [ ] Employ 3rd-party libraries _(outside of test task scope)_
- [ ] Separate API and HTML _(not necessary)_
- [x] Simple testing
- [ ] Adequate testing _(meh, one integration test is fine for a test task)_
- [ ] Choosing `Searcher` and/or changing it on-the-fly _(meh, just change one word in `main` function, no DI today)_
