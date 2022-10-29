package me.dl33

interface Searcher {
    val folder: String
    /**
     * Returns names of the files in [folder] that contain at least one word from [words] list.
     */
    fun search(words: List<String>): List<String>
}
