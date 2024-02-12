package tel.jeelpa.plugger.pluginloader

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.plugger.ManifestParser
import tel.jeelpa.plugger.PluginLoader
import tel.jeelpa.plugger.PluginMetadata
import tel.jeelpa.plugger.PluginRepo
import tel.jeelpa.plugger.getClassLoader
import tel.jeelpa.plugger.parsed
import java.io.File

data class FileSystemPluginConfig(
    val path: String,
)

class FilePluginManifestParser(
    private val context: Context
): ManifestParser<String> {
    override fun parseManifest(data: String): PluginMetadata {
        return context.getClassLoader(data)
            .getResourceAsStream("manifest.json")
            .readBytes()
            .toString(Charsets.UTF_8)
            .parsed()
    }
}

class FileSystemPluginLoader<TPlugin>(
    private val context: Context,
    private val config: FileSystemPluginConfig
): PluginRepo<TPlugin> {

    private val loader = PluginLoader(context)
    private val filePluginManifestParser = FilePluginManifestParser(context)

    private fun loadAllPlugins(): List<TPlugin> {
        return (File(config.path, "plugins").listFiles() ?: emptyArray<File>())
            .map { it.path }
            .filter { it.endsWith(".plug") }
            .map { filePluginManifestParser.parseManifest(it) }
            .map { loader(it) }
    }

    // TODO: Listen for filesystem change broadcasts and update flow on change
    override fun getAllPlugins(): Flow<List<TPlugin>> {
        return flowOf(loadAllPlugins())
    }
}