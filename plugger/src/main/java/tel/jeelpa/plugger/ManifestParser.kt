package tel.jeelpa.plugger

import kotlinx.serialization.json.Json


interface ManifestParser<T> {
    fun parseManifest(data: T): PluginMetadata
}

inline fun <reified T> String.parsed(): T {
    return Json.decodeFromString<T>(this)
}
