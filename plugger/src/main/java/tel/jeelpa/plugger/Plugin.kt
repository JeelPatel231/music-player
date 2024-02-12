package tel.jeelpa.plugger

import android.content.Context
import dalvik.system.DexClassLoader

data class PluginMetadata(
    val className: String,
    val path: String,
)


fun Context.getClassLoader(path: String): DexClassLoader {
    return DexClassLoader(
        path,
        cacheDir.absolutePath,
        null,
        classLoader
    )
}

class PluginLoader(private val context: Context) {

    // for loading dex, jar, apk
    operator fun <TPluginClass> invoke(pluginMetadata: PluginMetadata) : TPluginClass {

        return context.getClassLoader(pluginMetadata.path)
            .loadClass(pluginMetadata.className)
            .getConstructor()
            .newInstance() as TPluginClass
    }
}
